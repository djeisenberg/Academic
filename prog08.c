/*=============================================================================
 |   Assignment:  Program #8: Recursive Flood-Fill
 |
 |       Author:  Daniel Eisenberg
 |     Language:  C, tested using gcc on lectura
 |   To Compile:  gcc, no required flags
 |
 |        Class:  CSc 352 -- Systems Programming and UNIX
 |   Instructor:  Lester McCann
 |     Due Date:  October 27th, 2011, 5:00pm
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  This program executes a recursive flood-fill algorithm on a
 |                  text file mock-up of an image under a 10-color palette,
 |                  displaying the result to stdout. The flood-fill algorithm
 |                  is 4-connected.
 |
 |        Input:  The path to an "image file", optionally followed by any
 |                  number of flood-fill parameters.
 |
 |                  An "image file" is any text file meeting the following
 |                  specifications:
 |                      1) The first line consists of 2 whitespace delimited
 |                          positive integers whose values are at most 50.
 |                          These integers are the row count and column count,
 |                          respectively.
 |                      2) The file contains a number of additional lines equal
 |                          to the row count, each having a number of
 |                          whitespace delimited, single-digit, positive
 |                          decimal values equal to the column count.
 |
 |                  The flood-fill parameters indicate the position at which
 |                  a flood-fill should begin, and the color to be used.
 |
 |                  A flood-fill parameter is a triple (3-tuple) whose first
 |                  element is an integer from 0 to one less than the row count
 |                  inclusive, whose second element is an integer from 0 to one
 |                  less than the column count inclusive, and whose third
 |                  element is an integer in the range 0 to 9 inclusive.
 |
 |       Output:  The "image" resulting from executing the cumulative
 |                  flood-fill operations indicated by the given flood-fill
 |                  parameters. The operations will be executed in the
 |                  left-to-right order of the parameter arguments.
 |
 |    Algorithm:  First a 2D image array is built from the image file and this
 |                  image is printed to stdout. Next, for each flood-fill
 |                  instruction provided, the recursive flood-fill algorithm is
 |                  executed on the image array, and the resulting image array
 |                  is printed to stdout.
 |
 |   Required Features Not Included:  None.
 |
 |   Known Bugs:  None.
 |
 *===========================================================================*/

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

#define MAX_SIZE 50

int makeimage(FILE*, char*, short, short);
int makepixel(FILE*, char*, short, short, short, short);
void fill(char*, short, short, char**, int, int*);
void floodfill(char*, short, short, short, short, char, char, int*);
void printimage(char*, short, short);
void printpixel(char*, short, short, short, short);
void advancetonextline(FILE*);
int validate(char**, int, short, short);
void badfile(char*);

int main(int argc, char **argv) {

    /* check arguments */
    if (argc < 2) {
        printf("Usage: %s filename parameters\n", *argv);
        printf("parameters consist of a row index, a column index, and a ");
        printf("color digit.\n");
        printf("Zero to many parameters may be specified.\n");
        return 1;
    }
    if ((argc - 2) % 3 != 0) {
        printf("Invalid flood-fill parameters.\n");
        printf("Specify row index, column index, and color digit for each ");
        printf("flood-fill parameter.\n");
        return 1;
    }

    /* open image file */
    FILE *file;     /* image file pointer */
    file = fopen(*(argv+1), "r");
    if (!file) {
        printf("ERROR: Cannot open file %s, exiting.\n", *(argv+1));
        return 2;
    }

    /* get image size from file */
    short   rows,   /* number of rows in image */
            cols;   /* number of columns in image */
    if (fscanf(file, "%hd %hd", &rows, &cols) < 2) {
        badfile(*(argv+1));
        printf("First line of image file must specify image dimensions.\n");
        return 3;
    }
    if (rows < 1 || cols < 1 || rows > MAX_SIZE || cols > MAX_SIZE) {
        badfile(*(argv+1));
        printf("Image dimensions must be in range 1 to 50 inclusive.\n");
        return 3;
    }

    /* check flood-fill instructions valid */
    if (validate(argv+2, argc-2, rows, cols)) {
        printf("Invalid flood-fill parameters.\n");
        printf("Row and column values must be in range of image size.\n");
        printf("Color value must be between 0 and 9 inclusive.\n");
        fclose(file);
        return 1;
    }

    /* make image */
    char *image;    /* holds the image content */
    image = malloc(rows * cols); /* using char, only need 1 byte per datum */
    if (!image) {
        printf("ERROR: Insufficient memory available.\n");
        return 4;
    }
    advancetonextline(file);
    if (makeimage(file, image, rows, cols)) { /* problem creating image */
        badfile(*(argv+1));
        fclose(file);
        free(image);
        return 5;
    }
    fclose(file);

    int changecount = 0;    /* holds the number of pixels changed*/
    fill(image, rows, cols, argv+2, argc-2, &changecount);
    free(image);
    printf("A total of %d pixels were changed.\n", changecount);
    return 0;
}

        /*------------------------------------------------- makeimage ---------
         |  Function makeimage
         |
         |  Purpose:  Attempts to create an array representation of the image
         |              stored in file. This array will be used throughout the
         |              program's execution. This function calls the recursive
         |              function makepixel to populate the array and validate
         |              the image file.
         |
         |  Parameters:
         |      file (IN) -- A FILE pointer to the file containing the image
         |              to be read in.
         |      image (OUT) -- An array to store the image in as it is read in.
         |      numrows (IN) -- The height dimension of the array.
         |      numcols (IN) -- The width dimension of the array.
         |
         |  Returns:  0 if the image was successfully created, 1 otherwise.
         *-------------------------------------------------------------------*/

int makeimage(FILE *file, char *image, short numrows, short numcols) {
    if (makepixel(file, image, numrows, numcols, 0, 0)) {
        return 1;
    } else {
        printf("Original Image:\n");
        printimage(image, numrows, numcols);
        return 0;
    }
}

        /*------------------------------------------------- makepixel ---------
         |  Function makepixel
         |
         |  Purpose:  This recursive function populates one "pixel" of the
         |              parameter image. This function is initially called by
         |              makeimage. The correct location of the current pixel
         |              is maintained by preincrementing the pointer image
         |              at each recursive call.
         |
         |  Parameters:
         |      file (IN) -- A FILE pointer to the file containing the image to
         |              be read in.
         |      image (IN/OUT) -- A pointer to the position where the next
         |              pixel should be stored.
         |      numrows (IN) -- The height of the image to be stored.
         |      numcols (IN) -- The width of the image to be stored.
         |      row (IN) -- The row position of the pixel to be read in.
         |      col (IN) -- The column position of the pixel to be read in.
         |
         |  Returns:  0 if the image was successfully created, 1 otherwise.
         *-------------------------------------------------------------------*/

int makepixel(FILE *file, char *image, short numrows, short numcols,
                                                short row, short col) {
    char    pixel;      /* holds next pixel */
    short   nextrow,    /* holds row value of next pixel */
            nextcol;    /* holds column value of next pixel */
    if (fscanf(file, "%c", &pixel) == 1 && isdigit(pixel)) {
        *image = pixel;
        if (col + 1 == numcols) {
            advancetonextline(file);
            nextcol = 0;
            nextrow = row + 1;
            if (nextrow == numrows) {
                return 0;
            }
        } else {
            nextcol = col + 1;
            nextrow = row;
        }
        return makepixel(file, ++image, numrows, numcols, nextrow, nextcol);
    } else {
        return 1;
    }
}

        /*------------------------------------------------- fill --------------
         |  Function fill
         |
         |  Purpose:  This function calls floodfill and recurses until all
         |              flood-fill instructions supplied to main have been
         |              executed.
         |
         |  Parameters:
         |      image (IN) -- A pointer to the image data on which the
         |              flood-fill(s) is(/are) to be executed.
         |      numrows (IN) -- The height of the image.
         |      numcols (IN) -- The width of the image.
         |      args (IN) -- The flood-fill instructions passed to main.
         |      argcount (IN) -- The length of args.
         |      changecount (IN/OUT) -- A pointer to a count of the number of
         |              changed pixels.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void fill(char *image, short numrows, short numcols, char **args, 
                                    int argcount, int *changecount){
    if (argcount >= 3) {
        int row, col;
        char color, target;
        row = atoi(*args);
        col = atoi(*(args+1));
        color = **(args+2);
        /* initialize target color for flood-fill */
        target = *(image+numcols*row+col);
        /* flood-fill and print result */
        floodfill(image, numrows, numcols, row, col, color, target,
                    changecount);
        printf("Filling starting at (%d,%d) ", row, col);
        printf("with color %c:\n", color);
        printimage(image, numrows, numcols);
        
        /* recurse until out of instructions */
        fill(image, numrows, numcols, args+3, argcount-3, changecount);
    }
}

        /*------------------------------------------------- floodfill ---------
         |  Function floodfill
         |
         |  Purpose:  This recursive function executes a 4-direction flood-fill
         |              on the parameter image. 
         |
         |  Parameters:
         |      image (IN/OUT) -- A pointer to the image data.
         |      numrows (IN) -- The height of the image.
         |      numcols (IN) -- The width of the image.
         |      row (IN) -- The row value of the pixel to fill.
         |      col (IN) -- The column value of the pixel to fill.
         |      fillcolor (IN) -- The color with which target will be
         |              replaced.
         |      target (IN) -- The color to be replaced.
         |      changecount (IN/OUT) -- A pointer to a count of changed pixels.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void floodfill(char *image, short numrows, short numcols, short row,
                short col, char fillcolor, char target, int *changecount) {
    char *current;
    current = image+numcols*row+col;
    if (*current != target)
        return;

    if (*current == target && *current != fillcolor) {
        *current = fillcolor;
        *changecount = *changecount + 1;
    }

    if (row > 0)
        floodfill(image, numrows, numcols, row-1, col, fillcolor, target,
                    changecount);
    if (row < numrows-1)
        floodfill(image, numrows, numcols, row+1, col, fillcolor, target,
                    changecount);
    if (col > 0)
        floodfill(image, numrows, numcols, row, col-1, fillcolor, target,
                    changecount);
    if (col < numcols-1)
        floodfill(image, numrows, numcols, row, col+1, fillcolor, target,
                    changecount);
}

        /*------------------------------------------------- printimage --------
         |  Function printimage
         |
         |  Purpose:  This function prints an image stored in the parameter
         |              image. This function serves as an entry point to the
         |              recursive function printpixel.
         |
         |  Parameters:
         |      image (IN) -- An array containing the image to be printed.
         |      numrows (IN) -- The height of the image.
         |      numcols (IN) -- The width of the image.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void printimage(char *image, short numrows, short numcols) {
    printf("\n");
    printpixel(image, numrows, numcols, 0, 0);
    printf("\n");
}

        /*------------------------------------------------- printpixel --------
         |  Function printpixel
         |
         |  Purpose:  This function prints the image stored in the parameter
         |              image recursively.
         |
         |  Parameters:
         |      image (IN) -- Pointer to the pixel to be printed.
         |      numrows (IN) -- The height of the image.
         |      numcols (IN) -- The width of the image.
         |      row (IN) -- The row position of the pixel to be printed.
         |      col (IN) -- The column position of the pixel to be printed.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void printpixel(char *image, short numrows, short numcols, short row, 
                                                            short col) {
    short   nextrow,    /* holds row value of next pixel */
            nextcol;    /* holds column value of next pixel */
    printf("%c", *image);
    if (col + 1 == numcols) {
        printf("\n");
        nextcol = 0;
        nextrow = row + 1;
        if (nextrow == numrows) {
            return;
        }
    } else {
        nextcol = col + 1;
        nextrow = row;
    }
    printpixel(++image, numrows, numcols, nextrow, nextcol);
}

        /*--------------------------------------------- advancetonextline -----
         |  Function advancetonextline
         |
         |  Purpose:  This function recursively advances a file pointer to the
         |              the beginning of the next line of the file.
         |
         |  Parameters:
         |      file (IN/OUT) -- A file pointer which is to be advanced to the
         |              beginning of the next line of the file.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void advancetonextline(FILE *file) {
    char token;
    token = fgetc(file);
    if (token != '\n')
        advancetonextline(file);
}

        /*------------------------------------------------- validate ----------
         |  Function validate
         |
         |  Purpose:  This function recursively validates the arguments
         |              supplied to main.
         |
         |  Parameters:
         |      args (IN) -- The flood-fill parameters which were passed to
         |              main.
         |      argcount (IN) -- The number of arguments to be validated. This
         |              is a multiple of 3.
         |      rows (IN) -- The height of the image.
         |      cols (IN) -- The width of the image.
         |
         |  Returns:  0 if all arguments are valid, 1 otherwise.
         *-------------------------------------------------------------------*/

int validate(char **args, int argcount, short rows, short cols) {
    if (argcount >= 3) {
        int row, col;
        char color;
        row = atoi(*args);
        col = atoi(*(args+1));
        color = **(args+2);
        if (row < 0 || col < 0 || row >= rows || col >= cols || 
                                                    !isdigit(color))
            return 1;
        return validate(args+3, argcount-3, rows, cols);
    }
    return 0;
}

        /*------------------------------------------------- badfile -----------
         |  Function badfile
         |
         |  Purpose:  Print an error message to stdout to report an invalid
         |              image file. (Avoids repeated code.)
         |
         |  Parameters: None.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/
void badfile(char *filename) {
    printf("File %s does not contain valid image data.\n", filename);
}
