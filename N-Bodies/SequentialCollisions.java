/*
 * Daniel Eisenberg
 * CSc 422 Spring 2012
 * Parallel Project: N-Bodies Problem with Collisions
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SequentialCollisions {

    private static String filename     = Constants.FILENAME;

    private static int    numBodies    = 2;

    private static long   steps        = 1000L;
    private static long   maxSteps     = Long.MAX_VALUE;

    private static Long   seed         = null;

    private static double mass         = 0.0;
    private static double radius       = 0.0;
    private static double dt           = Constants.DT;
    private static double smallStep    = 1e-12;
    private static double time         = 0.0;
    private static double minIncrement = Double.MAX_VALUE;
    private static double maxIncrement = Double.MIN_VALUE;
    private static double initialDT;

    private static Random rng;


    public static void main(String[] args) throws IOException {
        // resolve arguments

        if (args.length > 1) {
            numBodies = Integer.parseInt(args[1]);
        } else {
            numBodies = Constants.PARAMETERS.length / 6;
        }

        if (numBodies <= 0) {
            System.out.println("Please specify a positive number of bodies.");
            System.exit(0);
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
        Body bodies[];
        if (args.length <= 1) {
            bodies = new Body[numBodies];
            for (int i = 0; i < bodies.length; i++) {
                int j = i * 6;
                bodies[i] = new Body(Constants.PARAMETERS[j],
                                     Constants.PARAMETERS[j + 1],
                                     Constants.PARAMETERS[j + 2],
                                     Constants.PARAMETERS[j + 3],
                                     Constants.PARAMETERS[j + 4],
                                     Constants.PARAMETERS[j + 5]);
            }

        } else {
            bodies = new Body[numBodies];
            for (int i = 0; i < numBodies; i++) {
                bodies[i] = new Body(rand(), rand(), rand(), rand(), mass,
                                     radius);
            }
        }

        fw.append("Initial positions and velocities\n\n");

        for (int i = 0; i < bodies.length; i++) {
            fw.append("Body " + i + ": " + "p = (" + bodies[i].posx + ", " +
                      bodies[i].posy + "); " + "v = (" + bodies[i].vx +
                      ", " + bodies[i].vy + ")\n");
        }
        fw.append("\n");

        long start = System.currentTimeMillis();

        // execute
        initialDT = dt;
        double runtime = initialDT * steps;
        long iterations = 0;
        while (time < runtime && iterations < maxSteps) {
            update(bodies);
            iterations++;
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

        fw.close();
    }


    private static void update(Body bodies[]) {
        double minDT = initialDT;

        // for each pair of bodies i, j where i != j
        for (int i = 0; i < bodies.length; i++) {
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
                    forceScalar = -1.0;
                } else if (Double.isInfinite(forceScalar)) {
                    forceScalar = -Constants.A_LARGE_VALUE;
                }

                bodies[i].fx += dirx * forceScalar;
                bodies[j].fx -= dirx * forceScalar;
                bodies[i].fy += diry * forceScalar;
                bodies[j].fy -= diry * forceScalar;
            }
        }

        for (int i = 0; i < bodies.length; i++) {

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
                    double nextix = bodies[i].posx +
                                    (bodies[i].vx + (bodies[i].fx /
                                                     bodies[i].m * ddt) / 2) *
                                    ddt;
                    double nextiy = bodies[i].posy +
                                    (bodies[i].vy + (bodies[i].fy /
                                                     bodies[i].m * ddt) / 2) *
                                    ddt;
                    double nextjx = bodies[j].posx +
                                    (bodies[j].vx + (bodies[j].fx /
                                                     bodies[j].m * ddt) / 2) *
                                    ddt;
                    double nextjy = bodies[j].posy +
                                    (bodies[j].vy + (bodies[j].fy /
                                                     bodies[j].m * ddt) / 2) *
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

        // calculate time interval based on nearest objects and greatest speed
        dt = minDT;

        if (minDT < minIncrement) {
            minIncrement = minDT;
        }
        if (minDT > maxIncrement) {
            maxIncrement = minDT;
        }

        time += dt;

        for (int i = 0; i < bodies.length; i++) {
            // calculate changes in position and velocity
            double dvx = bodies[i].fx / bodies[i].m * dt;
            double dvy = bodies[i].fy / bodies[i].m * dt;
            double dpx = (bodies[i].vx + dvx / 2) * dt;
            double dpy = (bodies[i].vy + dvy / 2) * dt;

            // add changes
            bodies[i].vx += dvx;
            bodies[i].vy += dvy;
            bodies[i].posx += dpx;
            bodies[i].posy += dpy;
        }

        // check for collisions
        for (int i = 0; i < bodies.length; i++) {
            for (int j = i + 1; j < bodies.length; j++) {
                // calculate distance between bodies
                double dirx = bodies[i].posx - bodies[j].posx;
                double diry = bodies[i].posy - bodies[j].posy;
                double magnitude = Math.sqrt(dirx * dirx + diry * diry);

                // check for bodies touching within error margin
                if (magnitude - bodies[i].r - bodies[j].r < Constants.EPSILON) {

                    double ddt = Math.min(dt, smallStep);
                    // check bodies touching are moving closer together
                    double nextix = bodies[i].posx +
                                    (bodies[i].vx + (bodies[i].fx /
                                                     bodies[i].m * ddt) / 2) *
                                    ddt;
                    double nextiy = bodies[i].posy +
                                    (bodies[i].vy + (bodies[i].fy /
                                                     bodies[i].m * ddt) / 2) *
                                    ddt;
                    double nextjx = bodies[j].posx +
                                    (bodies[j].vx + (bodies[j].fx /
                                                     bodies[j].m * ddt) / 2) *
                                    ddt;
                    double nextjy = bodies[j].posy +
                                    (bodies[j].vy + (bodies[j].fy /
                                                     bodies[j].m * ddt) / 2) *
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
            // reset force
            bodies[i].fx = 0.0;
            bodies[i].fy = 0.0;
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


    /*
     * Generate a random number in the range -MAX to MAX.
     */
    private static double rand() {
        return (Constants.MAX + Constants.MAX) * rng.nextDouble() -
               Constants.MAX;
    }

}
