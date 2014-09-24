/*
 * Daniel Eisenberg
 * CSc 422 Spring 2012 distributed project
 *
 * This is an MPMD distributed fractal application which makes use of
 * the Open MPI library for message passing and an X11 server for
 * graphics.
 *
 * This program creates a pixmap and sends pixel locations to copies
 * of a program which will calculate and return values for those
 * pixels.
 */

#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include <X11/Xcms.h>

#include <mpi.h>

#include <stdio.h>
#include <stdlib.h>

#define DEBUG 0
#define FILENAME "image.xbm"
#define GRADIENT_MIN_R 0.2
#define GRADIENT_MIN_G 0.0
#define GRADIENT_MIN_B 0.0
#define GRADIENT_MAX_R 1.0
#define GRADIENT_MAX_G 0.3
#define GRADIENT_MAX_B 0.0

typedef struct ext{
    int x;
    int y;
    double value;
} exterior;

int compar(const void *a, const void *b) {
    const exterior *aa = (const exterior *) a;
    const exterior *bb = (const exterior *) b;

    return (aa->value > bb->value) - (aa->value < bb->value);
}

int main(int argc, char *argv[]) {
    Display         *display;       /* pointer to X Display */
    int             screen_num;     /* number of screen for window */
    Window          win;            /* pointer to window to use */
    unsigned int    display_width,  /* X Display width */
                    display_height; /* " height */
    unsigned int    width,          /* window width */
                    height;         /* " height */
    char            *display_name;  /* address of X Display */
    GC              gc;             /* graphics context for window */
    Pixmap          pixmap;         /* pixmap to draw to window */
    Status          rc;             /* to hold Xlib return statuses */

    display_name = getenv("DISPLAY");
    display = XOpenDisplay(display_name);
    if (display == NULL) {
        fprintf(stderr, "%s: cannot connect to X server '%s'\n", 
                argv[0], display_name);
        exit(1);
    }

    screen_num = DefaultScreen(display);
    display_width = DisplayWidth(display, screen_num);
    display_height = DisplayHeight(display, screen_num);

    width = display_width;
    height = display_height;
    int border_width = 2;
    int depth = 24;

    /* create window */
    XVisualInfo vinfotemplate;
    vinfotemplate.depth = depth;
    vinfotemplate.class = TrueColor;
    int nitems;
    long vinfomask = VisualDepthMask | VisualClassMask;
    XVisualInfo *vinfo;
    vinfo = XGetVisualInfo(display, vinfomask, &vinfotemplate, &nitems);
    if (vinfo == NULL) {
        fprintf(stderr, "XGetVisual failed\n");
        exit(3);
    }
    XSetWindowAttributes att;
    att.background_pixel = WhitePixel(display, screen_num);
    win = XCreateWindow(display, RootWindow(display, screen_num),
                              0, 0, width, height, border_width,
                              depth, CopyFromParent, vinfo->visual,
                              CWBackPixel, &att);
/*
    Colormap cmap = XCreateColormap(display, win, vinfo->visual,
                                    AllocNone);
    XInstallColormap(display, cmap);
*/
    XMapWindow(display, win);

    long eventMask = StructureNotifyMask;
    XSelectInput(display, win, eventMask);

    XEvent event;
    do {
        XNextEvent(display, &event);
    } while (event.type != MapNotify);

    XGCValues gcvalues;

    /* create GC */
    gc = XCreateGC(display, win, 0, &gcvalues);

    if (gc < 0) {
        fprintf(stderr, "XCreateGC: \n");
    }

    XSetForeground(display, gc, BlackPixel(display, screen_num));
    XSetBackground(display, gc, WhitePixel(display, screen_num));

    XFlush(display);

    pixmap = XCreatePixmap(display, DefaultRootWindow(display),
                           width, height, depth);

    XCopyArea(display, win, pixmap, gc, 0, 0, width, height, 0, 0);

    int numprocs, rank, i, j;

    int incount = 0;
    int outcount = 0;
    int total = width * height;
/*
    exterior *extpts = malloc(total * sizeof(exterior));

    if (extpts == NULL) {
        fprintf(stderr, "malloc failed\n");
        exit(2);
    }
*/
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    printf("%d processes\n", numprocs);

    /* allocate space for non-blocking comm requests */
    MPI_Request *send_requests = malloc(numprocs * sizeof(MPI_Request));
    MPI_Request *recv_requests = malloc(numprocs * sizeof(MPI_Request));
    int **send_buf = malloc(numprocs * sizeof(int*));
    double **recv_buf = malloc(numprocs * sizeof(double*));
    if (send_requests == NULL || recv_requests == NULL ||
        send_buf == NULL || recv_buf == NULL) {
        fprintf(stderr, "malloc failed\n");
        MPI_Finalize();
        exit(2);
    }

    send_requests[rank] = recv_requests[rank] = MPI_REQUEST_NULL;

    for (i = 0; i < numprocs; i++) {
        /* will send 2 ints */
        send_buf[i] = malloc(2 * sizeof(int));
        /* will receive 3 doubles */
        recv_buf[i] = malloc(3 * sizeof(double));
        if (send_buf[i] == NULL || recv_buf[i] == NULL) {
            fprintf(stderr, "malloc failed\n");
            MPI_Finalize();
            exit(2);
        }
    }

    /* send rank to each worker */
    for (i = 0; i < numprocs; i++) {
        if (i != rank) {
            MPI_Send(&rank, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
        }
    }

    if (DEBUG) printf("Sent rank\n");

    /* send width and height to each worker */
    for (i = 0; i < numprocs; i++) {
        if (i != rank) {
            send_buf[i][0] = width;
            send_buf[i][1] = height;
            MPI_Send(send_buf[i], 2, MPI_INT, i, 0, MPI_COMM_WORLD);
        }
    }

    if (DEBUG) printf("Sent parameters\n");

    int sent = 0;
    int workers = numprocs - 1;
    /* send 1 (dummy) request to each worker */
    while (sent < workers) {
        int dest = sent;
        if (dest == rank) {
            dest++;
        }
        send_buf[dest][0] = 0;
        send_buf[dest][1] = 0;
        MPI_Isend(send_buf[dest], 2, MPI_INT, dest, 0,
                  MPI_COMM_WORLD, &send_requests[dest]);
        sent++;
    }

    if (DEBUG) printf("Sent initial points\n");

    /* initialize all receive requests */
    int k;
    for (k = 0; k < numprocs; k++) {
        if (k != rank) {
            MPI_Irecv(recv_buf[k], 3, MPI_DOUBLE, k, MPI_ANY_TAG,
                      MPI_COMM_WORLD, &recv_requests[k]);
        }
    }

    if (DEBUG) printf("Initialized receive requests\n");

    for (i = 0; i < width; i++) {
        for (j = 0; j < height; j++) {
            int next_worker;
            MPI_Waitany(numprocs, recv_requests, &next_worker, 
                        MPI_STATUS_IGNORE);
            int x = (int) recv_buf[next_worker][0];
            int y = (int) recv_buf[next_worker][1];
            if (recv_buf[next_worker][2] < 0.0) {
                XDrawPoint(display, pixmap, gc, x, y);
                XCopyArea(display, pixmap, win, gc, x, y, 1, 1, x, y);
                incount++;
            } else {
/*
                extpts[outcount].x = x;
                extpts[outcount].y = y;
                extpts[outcount].value = recv_buf[next_worker][2];
*/ 
                outcount++;
            }

            /* send next outgoing request */
            send_buf[next_worker][0] = i;
            send_buf[next_worker][1] = j;
            MPI_Isend(send_buf[next_worker], 2, MPI_INT, next_worker,
                      0, MPI_COMM_WORLD, &send_requests[next_worker]);

            /* initialize next receive request */
            MPI_Irecv(recv_buf[next_worker], 3, MPI_DOUBLE,
                      next_worker, MPI_ANY_TAG, MPI_COMM_WORLD,
                      &recv_requests[next_worker]);
        }
        XFlush(display);
    }

    int count = incount + outcount;

    if (DEBUG) printf("Sent all points\n");

    while (count < total) {
        int next_worker;
        MPI_Waitany(numprocs, recv_requests, &next_worker,
                    MPI_STATUS_IGNORE);
        int x = (int) recv_buf[next_worker][0];
        int y = (int) recv_buf[next_worker][1];
        if (recv_buf[next_worker][2] < 0) {
            XDrawPoint(display, pixmap, gc, x, y);
            XCopyArea(display, pixmap, win, gc, x, y, 1, 1, x, y);
            incount++;
        } else {
/*
            extpts[outcount].x = x;
            extpts[outcount].y = y;
            extpts[outcount].value = recv_buf[next_worker][2];
*/
            outcount++;
        }
        count++;
    }

    for (k = 0; k < numprocs; k++) {
        if (k != rank) {
            send_buf[k][0] = -1;
            MPI_Send(send_buf[k], 2, MPI_INT, k, 0, MPI_COMM_WORLD);
        }
    }

    if (DEBUG) printf("Sent stop signals\n");

    MPI_Finalize();

    XFlush(display);
/*
    qsort(extpts, outcount, sizeof(exterior), compar);

    int numcolors = 1000;
    double denominator = 1.0 / numcolors;
    double rlen = GRADIENT_MAX_R - GRADIENT_MIN_R;
    double glen = GRADIENT_MAX_G - GRADIENT_MIN_G;
    double blen = GRADIENT_MAX_B - GRADIENT_MIN_B;

    XcmsColor colors[numcolors];
    for (i = 0; i < numcolors; i++) {
        double gradr, gradg, gradb, rankorder;
        rankorder = i * denominator;
        gradr = rlen * rankorder + GRADIENT_MIN_R;
        gradg = glen * rankorder + GRADIENT_MIN_G;
        gradb = blen * rankorder + GRADIENT_MIN_B;
        char colorname[128];
        snprintf(colorname, 128, "RGBi:%f/%f/%f", gradr, gradg, gradb);
        XcmsColor color, unused;
        XcmsAllocNamedColor(display, cmap, colorname, &color, &unused,
                            XcmsRGBFormat);
    }

    for (i = 0; i < outcount; i++) {
        int index = i % numcolors;
        XSetForeground(display, gc, colors[index].pixel);
        int x = extpts[i].x;
        int y = extpts[i].y;
        XDrawPoint(display, pixmap, gc, x, y);
        XCopyArea(display, pixmap, win, gc, x, y, 1, 1, x, y);
        XFlush(display);
    }
*/
    XCopyArea(display, pixmap, win, gc, 0, 0, width, height, 0, 0);
    XWriteBitmapFile(display, FILENAME, pixmap, width, height, -1, -1);
    XFreePixmap(display, pixmap);

    XSync(display, False);
    printf("Press any key to terminate");
    fflush(stdout);
    fgetc(stdin);
    XCloseDisplay(display);

    if (DEBUG){ printf("i: %d, j: %d, width: %d, height: %d\n", i, j, width, height);printf("interior points: %d\nexterior points: %d\n", incount, outcount);}
}
