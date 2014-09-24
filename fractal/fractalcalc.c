/*
 * Daniel Eisenberg
 * CSc 422 Spring 2012 distributed project
 *
 * This is an MPMD distributed fractal application which makes use of
 * the Open MPI library for message passing and an X11 server for
 * graphics.
 *
 * This program receives points, maps these points to the complex
 * plane, and determines set inclusion (for the Mandelbrot set, for
 * example), before returning the point and an indication of the
 * set inclusion calculation result.
 */

#include <mpi.h>

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <complex.h>

#define DEBUG           0
#define XMIN            -2.5
#define YMIN            -1.0
#define XMAX            1.0
#define YMAX            1.0
#define POWER           2
#define MAX_ITERATIONS  10000

void main(int argc, char *argv[]) {
    double xmin = XMIN;
    double xmax = XMAX;
    double ymin = YMIN; 
    double ymax = YMAX;
    double p = POWER;
    double xlen, ylen;
    long maxcount = MAX_ITERATIONS;
    char mandelbrot = (POWER == 2);
    char tricorn = 0;

    if (argc > 2) {
        maxcount = atol(argv[2]);
    }

    if (argc > 6) {
        xmin = atof(argv[3]);
        xmax = atof(argv[4]);
        ymin = atof(argv[5]);
        ymax = atof(argv[6]);
    }

    if (argc > 7) {
        double p = atof(argv[7]);
        mandelbrot = (p - 2.0 < 0.01);
    }

    if (argc > 8) {
        mandelbrot = 0;
        tricorn = 1;
    }

    if (DEBUG){ int i; for (i = 0; i < argc; i++)printf("argv[%d]: %s\n", i, argv[i]);}

    xlen = xmax - xmin;
    ylen = ymax - ymin;

    if (DEBUG) printf("maxcount: %ld\n", maxcount);
    if (DEBUG) printf("xmin: %f\nxmax: %f\nymin: %f\nymax: %f\n", xmin, xmax, ymin, ymax);
    if (DEBUG) printf("xlen: %f\nylen: %f\n", xlen, ylen);

    int rank;
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    int master; /* master program rank */
    MPI_Recv(&master, 1, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG,
             MPI_COMM_WORLD, MPI_STATUS_IGNORE);

    if (DEBUG) printf("%d: master has rank %d\n", rank, master);

    int max[2]; /* max incoming point values */
    MPI_Recv(max, 2, MPI_INT, master, MPI_ANY_TAG, MPI_COMM_WORLD,
             MPI_STATUS_IGNORE);

    if (DEBUG) printf("%d: parameters are %d,%d\n", rank, max[0], max[1]);

    double convertx = xlen / max[0];
    double converty = ylen / max[1];
/*
    double divln2 = 1 / log(2);
    double lnlnmax = log(log(MAX_ITERATIONS));
*/
    while (1) {
        int point[2];
        MPI_Recv(point, 2, MPI_INT, master, MPI_ANY_TAG,
                 MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        if (point[0] == -1) {
            break;
        }

        /* scale point */
        double complex c = point[0] * convertx + xmin;
        c += I * (-1 * point[1] * converty + ymax);

        /* determine inclusion */
        double complex z = 0;
        long count = 0;

        /* Mandelbrot set */
        if (mandelbrot) {
            while (cabs(z) < 2 && count < maxcount) {
                z = z * z + c;
                count++;
            }

        /* tricorn (mandelbar) */
        } else if (tricorn) {
            while (cabs(z) < 2 && count < maxcount) {
                z = conj(z);
                z = z * z + c;
                count++;
            }

        /* multibrot set */
        } else {
            while (cabs(z) < 2 && count < maxcount) {
                z = cpow(z, p) + c;
                count++;
            }
        }

        double result[3];
        result[0] = (double) point[0];
        result[1] = (double) point[1];
        if (count == maxcount) {
            /* in set */
            result[2] = -1.0;
        } else {
            result[2] = (double) count;
/*
            double zn = cabs(z);
            result[2] = count + (lnlnmax - log(log(z*z))) * divln2;
*/
        }

        MPI_Send(result, 3, MPI_DOUBLE, master, 0, MPI_COMM_WORLD);
    }
    MPI_Finalize();
}
