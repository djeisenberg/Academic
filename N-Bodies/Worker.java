/*
 * Daniel Eisenberg
 * CSc 422 Spring 2012
 * Parallel Project: N-Bodies Problem with Collisions
 */

public class Worker implements Runnable {

    private int          id;
    private ParallelBody bodies[];


    public Worker(int wid, ParallelBody[] b) {
        id = wid;
        bodies = b;
    }


    public void run() {
        while (true) {
            ParallelCollisions.updateForces(bodies, id);
            ParallelCollisions.barrier(id);
            ParallelCollisions.updatePositions(bodies, id);
            ParallelCollisions.barrier(id);
            ParallelCollisions.checkCollisions(bodies, id);
            ParallelCollisions.barrier(id);
        }
    }

}
