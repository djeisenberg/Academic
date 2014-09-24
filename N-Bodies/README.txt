Daniel Eisenberg
CSc 422 Spring 2012
Parallel Project: N-Bodies Problem with Collisions

Program: SequentialCollisions; ParallelCollisions; ParallelCollisionsFS; ParallelCollisionsBarrier

To run:

java [program] [# of workers] [# of bodies] [mass of bodies] [# of steps] 
        [radius of bodies] [maximum time per step] [random seed]

The [# of workers] argument is present for both programs but ignored by 
the sequential solution.

Note that the time per step parameter and the number of steps parameter 
are approximate: the programs adaptively calculate the next time step 
using the time per step parameter as an upper bound until the cumulative 
elapsed simulation time exceeds (steps) * (time per step).

All arguments are optional; if either program is run with one or no 
arguments, it will use the test case parameters supplied in the 
Constants.java file.

If at least two arguments are provided, the specified number of bodies 
will be generated with random initial positions and velocities whose 
component-wise values are bounded by the MAX value in the Constants.java 
file. If a seed is provided, the values will be generated using that seed.

If the DEBUG value in Constants.java is set to true, each collision will 
be printed to stdout.

Timing tests were performed using the following arguments:

java SequentialCollisions 1 300 1.0 1000 1.0 1.0 99
java ParallelCollisions* i 300 1.0 1000 1.0 1.0 99
(for i in {1..16})