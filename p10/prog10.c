/*=============================================================================
 |   Assignment:  Program #10: "Curses; Worms!"
 |
 |       Author:  Daniel Eisenberg
 |     Language:  GNU C (gcc compiled, tested on lectura)
 |   To Compile:  make  (or gcc interf.o linkedlist.o prog10.c -lncurses)
 |
 |        Class:  CSc 352 -- Systems Programming and UNIX
 |   Instructor:  Dr. L. McCann
 |     Due Date:  November 10th, 2011, 5:00pm
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  This program prints a number of wriggling worms to the screen
 |                until the user terminates the program by typing Ctrl-C.
 |
 |        Input:  Two integers on the command line. The first is the number of
 |                worms to draw, between 1 and 20 inclusive. The second is the
 |                length of each worm, between 1 and 50 inclusive.
 |
 |       Output:  The program will print the specified number of worms, each of
 |                the specified length to the screen and each worm will move
 |                randomly about the screen, wrapping horizontally and
 |                vertically. Each worm will emerge from the center of the
 |                screen.
 |
 |    Algorithm:  Each worm is represented by a linked list of ordered pairs.
 |                Initially, these are updated by adding a new element at the
 |                front. Once each worm has been drawn completely, they are
 |                updated by adding an element at their end and removing one
 |                at their front. The new element is determined pseudo-randomly
 |                (using rand()). To ensure each worm is correctly drawn at
 |                each step, a table of how many segments exist at each
 |                terminal position is maintained. Worms move in 8 directions
 |                but each worm has a heading which restricts which directions
 |                it can move at any given step. Worms may only change their
 |                heading by one direction to the left or right; or not change
 |                their heading at all.
 |
 |   Required Features Not Included:  None.
 |
 |   Known Bugs:  None.
 |
 *===========================================================================*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "linkedlist.h"

#define MIN_WORMS      1        /* worm count limits */
#define MAX_WORMS      20
#define MIN_LENGTH     1        /* worm length limits */
#define MAX_LENGTH     50
#define TRUE           1
#define WORM_CHAR      '#'      /* the character used to print worms */
#define DELAY          100000   /* delay interval in microseconds */
#define LEFT           -1       /* worms can turn left or right or not turn */
#define RIGHT          1
#define TURN_OPTIONS   3        /* 3 possibilities */

/* define directions in circular order -- allows arithmetic on direction and 
   turns. e.g., a worm moving up_right who turns RIGHT will be heading right:
            (up_right + RIGHT) % NUM_DIRECTIONS == right
*/
typedef enum {
    up_left, up, up_right, right, down_right, down, down_left, left
} directions;

#define NUM_DIRECTIONS 8        /* the number of possible directions to move */

void outofmemory();
void getdirection(char, int *, int *, int, int, directions *);

int main(int argc, char *argv[]) {

    if (argc < 3) {
        printf("Usage: %s #worms length_of_worms\n", argv[0]);
        return EXIT_FAILURE;
    }

    int wormcount, wormlength;  /* number and length of worms */
    wormcount = atoi(argv[1]);
    wormlength = atoi(argv[2]);

    if (wormcount < MIN_WORMS || wormcount > MAX_WORMS
            || wormlength < MIN_LENGTH || wormlength > MAX_LENGTH) {
        printf("Number of worms must be between ");
        printf("%d and %d inclusive\n", MIN_WORMS, MAX_WORMS);
        printf("Length of worms must be between ");
        printf("%d and %d inclusive\n", MIN_LENGTH, MAX_LENGTH);
        return EXIT_FAILURE;
    }


    LinkedList **worms;         /* array of worms */
    worms = malloc(wormcount * sizeof(LinkedList *));
    if (worms == NULL) {
        outofmemory();
    }
    char w;                      /* loop iterator for worms */
    /* create lists for worms */
    for (w = 0; w < wormcount; w++) {
        if (create_linked_list(&worms[w]) == MALLOC_FAILED)
            outofmemory();
    }

    int rows, cols,             /* terminal dimensions */
        row, col;               /* to store current location to modify */

    c_init();                   /* start ncurses */
    c_refresh();                /* clear screen */

    rows = c_screenRows();      /* get terminal dimensions */
    cols = c_screenCols();

    short *segments;            /* table of worm segments at each position */
    segments = calloc(rows * cols, sizeof(short));
    if (segments == NULL) {
        c_die();
        outofmemory();
    }

    char direction,             /* the next direction to go */
         i;                     /* loop iterator */

    directions heading[MAX_WORMS]; /* holds each worm's facing */

    /* draw first worm segments in center of screen */
    row = rows >> 1;            /* same as rows / 2 */
    col = cols >> 1;            /* same as cols / 2 */
    for (w = 0; w < wormcount; w++) {

        /* add position to linked list */
        if (add_first(worms[w], row, col) == MALLOC_FAILED) {
            c_die();
            outofmemory();
        }

        /* register segment (x, yth position: segment[width*x+y]) */
        segments[cols*row+col] += 1;
        if (segments[cols*row+col] == 1) {
            c_move(row, col);   /* position cursor */
            c_addch(WORM_CHAR); /* print segment */
        }

        /* initialize each worm's facing randomly */
        heading[w] = rand() % NUM_DIRECTIONS;
    }
    c_refresh();                /* refresh for changes */
    usleep(DELAY);              /* wait */

    /* draw worms until full length of each is onscreen */
    for (i = 1; i < wormlength; i++) {
        for (w = 0; w < wormcount; w++) {

            /* get direction worm will move */
            get_first(worms[w], &row, &col);

            /* turn left (-1), right (1), or no turn (0) */
            direction = rand() % TURN_OPTIONS - 1;
            getdirection(direction, &row, &col, rows, cols, &heading[w]);

            /* add new segment of worm */
            if (add_first(worms[w], row, col) == MALLOC_FAILED) {
                c_die();
                outofmemory();
            }
            /* register segment (x, yth postion: segment[width*x+y]) */
            segments[cols*row+col] += 1;
            if (segments[cols*row+col] == 1) {
                /* print segment if not present */
                c_move(row, col);
                c_addch(WORM_CHAR);
            }
        }
        c_move(rows - 1, 0);    /* move cursor out of the way */
        c_refresh();            /* refresh for changes */
        usleep(DELAY);          /* wait */
    }

    /* update worms indefinitely */
    while (TRUE) {  /* loop until user terminates with Ctrl-C */
        for (w = 0; w < wormcount; w++) {

            /* remove oldest segment of worm */
            get_last(worms[w], &row, &col);
            remove_last(worms[w]);

            /* register removed segment (x,yth position: [width*x+y]) */
            segments[cols*row+col] -= 1;
            if (segments[cols*row+col] == 0) {
                /* remove segment from screen if unique */
                c_move(row, col);
                c_addch(' ');
            }

            /* get direction worm will move */
            /* get current position */
            get_first(worms[w], &row, &col);
            /* turn left (-1), right (1), or no turn (0) */
            direction = rand() % TURN_OPTIONS - 1;
            getdirection(direction, &row, &col, rows, cols, &heading[w]);

            /* add new segment to worm */
            if (add_first(worms[w], row, col) == MALLOC_FAILED) {
                c_die();
                outofmemory();
            }
            /* register new segment (x,yth position: [width*x+y]) */
            segments[cols*row+col] += 1;
            if (segments[cols*row+col] == 1) {
                /* print segment if now present */
                c_move(row, col);
                c_addch(WORM_CHAR);
            }
        }
        c_move(rows - 1, 0);    /* move cursor out of the way */
        c_refresh();            /* refresh for changes */
        usleep(DELAY);          /* wait */
    }
}

        /*--------------------------------------------------- outofmemory -----
         |  Function outofmemory
         |
         |  Purpose:  This function prints an error message and terminates the
         |            program in the event the program cannot allocate enough
         |            memory to function correctly.
         |
         |  Parameters: None.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void outofmemory() {
    fprintf(stderr, "ERROR: Insufficient memory\n");
    exit(EXIT_FAILURE);
}

        /*-------------------------------------------------- getdirection -----
         |  Function getdirection
         |
         |  Purpose:  This function determines the direction a worm should move
         |            based on the direction it is moving, it can move forward,
         |            turn left, or turn right. The new position is stored in
         |            the addresses pointed to by row and col, and the
         |            previous direction's value is updated to store the new
         |            direction.
         |
         |  Parameters:
         |      dir (IN) -- Holds the value -1, 0, or 1 to indicate that the
         |              worm will turn left, not turn, or turn right,
         |              respectively.
         |      row (OUT) -- An address to store the new row position of the
         |              worm.
         |      col (OUT) -- An address to store the new column position of the
         |              worm.
         |      rows (IN) -- The total number of rows, to prevent running off
         |              the screen.
         |      cols (IN) -- The total number of columns.
         |      prevdirection (IN/OUT) -- A pointer to the previous direction
         |              the worm moved, will be updated to the new direction
         |              the worm is now moving.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void getdirection(char dir, int *row, int *col, int rows, int cols, 
                                                directions *prevdirection) {
    /* add dir (-1, 0, or 1) to prevdirection and mod by NUM_DIRECTIONS */
    /* forces worms to turn gradually */
    *prevdirection += dir;
    *prevdirection %= NUM_DIRECTIONS;
    /* note that (a + n - 1) % n is congruent to (a - 1) mod n
       when a is positive 
       (that is, this method always returns a nonnegative value) */
    switch (*prevdirection) {
        case up_left:           /* move left one column */
            *col = (*col + cols - 1) % cols;
        case up:                /* move up one row */
            *row = (*row + rows - 1) % rows;
            break;

        case up_right:          /* move up one row */
            *row = (*row + rows - 1) % rows;
        case right:             /* move right one column */
            *col = (*col + 1) % cols;
            break;

        case down_right:        /* move right one column */
            *col = (*col + 1) % cols;
        case down:              /* move down one row */
            *row = (*row + 1) % rows;
            break;

        case down_left:         /* move down one row */
            *row = (*row + 1) % rows;
        case left:              /* move left one column */
        default:                /* why not put a default case here? */
            *col = (*col + cols - 1) % cols;
            break;
    }
}
