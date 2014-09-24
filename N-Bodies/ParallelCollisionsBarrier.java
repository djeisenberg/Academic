/*
 * Daniel Eisenberg
 * CSc 422 Spring 2012
 * Parallel Project: N-Bodies Problem with Collisions
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;

public class ParallelCollisionsBarrier {

    private static String    filename     = Constants.FILENAME;

    private static boolean   first        = true;

    private static int       numBodies    = 4;
    private static int       numWorkers   = 2;

    private static long      barrierTime  = 0L;
    private static long      barrierCount = 0L;
    private static long      steps        = 100L;
    private static long      maxSteps     = Long.MAX_VALUE;

    private static Long      seed         = null;

    private static double    mass         = 0.0;
    private static double    radius       = 0.0;
    private static double    dt           = Constants.DT;
    private static double    smallStep    = 1e-12;
    private static double    time         = 0.0;
    private static double    minIncrement = Double.MAX_VALUE;
    private static double    maxIncrement = Double.MIN_VALUE;
    private static double    initialDT    = Constants.DT;

    private static Random    rng;

    private static Semaphore mutex        = new Semaphore(1);

    private static Semaphore barrierMutex = new Semaphore(1);

    private static Semaphore barrier[][];


    public static void main(String[] args) throws IOException,
                                          InterruptedException,
                                          BrokenBarrierException {
        // resolve arguments

        if (args.length > 0) {
            numWorkers = Integer.parseInt(args[0]);
        }

        if (numWorkers > Constants.MAX_WORKERS) {
            numWorkers = Constants.MAX_WORKERS;
        }

        if (numWorkers < 1) {
            numWorkers = 1;
        }

        if (args.length > 1) {
            numBodies = Integer.parseInt(args[1]);
        } else {
            numBodies = Constants.PARAMETERS.length / 6;
        }

        if (numBodies <= 0) {
            System.out.println("Please specify a positive number of bodies.");
            System.exit(0);
        }

        if (numBodies < numWorkers) {
            numWorkers = numBodies;
        }

        if (args.length > 2) {
            mass = Double.parseDouble(args[2]);
        }

        if (mass <= 0.0) {
            mass = Constants.DEFAULT_MASS;
        }

        if (args.length > 3) {
            steps = Long.parseLong(args[3]);
        }

        if (steps <= 0) {
            System.out.println("Please specify a positive number of steps.");
            System.exit(0);
        }

        if (steps > maxSteps) {
            maxSteps = steps;
        }

        if (args.length > 4) {
            radius = Double.parseDouble(args[4]);
        }

        if (radius <= 0.0) {
            radius = Constants.DEFAULT_RADIUS;
        }

        if (args.length > 5) {
            dt = Double.parseDouble(args[5]);
        }

        if (dt <= 0.0) {
            System.out.println("Please specify a positive time increment.");
        }

        initialDT = dt;

        if (args.length > 6) {
            seed = Long.parseLong(args[6]);
        }

        if (seed != null) {
            rng = new Random(seed);
        } else {
            rng = new Random();
        }

        File file = new File(filename);

        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        FileWriter fw = new FileWriter(file);

        // initialize bodies
        ParallelBodyFS bodies[];
        if (args.length <= 1) {
            bodies = new ParallelBodyFS[numBodies];
            for (int i = 0; i < numBodies; i++) {
                int j = i * 6;
                bodies[i] = new ParallelBodyFS(Constants.PARAMETERS[j],
                                               Constants.PARAMETERS[j + 1],
                                               Constants.PARAMETERS[j + 2],
                                               Constants.PARAMETERS[j + 3],
                                               Constants.PARAMETERS[j + 4],
                                               Constants.PARAMETERS[j + 5],
                                               numWorkers);
            }

        } else {
            bodies = new ParallelBodyFS[numBodies];
            for (int i = 0; i < numBodies; i++) {
                bodies[i] = new ParallelBodyFS(rand(), rand(), rand(),
                                               rand(), mass, radius,
                                               numWorkers);
            }
        }

        fw.append("Initial positions and velocities\n\n");

        for (int i = 0; i < bodies.length; i++) {
            fw.append("Body " + i + ": " + "p = (" + bodies[i].posx + ", " +
                      bodies[i].posy + "); " + "v = (" + bodies[i].vx +
                      ", " + bodies[i].vy + ")\n");
        }
        fw.append("\n");

        // initialize barrier
        barrier = new Semaphore[numWorkers][Constants.MAX_STAGES];

        for (int i = 0; i < barrier.length; i++) {
            for (int j = 0; j < Constants.MAX_STAGES; j++) {
                barrier[i][j] = new Semaphore(0);
            }
        }

        long start = System.currentTimeMillis();

        WorkerBarrier workers[] = new WorkerBarrier[numWorkers - 1];
        for (int i = 0; i < numWorkers - 1; i++) {
            workers[i] = new WorkerBarrier(i, bodies);
            new Thread(workers[i]).start();
        }

        // execute
        double runtime = initialDT * steps;
        long iterations = 0;
        int id = numWorkers - 1;
        while (time < runtime && iterations < maxSteps) {

            updateForces(bodies, id);
            barrier(id);
            updatePositions(bodies, id);
            barrier(id);
            checkCollisions(bodies, id);

            if (dt < minIncrement) {
                minIncrement = dt;
            }
            if (dt > maxIncrement) {
                maxIncrement = dt;
            }
            time += dt;
            iterations++;
            dt = initialDT;

            if (time >= runtime || iterations >= maxSteps) {
                break;
            }

            barrier(id);
        }

        long stop = System.currentTimeMillis();

        long computeTime = stop - start;
        long secs = computeTime / 1000;
        long msecs = computeTime % 1000;
        System.out.println("computation time: " + secs + " seconds " +
                           msecs + " milliseconds");

        fw.append("Final positions and velocities\n\n");

        for (int i = 0; i < bodies.length; i++) {
            fw.append("Body " + i + ": " + "p = (" + bodies[i].posx + ", " +
                      bodies[i].posy + "); " + "v = (" + bodies[i].vx +
                      ", " + bodies[i].vy + ")\n");
        }

        fw.append("\nActual steps: " + iterations + "\n");
        fw.append("Minimum time increment: " + minIncrement + "\n");
        fw.append("Maximum time increment: " + maxIncrement + "\n");
        if (barrierTime != 0) {
            fw.append("Total time spent in barriers: " + barrierTime /
                      1000 + " seconds " + barrierTime % 1000 +
                      " milliseconds\n");
            if (barrierCount != 0) {
                fw.append("Average time per barrier: " + barrierTime /
                          (double) barrierCount + " milliseconds\n");
            }
            fw.append("Percent time in barriers: " + barrierTime /
                      (double) computeTime + "\n");
        }

        fw.close();
        System.exit(0);
    }


    public static void updateForces(ParallelBodyFS bodies[], int id) {
        double minDT = initialDT;

        // for each pair of bodies i, j where i != j
        for (int i = id; i < bodies.length; i += numWorkers) {
            for (int j = i + 1; j < bodies.length; j++) {
                // calculate vector pointing from j to i
                double dirx = bodies[i].posx - bodies[j].posx;
                double diry = bodies[i].posy - bodies[j].posy;

                // calculate magnitude of vector
                double magnitude = Math.sqrt(dirx * dirx + diry * diry);
                if (magnitude <= 0.0 || Double.isNaN(magnitude)) {
                    magnitude = Constants.EPSILON;
                } else if (Double.isInfinite(magnitude)) {
                    magnitude = Constants.A_LARGE_VALUE;
                }

                // calculate force
                double forceScalar = Constants.G *
                                     (bodies[i].m + bodies[j].m) /
                                     (magnitude * magnitude * magnitude);

                if (Double.isNaN(forceScalar)) {
                    forceScalar = 0.0;
                } else if (Double.isInfinite(forceScalar)) {
                    forceScalar = -Constants.A_LARGE_VALUE;
                }

                bodies[i].fx[id] += dirx * forceScalar;
                bodies[j].fx[id] -= dirx * forceScalar;
                bodies[i].fy[id] += diry * forceScalar;
                bodies[j].fy[id] -= diry * forceScalar;
            }
        }

        // calculate time interval based on nearest objects and greatest speed
        for (int i = id; i < bodies.length; i += numWorkers) {

            // calculate speed of body i
            double thisSpeed = Math.sqrt(bodies[i].vx * bodies[i].vx +
                                         bodies[i].vy * bodies[i].vy);

            for (int j = i + 1; j < bodies.length; j++) {
                // calculate max speed of i, j
                double speed = thisSpeed +
                               Math.sqrt(bodies[j].vx * bodies[j].vx +
                                         bodies[j].vy * bodies[j].vy);

                // calculate vector pointing from j to i
                double dirx = bodies[i].posx - bodies[j].posx;
                double diry = bodies[i].posy - bodies[j].posy;

                // calculate magnitude of vector
                double magnitude = Math.sqrt(dirx * dirx + diry * diry);
                if (magnitude <= 0.0 || Double.isNaN(magnitude)) {
                    magnitude = Constants.EPSILON;
                } else if (Double.isInfinite(magnitude)) {
                    magnitude = Constants.A_LARGE_VALUE;
                }

                // update time granularity: time to next potential collision
                double increment = (magnitude - bodies[i].r - bodies[j].r) /
                                   speed;

                // if increment valid and less than current minimum
                if (!Double.isInfinite(increment) &&
                    !Double.isNaN(increment) && increment < minDT &&
                    increment > 0.0) {

                    double ddt = Math.min(smallStep, increment);

                    // calculate next positions for i, j

                    double iforcex = 0.0;
                    double iforcey = 0.0;
                    double jforcex = 0.0;
                    double jforcey = 0.0;

                    for (int k = 0; k < bodies[i].fx.length; k++) {
                        iforcex += bodies[i].fx[k];
                        iforcey += bodies[i].fy[k];
                        jforcex += bodies[j].fx[k];
                        jforcey += bodies[j].fy[k];
                    }

                    double nextix = bodies[i].posx +
                                    (bodies[i].vx + (iforcex / bodies[i].m * ddt) / 2) *
                                    ddt;
                    double nextiy = bodies[i].posy +
                                    (bodies[i].vy + (iforcey / bodies[i].m * ddt) / 2) *
                                    ddt;
                    double nextjx = bodies[j].posx +
                                    (bodies[j].vx + (jforcex / bodies[j].m * ddt) / 2) *
                                    ddt;
                    double nextjy = bodies[j].posy +
                                    (bodies[j].vy + (jforcey / bodies[j].m * ddt) / 2) *
                                    ddt;
                    double nextMagnitude = Math.sqrt((nextix - nextjx) *
                                                     (nextix - nextjx) +
                                                     (nextiy - nextjy) *
                                                     (nextiy - nextjy));

                    // if objects will move closer, update minDT
                    if (!Double.isNaN(nextMagnitude) &&
                        !Double.isInfinite(nextMagnitude) &&
                        nextMagnitude < magnitude) {
                        minDT = increment;
                    }
                }
            }
        }

        // CS: update dt if necessary
        if (minDT < dt) {
            mutex.acquireUninterruptibly();
            if (minDT < dt) {
                dt = minDT;
            }
            mutex.release();
        }
    }


    public static void updatePositions(ParallelBodyFS bodies[], int id) {
        double forcex = 0.0;
        double forcey = 0.0;
        for (int i = id; i < bodies.length; i += numWorkers) {
            for (int j = 0; j < bodies[i].fx.length; j++) {
                forcex += bodies[i].fx[j];
                forcey += bodies[i].fy[j];
            }
            // calculate changes in position and velocity
            double dvx = forcex / bodies[i].m * dt;
            double dvy = forcey / bodies[i].m * dt;
            double dpx = (bodies[i].vx + dvx / 2) * dt;
            double dpy = (bodies[i].vy + dvy / 2) * dt;

            // add changes
            bodies[i].vx += dvx;
            bodies[i].vy += dvy;
            bodies[i].posx += dpx;
            bodies[i].posy += dpy;

            forcex = 0.0;
            forcey = 0.0;
        }
    }


    public static void checkCollisions(ParallelBodyFS bodies[], int id) {
        for (int i = id; i < bodies.length; i += numWorkers) {
            for (int j = i + 1; j < bodies.length; j++) {
                // calculate distance between bodies
                double dirx = bodies[i].posx - bodies[j].posx;
                double diry = bodies[i].posy - bodies[j].posy;
                double magnitude = Math.sqrt(dirx * dirx + diry * diry);

                // check for bodies touching within error margin
                if (magnitude - bodies[i].r - bodies[j].r < Constants.EPSILON) {

                    double ddt = Math.min(dt, smallStep);
                    // check bodies touching are moving closer together
                    double iforcex = 0.0;
                    double iforcey = 0.0;
                    double jforcex = 0.0;
                    double jforcey = 0.0;

                    for (int k = 0; k < bodies[i].fx.length; k++) {
                        iforcex += bodies[i].fx[k];
                        bodies[i].fx[k] = 0.0;
                        iforcey += bodies[i].fy[k];
                        bodies[i].fy[k] = 0.0;
                        jforcex += bodies[j].fx[k];
                        jforcey += bodies[j].fy[k];
                    }

                    double nextix = bodies[i].posx +
                                    (bodies[i].vx + (iforcex / bodies[i].m * ddt) / 2) *
                                    ddt;
                    double nextiy = bodies[i].posy +
                                    (bodies[i].vy + (iforcey / bodies[i].m * ddt) / 2) *
                                    ddt;
                    double nextjx = bodies[j].posx +
                                    (bodies[j].vx + (jforcex / bodies[j].m * ddt) / 2) *
                                    ddt;
                    double nextjy = bodies[j].posy +
                                    (bodies[j].vy + (jforcey / bodies[j].m * ddt) / 2) *
                                    ddt;
                    double nextMagnitude = Math.sqrt((nextix - nextjx) *
                                                     (nextix - nextjx) +
                                                     (nextiy - nextjy) *
                                                     (nextiy - nextjy));
                    if (!Double.isNaN(nextMagnitude) &&
                        !Double.isInfinite(nextMagnitude) &&
                        nextMagnitude < magnitude) {
                        if (Constants.DEBUG) {
                            System.out.println("Objects " + i + " and " +
                                               j + " collided.");
                        }
                        collide(bodies[i], bodies[j]);
                    }
                }
            }
        }
    }


    private static void collide(Body a, Body b) {
        double vxa;
        double vya;
        double vxb;
        double vyb;
        double px = b.posx - a.posx;
        double py = b.posy - a.posy;

        // calculate new vx for a
        vxa = b.vx * px * px + b.vy * px * py + a.vx * py * py - a.vy * px *
              py;
        vxa /= px * px + py * py;
        // calculate new vy for a
        vya = b.vx * px * py + b.vy * py * py - a.vx * px * py + a.vy * px *
              px;
        vya /= px * px + py * py;
        // calculate new vx for b
        vxb = a.vx * px * px + a.vy * px * py + b.vx * py * py - b.vy * px *
              py;
        vxb /= px * px + py * py;
        // calculate new vy for b
        vyb = a.vx * px * py + a.vy * py * py - b.vx * px * py + b.vy * px *
              px;
        vyb /= px * px + py * py;

        // update velocities
        a.vx = vxa;
        a.vy = vya;
        b.vx = vxb;
        b.vy = vyb;
    }


    public static void barrier(int id) {

        boolean wasFirst = false;
        long start = 0;
        if (first) {
            start = System.currentTimeMillis();
            barrierMutex.acquireUninterruptibly();
            if (first) {
                first = false;
                barrierMutex.release();
                wasFirst = true;
            } else {
                barrierMutex.release();
            }
        }

        int stage = 0;
        for (int i = 1; i < numWorkers; i *= 2) {
            barrier[id][stage].release();
            barrier[(id + i) % numWorkers][stage].acquireUninterruptibly();
            stage++;
        }

        if (wasFirst) {
            long stop = System.currentTimeMillis();
            barrierTime += stop - start;
            barrierCount++;
            first = true;
        }
    }


    /*
     * Generate a random number in the range -MAX to MAX.
     */
    private static double rand() {
        return (Constants.MAX + Constants.MAX) * rng.nextDouble() -
               Constants.MAX;
    }

}
