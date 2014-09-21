/*=============================================================================
 |   Assignment:  Program #9: PCX Image Files
 |
 |       Author:  Daniel Eisenberg
 |     Language:  C, tested on lectura
 |   To Compile:  gcc, no flags required
 |
 |        Class:  C Sc 352 -- Systems Programming and UNIX
 |   Instructor:  Lester McCann
 |     Due Date:  November 3rd, 2011, 5:00pm
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  This program converts a text file of a given format into a
 |                  PCX format image file using a custom 95-color palette.
 |
 |        Input:  The filename of a text file should be supplied as a command
 |                  line argument. The first line of the text file should begin
 |                  with the [height] and [width] of the image to be produced,
 |                  separated by at least one space. There should be at least
 |                  [height] more lines in the file, each beginning with
 |                  [width] printable ASCII characters. Each of these printable
 |                  ASCII characters is mapped to a color by the custom
 |                  palette.
 |
 |       Output:  A PCX format image file, which will have the same name as the
 |                  input file, except the extension (the text following the
 |                  last '.' character) will be replaced by pcx. If the source
 |                  file has no extension, .pcx will be appended to the full
 |                  filename. This program does not write color palette data
 |                  PCX files, the resulting file uses 24-bit RGB color
 |                  instead.
 |
 |    Algorithm:  The input file is read in, and a new file is written.
 |                  The new file consists of a PCX file header followed by the
 |                  Run-Length Encoded pixel data based on the contents of the
 |                  source file and the custom palette used by this program.
 |
 |   Required Features Not Included:  None.
 |
 |   Known Bugs:  None.
 |
 *===========================================================================*/

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

#define HEADER_SIZE 128
#define HEADER0 10
#define HEADER1 5
#define HEADER2 1
#define HEADER3 8
#define DPI0 1      /* upper byte of DPI */
#define DPI1 44     /* lower byte of DPI */
#define COLOR_PLANES 3
#define PALETTE_TYPE 1
#define MAX_RUN 63

#define FILE_SEPARATOR '/'
#define COLOR 0     /* 0 for greyscale palette, 1 for color palette */

void namefile(char*, char*);
void closefiles(FILE*, FILE*);
void makeheader(FILE*, FILE*, short, short);
void advance(FILE*);
void process(FILE*, FILE*, short, short);
void writeline(FILE*, FILE*, char*, unsigned short);
void getcolor(char, unsigned char*, unsigned char*, unsigned char*);
void encode(unsigned char*, int*, unsigned char*, unsigned short);
void colorpalette(char, unsigned char*, unsigned char*, unsigned char*);
void greyscalepalette(char, unsigned char*, unsigned char*, unsigned char*);

int main(int argc, char *argv[]) {
    if (argc < 2) {
        printf("Usage: %s file\n", *argv);
        return EXIT_FAILURE;
    }

    char    *pcxfilename;       /* stores PCX filename */
    FILE    *sourcefile,        /* file pointer for source file */
            *destfile;          /* file pointer for destination file */
    int len;                    /* for length of new filename */        
    len = strlen(argv[1]) + 4;  /* need to add up to 4 chars (.pcx) */
    pcxfilename = malloc(len);
    if (pcxfilename == NULL) {
        fprintf(stderr, "ERROR: Insufficient memory\n");
        return EXIT_FAILURE;
    }

    sourcefile = fopen(argv[1], "r");
    if (sourcefile == NULL) {
        fprintf(stderr, "ERROR: Cannot open file %s\n", argv[1]);
        return EXIT_FAILURE;
    }
    namefile(argv[1], pcxfilename);

    destfile = fopen(pcxfilename, "wb");
    if (destfile == NULL) {
        fprintf(stderr, "ERROR: Cannot write file %s\n", pcxfilename);
        fclose(sourcefile);
        return EXIT_FAILURE;
    }
    free(pcxfilename);

    short height, width; /* store pixel height and width of image */
    if (fscanf(sourcefile, "%hd %hd", &height, &width) < 2) {
        fprintf(stderr, "ERROR: %s is not a valid source file\n", argv[1]);
        closefiles(sourcefile, destfile);
        return EXIT_FAILURE;
    }
    advance(sourcefile);

    makeheader(sourcefile, destfile, height, width);

    process(sourcefile, destfile, height, width);

    closefiles(sourcefile, destfile);

}

        /*------------------------------------------------- namefile ----------
         |  Function namefile
         |
         |  Purpose:  Takes a filename and replaces the terminal extension with
         |              .pcx, or appends .pcx if no extension exists.
         |              Used by main to name the output PCX file.
         |              The extension (or lack thereof) is identified by
         |              locating the last '.' and the last file separator in
         |              the filename and comparing these to check for extension
         |              (or hidden file with no extension).
         |
         |  Parameters:
         |      original (IN) - the source filename
         |      name (OUT) - a pointer for the resulting PCX filename
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void namefile(char *original, char *name) {
    int i;  /* loop iterator */

    /* copy original filename to name */
    for (i = 0; original[i] != '\0'; i++) {
        name[i] = original[i];
    }
    name[i] = '\0';

    /* make output filename */
    char    *dotlocation,   /* pointer to location of last dot in name */
            *separatorlocation; /* pointer to location of last file separator
                                   in name */
    dotlocation = strrchr(name, '.');
    separatorlocation = strrchr(name, FILE_SEPARATOR);
    /* file has no extension (possibly hidden) */
    if (dotlocation == NULL || dotlocation == name
                            || separatorlocation + 1 == dotlocation) {
        for (i = 0; name[i] != '\0'; i++);
        name[i] = '.';
        dotlocation = &name[i];   
    }
    /* dotlocation is now pointing to last dot in name */
    *dotlocation++;
    *dotlocation++ = 'p';
    *dotlocation++ = 'c';
    *dotlocation++ = 'x';
    *dotlocation = '\0';
}

        /*------------------------------------------------- closefiles --------
         |  Function closefiles
         |
         |  Purpose:  This function is called before the program exits and
         |              simply closes the files associated with the program's
         |              file pointers.
         |
         |  Parameters:
         |      f1 (IN/OUT) - the first file to be closed
         |      f2 (IN/OUT) - the second file to be closed
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void closefiles(FILE *f1, FILE *f2) {
    fclose(f1);
    fclose(f2);
}

        /*------------------------------------------------- makeheader --------
         |  Function makeheader
         |
         |  Purpose:  This function creates a header for the PCX file that is
         |              to be written by the program.
         |
         |  Parameters:
         |      source (IN) - a pointer to the source file in case it needs to
         |              be closed in an error situation
         |      dest (OUT) - a pointer to the file whose header is to be
         |              written
         |      height (IN) - the height of the final image in pixels
         |      width (IN) - the width of the final image in pixels
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void makeheader(FILE *source, FILE *dest, short height, short width) {
    char *header; /* stores the header data */
    /* most of the header consists of 0 bytes, use calloc */
    header = calloc(1, HEADER_SIZE);

    if (header == NULL) {
        fprintf(stderr, "ERROR: Insufficient memory\n");
        closefiles(source, dest);
        exit(EXIT_FAILURE);
    }

    char upper; /* store one byte of a short */
    /* defining constants for every byte position will just create clutter
       see PCX file header definitions for more info */
    header[0] = HEADER0;
    header[1] = HEADER1;
    header[2] = HEADER2;
    header[3] = HEADER3;
    /* need upper byte of width */
    upper = (char) (width >> 8);
    header[9] = upper;
    header[8] = (char) width - 1;
    /* need lower byte of width */
    upper = (char) (height >> 8);
    header[11] = upper;
    header[10] = (char) height - 1;
    header[13] = DPI0;
    header[12] = DPI1;
    header[15] = DPI0;
    header[14] = DPI1;
    header[65] = COLOR_PLANES;
    /* again need upper byte of width */
    upper = (char) (width >> 8);
    header[67] = upper;
    header[66] = width;
    header[68] = PALETTE_TYPE;

    fwrite(header, 1, HEADER_SIZE, dest);
    if (ferror(dest)) {
        fprintf(stderr, "ERROR: cannot write file\n");
        closefiles(source, dest);
        exit(EXIT_FAILURE);
    }
    free(header);
}

        /*------------------------------------------------- advance -----------
         |  Function advance
         |
         |  Purpose:  This function advances a pointer to a text file to the
         |              next line of that file.
         |
         |  Parameters:
         |      fp (IN/OUT) - the file pointer to be advanced to its file's
         |              next line
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void advance(FILE *fp) {
    char next;
    do {
        next = fgetc(fp);
    } while (next != EOF && next != '\n');
}

        /*------------------------------------------------- process -----------
         |  Function process
         |
         |  Purpose:  This function reads in text data from source and writes
         |              a PCX format encoding to dest. This function primarily
         |              consists of flow control and error checking. Calls to
         |              writeline handle the actual conversion, encoding, and
         |              writing.
         |
         |  Parameters:
         |      source (IN) - a pointer to a text file which is to be converted
         |              to a PCX image
         |      dest (IN/OUT) - a pointer to a binary file which is to be
         |              written as a PCX encoded image file
         |      height (IN) - the final pixel height of the image
         |      width (IN) - the final pixel width of the image
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void process(FILE *source, FILE *dest, short height, short width) {
    char *line; /* holds one row of source file "pixels" */
    line = malloc(width);
    if (line == NULL) {
        fprintf(stderr, "ERROR: Insufficient memory\n");
        closefiles(source, dest);
        exit(EXIT_FAILURE);
    }
    int i, j;   /* loop iterators */
    char pixel; /* holds one ASCII character ("pixel") from source file */

    /* for each row of the source file's "image"... */
    for (i = 0; i < height; i++) {
        if (feof(source)) {
            fprintf(stderr, "ERROR: Invalid source file\n");
            closefiles(source, dest);
            exit(EXIT_FAILURE);
        }
        /* for each pixel of that row... */
        for (j = 0; j < width; j++) {
            pixel = fgetc(source);
            if (isprint(pixel)) {
                /* copy that pixel */
                line[j] = pixel;
            } else {
                fprintf(stderr, "ERROR: Invalid source file\n");
                closefiles(source, dest);
                exit(EXIT_FAILURE);
            }
        }
        /* move file pointer to beginning of next line */
        advance(source);
        /* write line to output file */
        writeline(source, dest, line, width);

        if (ferror(dest)) {
            fprintf(stderr, "ERROR: Could not write to file\n");
            closefiles(source, dest);
            exit(EXIT_FAILURE);
        }
    }

    free(line);
}

        /*------------------------------------------------- writeline ---------
         |  Function writeline
         |
         |  Purpose:  Writes one line of characters to dest in PCX format Run-
         |              Length Encoding (RLE). This is accompished by creating
         |              3 byte arrays to store the red, green, and blue pixel
         |              byte values. These are populated by calls to getcolor.
         |              Another byte array is then created to be encoded by the
         |              encode function.
         |
         |  Parameters:
         |      source (IN/OUT) - a pointer to the source file which may need
         |              to be closed in an error situation
         |      dest (IN/OUT) - a pointer to the PCX file to be written
         |      line (IN) - the ASCII data to encode
         |      length (IN) - the length of line
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void writeline(FILE *source, FILE *dest, char *line, unsigned short length) {
    unsigned char   *red,   /* the red bytes */
                    *green, /* the green bytes */
                    *blue;  /* the blue bytes */
    red = malloc(length);
    green = malloc(length);
    blue = malloc(length);
    if (red == NULL || green == NULL || blue == NULL) {
        fprintf(stderr, "ERROR: Insufficient memory\n");
        closefiles(source, dest);
        exit(EXIT_FAILURE);
    }
    /* populate red, green, blue with uncompressed pixel values */
    int i = 0;  /* loop iterator */
    while (i < length) {
        getcolor(line[i], &red[i], &green[i], &blue[i]);
        i++;
    }
    unsigned char *encodedline; /* stores the encoded line of pixels */
    int elinelen = 0;           /* stores the length of encodedline */
    /*  encoded line may be twice as long as actual bytes in worst case;
        since one line is red + green + blue and each raw byte may take 2 bytes
        to represent after encoding, we need 6 times the width of one line
        at most */
    encodedline = malloc(length*6);
    if (encodedline == NULL) {
        fprintf(stderr, "ERROR: Insufficient memory\n");
        closefiles(source, dest);
        exit(EXIT_FAILURE);
    }

    /* encode red, green, blue */
    /* pass address of elinelen to encode so we can track length */
    encode(encodedline, &elinelen, red, length);
    /* pass address of first empty byte of encodedline (its populated size) */
    encode(&encodedline[elinelen], &elinelen, green, length);
    /* as above */
    encode(&encodedline[elinelen], &elinelen, blue, length);
    /* encodedline now has compressed red, green, blue for one line of image */

    /* write encoded line to file */
    fwrite(encodedline, 1, elinelen, dest);
    free(red);
    free(green);
    free(blue);
    free(encodedline);
}

        /*------------------------------------------------- getcolor ----------
         |  Function getcolor
         |
         |  Purpose:  This function chooses the custom color palette used to
         |              encode the PCX file. The result is written to the
         |              locations red, green, and blue.
         |
         |  Parameters:
         |      pixel (IN) - the character to be encoded
         |      red (OUT) - a pointer to store the red pixel value
         |      green (OUT) - a pointer to store the green pixel value
         |      blue (OUT) - a pointer to store the blue pixel value
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void getcolor(char pixel, unsigned char *red, unsigned char *green,
                                                unsigned char* blue) {
    if (COLOR) {
        colorpalette(pixel, red, green, blue);
    } else {
        greyscalepalette(pixel, red, green, blue);
    }
}

        /*------------------------------------------------- encode ------------
         |  Function encode
         |
         |  Purpose:  This function writes the provided unsigned byte values as
         |              PCX RLE format into rle and increments the value
         |              referenced by rlelen for each byte written.
         |
         |  Parameters:
         |      encodedline (OUT) - an array to store an encoded line of a PCX
         |              image
         |      elinelen (IN/OUT) - a pointer to a running total of the length
         |              of rle
         |      bytes (IN) - the values of one color of one line of the image
         |      length (IN) - the length of bytes
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void encode(unsigned char *encodedline, int *elinelen, unsigned char *bytes,
                                                    unsigned short length) {
    short i = 0;    /* loop iterator */
    short count;    /* running count of identical bytes */
    while (i < length) {
        count = 1;
        while (i + count < length && bytes[i] == bytes[i+count]
                                            && i + count < MAX_RUN)
            count++;
        /* two-byte sequence case, write first byte and increment *elinelen */
        if (count > 1 || bytes[i] >= 192) {
            *encodedline++ = 192 + count;
            *elinelen += 1;
        }
        *encodedline++ = bytes[i];
        *elinelen += 1;
        /* skip over all processed bytes */
        i += count;
    }
}

        /*------------------------------------------------- colorpalette ------
         |  Function colorpalette
         |
         |  Purpose:  This function defines the custom color palette used to
         |              encode the PCX image file. This palette uses a limited
         |              selection of colors.
         |
         |  Parameters:
         |      pixel (IN) - the ASCII character to convert to 24-bit color
         |      rval (OUT) - a pointer to store the red color byte
         |      gval (OUT) - a pointer to store the green color byte
         |      bval (OUT) - a pointer to store the blue color byte
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void colorpalette(char pixel, unsigned char *rval, unsigned char *gval,
                                                    unsigned char *bval) {
    unsigned char r, g, b;  /* color values */

    pixel -= 32;            /* normalize: 0 to 94 (printable: 32-126) */

    if (pixel > 63) {       /* this range used for grey values */
        /* greyscale range: 64-94 mapped to 21-231 */
        r = g = b = 21;
        pixel -= 64;
        *rval = *gval = *bval = r + pixel * 7;
    } else {                /* 0 <= pixel < 64 */
        /* pixel in range 0-63 inclusive
           encode bitwise: rrggbb 
           00 = 00 
           01 = 55 
           10 = AA
           11 = FF
        */
        /* bit twiddling follows */
        /* get red bits */
        r = pixel >> 4;
        /* get green bits */
        g = (pixel & 0x0f) >> 2;
        /* get blue bits */
        b = pixel & 0x03;

        /* convert 2 bits to byte */
        *rval = r * 0x55;
        *gval = g * 0x55;
        *bval = b * 0x55;
    }
}

        /*------------------------------------------------- greyscalepalette --
         |  Function greyscalepalette
         |
         |  Purpose:  This function defines the custom color palette used to
         |              encode the PCX image file. This palette is greyscale.
         |
         |  Parameters:
         |      pixel (IN) - the ASCII character to convert to 24-bit color
         |      rval (OUT) - a pointer to store the red color byte
         |      gval (OUT) - a pointer to store the green color byte
         |      bval (OUT) - a pointer to store the blue color byte
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/

void greyscalepalette(char pixel, unsigned char *rval, unsigned char *gval,
                                                        unsigned char *bval) {
    unsigned char value;    /* value to write to all 3 color bytes */
    if (pixel >= 117) {     /* values 117-126 used as black */
        value = 0;          /* = 0x000000  (black) */
    } else {
        /* start at ASCII 32 = 0xFFFFFF (white) 
           decrement by 3 for every increase of ASCII value by 1 */

        /* pixel printable, so 31 < pixel < 117 */
        pixel -= 117;
        /* pixel in range -85 to -1 */
        value = pixel * -3;
        /* pixel in range 3 to 255 */
    }
    *rval = *gval = *bval = value;
}
