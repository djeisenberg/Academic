/*
 * Daniel Eisenberg
 * CSc 422 Spring 2012
 * Parallel Project: N-Bodies Problem with Collisions
 */

public class WorkerBarrier implements Runnable {

    private int          id;
    private ParallelBodyFS bodies[];


    public WorkerBarrier(int wid, ParallelBodyFS[] b) {
        id = wid;
        bodies = b;
    }


    public void run() {
        while (true) {
            ParallelCollisionsBarrier.updateForces(bodies, id);
            ParallelCollisionsBarrier.barrier(id);
            ParallelCollisionsBarrier.updatePositions(bodies, id);
            ParallelCollisionsBarrier.barrier(id);
            ParallelCollisionsBarrier.checkCollisions(bodies, id);
            ParallelCollisionsBarrier.barrier(id);
        }
    }

}
