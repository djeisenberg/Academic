/*
 * Daniel Eisenberg
 * CSc 422 Spring 2012
 * Parallel Project: N-Bodies Problem with Collisions
 */

public class WorkerFS implements Runnable {

    private int          id;
    private ParallelBodyFS bodies[];


    public WorkerFS(int wid, ParallelBodyFS[] b) {
        id = wid;
        bodies = b;
    }


    public void run() {
        while (true) {
            ParallelCollisionsFS.updateForces(bodies, id);
            ParallelCollisionsFS.barrier(id);
            ParallelCollisionsFS.updatePositions(bodies, id);
            ParallelCollisionsFS.barrier(id);
            ParallelCollisionsFS.checkCollisions(bodies, id);
            ParallelCollisionsFS.barrier(id);
        }
    }

}
