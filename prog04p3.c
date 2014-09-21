/*=============================================================================
 |   Assignment: Program #4: Basic Shell Scripting and C Arrays
 |                  Part 3: Magic Squares
 |
 |       Author:  Daniel Eisenberg
 |     Language:  C, tested on lectura using gcc compiled code
 |   To Compile:  gcc, no arguments necessary
 |
 |        Class:  CSc 352 -- Systems Programming and UNIX
 |   Instructor:  Lester McCann
 |     Due Date:  September 22nd, 2011, 5:00pm
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  This C program attempts to create a magic square and verifies
 |                  whether the resulting square is indeed a magic square.
 |                  A magic square of order n is here defined as an n x n 
 |                  array, for n an odd integer with n >= 3, which contains
 |                  each integer 1 through n * n inclusive in an arragement
 |                  which results in the sums of the values in each row, each 
 |                  column and both diagonals being equal.
 |                  This sum is called the magic constant (for a magic square
 |                  of order n).
 |                  The magic constant for an n x n square can be calculated 
 |                  using the formula
 |                                      n(n*n + 1)/2.
 |
 |        Input:  Three unsigned integers, from stdin, the first being the
 |                  order of a magic square, the second and third being the 
 |                  zero-indexed row and column positions, respectively, of the
 |                  number 1 in the candidate magic square. The order should be
 |                  an odd number n between 3 and 31 inclusive. The row and 
 |                  column should each be between 0 and n - 1 inclusive.
 |
 |       Output:  The candidate magic square resulting from the Siamese method
 |                  (or de la Loubere's method) (see Algorithm section below) 
 |                  being applied to the input values; followed by the magic
 |                  constant for a magic square of order n; finally a
 |                  determination of whether the candidate magic square is
 |                  actually a magic square, and, if it is not, a justification
 |                  of why it is not.
 |
 |                  Example output format (for input of "3 2 1"):
 |                    3   5   7
 |                    4   9   2
 |                    8   1   6
 |                  15 is the magic constant
 |                  No - The upper-left-to-lower-right diagonal has a sum of 18.
 |                  
 |                  The format above only allows for 3-digit cell values, so
 |                  this program only functions for magic squares of order 31
 |                  or less. Note also that for orders larger than 19, the
 |                  output will not appear in an easily readable format on
 |                  terminals of the standard 80 character width.
 |
 |                  If the order (n) is not an odd number between 3 and 31
 |                  inclusive, or if the row value or column value is not
 |                  between 0 and n - 1 inclusive; an error message will be
 |                  printed and the program will exit.
 |
 |    Algorithm:  An implementation of the Siamese method of finding magic
 |                  squares of order n. 
 |                  First, the number 1 is placed in a specific location of an
 |                  empty n x n array. 
 |                  Then for each number k in the range 1 to n * n - 1 
 |                  inclusive, for k in zero-indexed position (r, c),
 |                  k + 1 is placed in the location immediately above and to
 |                  the right of the position of k, modulo n.
 |                  That is, 
 |                  k + 1 is placed in location ((r - 1) % n, (c + 1) % n).
 |                  If this position is occupied, instead place k + 1 in
 |                  the position below the position of k, modulo n.
 |                  That is, k + 1 is placed in location ((r + 1) % n, c).
 |                  Repeat this process until every cell of the array is filled
 |                  by a unique integer in the range 1 to n * n inclusive.
 |
 |                  To check the resulting candidate magic square is a magic
 |                  square, we must first calculate the value of the magic
 |                  constant for a magic square of order n. This is done using
 |                  the formula n(n * n + 1)/2.
 |
 |                  With the magic constant calculated, we now need to compare
 |                  the sums of each column, each row, and each of the two
 |                  diagonals, against the magic constant. If all these sums
 |                  are equal, the candidate magic square is a magic square.
 |
 |
 |   Required Features Not Included:  None known.
 |
 |   Known Bugs:  None.
 |
 *===========================================================================*/


#include <stdio.h>

#define MAX_ORDER 31

void makeCandidateSquare(unsigned int [MAX_ORDER][MAX_ORDER], 
        const unsigned int, const unsigned int, const unsigned int);

void checkMagic(unsigned int [MAX_ORDER][MAX_ORDER], const unsigned int);

int main(void) {

unsigned int    order,      /* the order of the square to be checked */
                one_row,    /* the row the number 1 should be placed in */
                one_col;    /* the column the number 1 should be placed in */
/* get input and ensure 3 arguments are read in */
if (scanf("%u%u%u", &order, &one_row, &one_col) < 3) {
    printf("Please enter order, row position, column position.\n");
    return;
}

/* check order odd number between 3 and 31 */
if (order < 3 || order > 31 || order % 2 != 1) {
    printf("Order must be an odd number between 3 and 31 inclusive.\n");
    return;
}

/* check row and column valid indices for array of dimensions order by order */
if (one_row >= order || one_col >= order) {
    printf("The row and column positions must be in the range 0 to ");
    printf("(order - 1) inclusive.\n");
    return;
}

/* valid input */

unsigned int square[MAX_ORDER][MAX_ORDER]; /* array will hold our candidate 
                                                square */

/* initialize square */
int i, j; /* for loop iterators */
for (i = 0; i < order; i++)
    for (j = 0; j < order; j++)
        square[i][j] = 0;

/* generate candidate square */
makeCandidateSquare(square, order, one_row, one_col);
/* check whether candidate square is a magic square and print results */
checkMagic(square, order);
}

        /*-------------------------------------------- makeCandidateSquare ----
         |  Function makeCandidateSquare
         |
         |  Purpose:  This function creates a candidate magic square by the
         |              Siamese method (described in the Algorithm section of
         |              the external comment block above).
         |
         |  Parameters:
         |      square (OUT) -- This is an order x order array of type
         |              unsigned int. The array is assumed to be initialized
         |              to zero. This array will be used to store the candidate
         |              magic square created by this function.
         |
         |      order (IN) -- This is an unsigned int representing the number
         |              rows (or columns) in the square array square. This
         |              is assumed to be an odd value between 3 and 31
         |              inclusive. This parameter is unchanged by the function.
         |
         |      one_row (IN) -- This is an unsigned int representing the row
         |              location where the number 1 should be placed in the
         |              array square. This value is assumed to be in the bounds
         |              of the array's height. This parameter is unchanged by
         |              the function.
         |
         |      one_col (IN) -- This is an unsigned int representing the column
         |              location where the number 1 should be placed in the
         |              array square. This value is assumed to be in the bounds
         |              of the array's width. This parameter is unchanged by
         |              the function.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void makeCandidateSquare(unsigned int square[MAX_ORDER][MAX_ORDER], 
                            const unsigned int order,
                            const unsigned int one_row, 
                            const unsigned int one_col) {
    /* place 1 and store row and column in last_row and last_col */
    square[one_row][one_col] = 1;
    unsigned int i,                  /* loop iterator */
                 last_row = one_row, /* previous row position */
                 last_col = one_col; /* previous column position */
    for (i = 2; i <= order * order; i++) {
        /* first move up and right */
        /* note: (x + n - 1) % n is the same as x - 1 in the field of n elts. */
        unsigned int next_row, /* stores next row test value */
                     next_col; /* stores next column test value */
        next_row = (last_row + order - 1) % order;
        next_col = (last_col + 1) % order;
        if (square[next_row][next_col] == 0) {
            /* no value, put i in this cell */
            square[next_row][next_col] = i;
            /* update previous locations */
            last_row = next_row;
            last_col = next_col;
        /* otherwise move down */
        } else {
            next_row = (last_row + 1) % order;
            /* no value, put i in this cell */
            square[next_row][last_col] = i;
            /* update previous locations (column didn't change) */
            last_row = next_row;
        }
    }
}

        /*------------------------------------------------- checkMagic --------
         |  Function checkMagic
         |
         |  Purpose:  This function checks a square array to see whether its
         |              contents describe a magic square. It prints the
         |              contents of the array, the magic constant for a magic
         |              square of order the length of the given array, and
         |              whether the array passed in describes a magic square
         |              including a justification if it does not.
         |
         |  Parameters:
         |      square (IN) -- This is a square array of size order x order to
         |              be checked as a magic square. It is expected to contain
         |              each number from 1 to n * n inclusive. This parameter
         |              is unchanged by the function.
         |
         |      order (IN) -- This is an unsigned int representing the number
         |              rows (or columns) in the square array square. This
         |              is assumed to be an odd value between 3 and 31
         |              inclusive. This parameter is unchanged by the function.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void checkMagic(unsigned int square[MAX_ORDER][MAX_ORDER], 
                const unsigned int order) {
    /* calculate magic constant */
    unsigned int magic_constant; /* the magic constant for length order */
    magic_constant = (order * (order * order + 1)) >> 1;

    unsigned int row_sums[MAX_ORDER] = {0}, /* stores sums of rows' values */
                 col_sums[MAX_ORDER] = {0}, /* stores sums of columns' values */
                 main_diag_sum = 0,         /* stores the sum of 
                                            upper-left-to-lower-right 
                                            diagonal */
                 other_diag_sum = 0;        /* stores the sum of 
                                            lower-left-to-upper-right 
                                            diagonal */

    /* check sums of rows, columns, diagonals, and print square */
    int i, j; /* loop iterators */
    for (i = 0; i < order; i++) {
        for (j = 0; j < order; j++) {
            unsigned int cell = square[i][j]; /* current cell value */
            /* update sums */
            row_sums[i] += cell;
            col_sums[j] += cell;
            if (i == j)
                main_diag_sum += cell;
            if (i + j == order - 1)
                other_diag_sum += cell;

            /* print cell */
            printf("%3u", cell);
            /* print trailing space after all but last column */
            if (j != order - 1)
                printf(" ");
            /* print newline after last column */
            else
                printf("\n");
        }
    }

    printf("%u is the magic constant\n", magic_constant);

    /* test sums for equality to magic_constant */
    if (main_diag_sum != magic_constant) {
        printf("No - the upper-left-to-lower-right diagonal ");
        printf("has a sum of %u\n", main_diag_sum);
        return;
    }
    if (other_diag_sum != magic_constant) {
        printf("No - the upper-right-to-lower-left diagonal ");
        printf("has a sum of %u\n", other_diag_sum);
        return;
    }
    for (i = 0; i < order; i++) {
        if (row_sums[i] != magic_constant) {
            printf("No - row %d has a sum of %u\n", i, row_sums[i]);
            return;
        }
        if (col_sums[i] != magic_constant) {
            printf("No - column %d has a sum of %u\n", i, col_sums[i]);
            return;
        }
    }
    /* all equality tests passed */
    printf("Yes, this is a magic square.\n");
}
