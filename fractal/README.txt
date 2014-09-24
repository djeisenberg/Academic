Daniel Eisenberg
CSc 422, Spring 2012
Distributed project

Files:
fractal.c fractalcalc.c makefile hosts run.bsh

These files are sufficient to run the program from lectura via ssh -Y.
The program uses a MIMD, master-slave paradigm.

The script run.bsh can be used to compile and execute the program
assuming a valid hostfile is present.

The first argument to run.bsh is interpreted as the number of copies
of fractalcalc to launch. Only one copy of fractal is executed.

The script passes any arguments to fractalcalc:
The first argument is ignored, the second is taken to be the
maximum iteration count for calculations, the next 4 arguments are
interpreted as the window parameters in the complex plane --
for the Mandelbrot set, this will ideally be within
-2.5 1 -1 1 --
which are interpreted as min x, max x, min y, max y.
The foregoing are the parameters with which the program will be
executed if no parameters are specified.
The next argument, if present, is interpreted as the real power
(ideally positive) for the multibrot set. By default the power is 2
resulting in a Mandelbrot set calculation.
Finally, if any additional arguments are provided, the Mandelbar set
will be used.

Example parameters:

./run.bsh 20 10000 -0.3 0.3 0.5 0.9

will provide a closeup of the largest upper bulb on the main cardioid
of the Mandelbrot set calculated by 20 copies of fractalcalc, while

./run.bsh

will provide a full view of the Mandelbrot set calculated by 8 copies
of fractalcalc, and is equivalent to

./run.bsh 8 10000 -2.5 1.0 -1.0 1.0 2

The command

./run.bsh 10 256 -1.8 -1.74 -0.02 0.02

will provide a view of the largest copy of the set on the negative
x-axis line.

This program is designed to run on multiple machines in the CS
department.
It is designed to run via an ssh with trusted X11 forwarding (ssh -Y).
There should be one instance of fractal and one or more instances of
fractalcalc.
The script run.bsh is coded to launch fractal (and no other
processes) on lectura, which requires that the hostfile include
lectura.

Setting up an ssh-agent is helpful but not required for execution.

A pixmap file will be created at completion of the execution with a
filename given by the #define constant FILENAME in fractal.c.

Full instructions (to execute on lectura):
-- login to lectura with trusted X11 forwarding
    $ ssh -Y user@lectura.cs.arizona.edu
-- (OPTIONAL) setup ssh-agent:
    $ ssh-keygen -t rsa
    -- choose directory or use default
    -- choose and confirm passphrase
    -- launch ssh-agent
        $ eval `ssh-agent`
    -- add rsa key
        $ ssh-add ~/.ssh/id_rsa
-- navigate to directory containing project
-- (compile and) execute program:
    $ ./run.bsh [# slaves] [maxcount] [xmin] [xmax] [ymin] [ymax] [pow]
