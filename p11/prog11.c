/*=============================================================================
 |   Assignment:  Program #11: The One with the 3D Orthogonal List
 |
 |       Author:  Daniel Eisenberg
 |     Language:  GNU C (gcc compiled, tested on lectura)
 |   To Compile:  make  (otherwise compile orthogonallist.c -- see file for
 |                       instructions -- and use gcc orthogonallist.o prog11.c)
 |
 |        Class:  CSc 352 -- Systems Programming and UNIX
 |   Instructor:  Dr. L. McCann
 |     Due Date:  December 1st, 2011, 5:00pm
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  This program executes instructions on a sparse orthogonal
 |                list of student records, printing the instructions and any
 |                results to stdout.
 |                The orthogonal list has dimensions for year admitted, major,
 |                and hometown; and the student records additionally contain
 |                student names (with middle initial) and expected graduation
 |                year.
 |
 |        Input:  The name of a file containing valid instructions should be
 |                supplied to this program on the command line.
 |                Instructions are newline delimited.
 |                The supported instructions and formats are as follows:
 |
 |                insert -- inserts the student record into the list
 |                format:
 |                  I,surname,given name, middle initial, major, year admitted,
 |                      expected graduation year, hometown
 |
 |                delete -- deletes the student record from the list
 |                format:
 |                  D,surname,given name,middle initial
 |
 |                status -- displays the quantity of nodes in each dimension
 |                          list; the total number of records; and the
 |                          load factor of the list, defined as the ratio
 |                          of records to the product of the size of each
 |                          dimension list, reported as a percentage.
 |                format:
 |                  S
 |
 |                query -- requests information about a given dimension list
 |                formats:
 |                  Q,1,start year,end year     (year admitted query)
 |                  Q,2,major                   (major query)
 |                  Q,3,hometown                (hometown query)
 |
 |                purge -- clears the list of all records
 |                format:
 |                  P
 |
 |       Output:  Each instruction, any results from that instrution, and any
 |                messages regarding the failure of an operation are printed to
 |                stdout.
 |                Query operations have outputs as follows
 |                  year admitted (query id 1): prints the number of students
 |                      admitted between the supplied dates (inclusive)
 |                  major (query id 2): prints the name of each student with
 |                      the supplied major in "phonebook order" (surname before
 |                      given name before middle)
 |                  hometown (query id 3): prints the name of each student with
 |                      the supplied hometown
 |
 |    Algorithm:  After opening the file supplied on the command line, the
 |                program executes each instruction in order until the end of
 |                the instruction file.
 |                Strings holding data from commands will be stored by scanning
 |                ahead to determine their sizes, using fgetpos and fsetpos.
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

#include "orthogonallist.h"

#define QUERY_YEAR      1
#define QUERY_MAJOR     2
#define QUERY_HOMETOWN  3

int process(FILE *, OrthogonalList **);
void ins(FILE *, OrthogonalList *, fpos_t *);
void del(FILE *, OrthogonalList *, fpos_t *);
void printstatus(OrthogonalList *);
void query(FILE *, OrthogonalList *, fpos_t *);
void purge(OrthogonalList **);
void advance(FILE *);
int nextsize(FILE *);
void printinstruction(FILE *, fpos_t *);

int main(int argc, char *argv[]) {
    if (argc < 2) {
        printf("Please supply the instruction file name as first argument.\n");
        return EXIT_FAILURE;
    }

    FILE *instructions = fopen(argv[1], "r");   /* instructions file */
    if (instructions == NULL) {
        printf("Could not open file %s, exiting.\n", argv[1]);
        return EXIT_FAILURE;
    }
    OrthogonalList *list;   /* list to execute instructions on */
    create_list(&list);
    while (process(instructions, &list));
    destroy_list(&list);
    fclose(instructions);
    return EXIT_SUCCESS;
}

        /*------------------------------------------------------- process -----
         |  Function process
         |
         |  Purpose:  Reads in an instruction code from the file pointer
         |            instructions and routes the remaining arguments and the
         |            list to the appropriate function to handle the
         |            instruction.
         |
         |  Parameters:
         |      instructions (IN/OUT) -- A file pointer to an instructions file
         |      list (IN/OUT) -- The address of an OrthogonalList pointer
         |
         |  Returns:  0 if the file pointer read EOF and 1 otherwise
         *-------------------------------------------------------------------*/

int process(FILE *instructions, OrthogonalList **list) {
    /* parse instruction */
    fpos_t *position;    /* store position to print instruction on error */
    position = malloc(sizeof(fpos_t));  /* allocate */
    if (position == NULL) {
        printf("ERROR: insufficient memory.");
        return 0;
    }
    fgetpos(instructions, position);    /* get position */
    char which = fgetc(instructions);   /* route instruction by type */
    switch(which) {
        case 'I':
            ins(instructions, *list, position);
            break;
        case 'D':
            del(instructions, *list, position);
            break;
        case 'S':
            advance(instructions);
            printstatus(*list);
            break;
        case 'Q':
            query(instructions, *list, position);
            break;
        case 'P':
            advance(instructions);
            purge(list);
            break;
        case EOF:
            free(position);
            return 0;
        default:
            printinstruction(instructions, position);
            printf("Undefined instruction: %c\n", which);
    }
    free(position);
    if (feof(instructions))
        return 0;   /* tell main loop to stop */
    return 1;
}

        /*----------------------------------------------------------- ins -----
         |  Function ins
         |
         |  Purpose:  Attempts to insert a new entry into the specified list.
         |            This function performs sanity checks on the input data.
         |
         |  Parameters:
         |      args (IN/OUT) -- A file pointer to an instructions file
         |      list (IN/OUT) -- The OrthogonalList to modify
         |      position (IN) -- Holds the position of the start of this
         |              instruction to print malformed instructions.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/


void ins(FILE *args, OrthogonalList *list, fpos_t *position) {
    int size;                   /* stores length of next token */

    if (fgetc(args) != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }

    size = nextsize(args) + 1;  /* get last name length */
    char last[size];            /* last name parameter */
    fgets(last, size, args);    /* get last name */
    if (fgetc(args) != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }

    /* get first name */
    size = nextsize(args) + 1;
    char first[size];           /* first name parameter */
    fgets(first, size, args);
    if (fgetc(args) != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }

    /* get middle initial */
    size = nextsize(args) + 1;
    char middlename[size],      
         middle;                /* middle initial parameter */
    fgets(middlename, size, args);
    middle = *middlename;
    if (middle == '\0') {        /* if no middle initial store as space */
        middle = ' ';
    }
    if ((!isalpha(middle) && middle != ' ') || fgetc(args) != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }    

    /* get major */
    size = nextsize(args) + 1;
    char major[size];           /* major parameter */
    fgets(major, size, args);
    if (fgetc(args) != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }

    /* get year admitted */
    size = nextsize(args) + 1;
    char admitted[size];        /* year admitted parameter */
    fgets(admitted, size, args);
    if (fgetc(args) != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }

    /* get graduation year */
    int graduates;              /* graduation year parameter */
    fscanf(args, "%d", &graduates);
    if (fgetc(args) != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }

    if (atoi(admitted) < 0 || graduates < 0) {
        printinstruction(args, position);
        printf("Years must be positive.\n");
        return;
    }

    /* get hometown */
    size = nextsize(args) + 1;
    char hometown[size];        /* hometown parameter */
    fgets(hometown, size, args);

    /* print instruction */
    if (middle == ' ') {
        printf("I,%s,%s,,%s,%s,%d,", last, first, major, admitted, graduates);
        printf("%s\n", hometown);
    } else {
        printf("I,%s,%s,%c,%s,%s,", last, first, middle, major, admitted);
        printf("%d,%s\n", graduates, hometown);
    }

    int result;                 /* get result of operation */
    result = insert(list, last, first, middle, major, admitted, graduates,
                    hometown);

    if (result) {
        printf("%s\n", describe_orthogonal_list_error(result));
    }

    /* advance file pointer to next line */
    advance(args);
}

        /*----------------------------------------------------------- del -----
         |  Function del
         |
         |  Purpose:  Attempts to remove an entry from the specified list.
         |            This function performs sanity checks on the input data.
         |
         |  Parameters:
         |      args (IN/OUT) -- A file pointer to an instructions file
         |      list (IN/OUT) -- The OrthogonalList to modify
         |      position (IN) -- Holds the position of the start of this
         |              instruction to print malformed instructions.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/


void del(FILE *args, OrthogonalList *list, fpos_t *position) {
    int size;                       /* holds size of next token */

    if (fgetc(args) != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }

    /* get last name */
    size = nextsize(args) + 1;      /* get last name size */
    char last[size];                /* last name parameter */
    fgets(last, size, args);        /* get last name parameter */
    if (fgetc(args) != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }

    /* get first name */
    size = nextsize(args) + 1;      /* get first name size */
    char first[size];               /* first name parameter */
    fgets(first, size, args);
    if (fgetc(args) != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }

    /* get middle initial */
    char middle;                    /* middle initial parameter */
    middle = fgetc(args);
    if (!isalpha(middle)) {
        if (middle != '\n' && middle != EOF)
            advance(args);          /* advance file pointer if not newline */
        middle = ' ';               /* set to space for no middle initial */
    } else {
        advance(args);
    }

    /* print instruction */
    if (middle == ' ')
        printf("D,%s,%s,\n", last, first);
    else
        printf("D,%s,%s,%c\n", last, first, middle);

    int result;                     /* store result of list operation */
    result = remove_node(list, last, first, middle);
    if (result)
        printf("%s\n", describe_orthogonal_list_error(result));
}

        /*--------------------------------------------------- printstatus -----
         |  Function printstatus
         |
         |  Purpose:  This function retrieves status information from the
         |            supplied list, and formats and prints that information.
         |
         |  Parameters:
         |      list (IN) -- The OrthogonalList whose status is to be reported
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/


void printstatus(OrthogonalList *list) {
    printf("S\n");

    int years, majors, hometowns, nodes;    /* for status results */
    status(list, &years, &majors, &hometowns, &nodes);
    float load;                             /* list load factor */

    /* calculate load factor */
    if (nodes == 0)
        load = 0.0F;
    else
        load = (float) nodes / (years * majors * hometowns) * 100;

    /* print output */
    printf("%u node(s) in the year admitted dimension list\n", years);
    printf("%u node(s) in the major dimension list\n", majors);
    printf("%u node(s) in the hometown dimension list\n", hometowns);
    printf("%u data item node(s)\n", nodes);
    printf("%.0f%% occupied\n", load);
}

        /*--------------------------------------------------------- query -----
         |  Function query
         |
         |  Purpose:  This function performs a query operation on the supplied
         |            list using the arguments in the provided file pointer,
         |            reporting the result to stdout.
         |
         |  Parameters:
         |      args (IN/OUT) -- A pointer to an instructions file
         |      list (IN) -- The list to query
         |      position (IN) -- Holds the position of the start of this
         |              instruction to print malformed instructions
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/


void query(FILE *args, OrthogonalList *list, fpos_t *position) {

    if (fgetc(args) != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }

    int query_id;                   /* type of query */
    int size;                       /* next token size */
    char delim;                     /* holds actual delimiter character */

    if (fscanf(args, "%d", &query_id) != 1) { /* get query type */
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }
    delim = fgetc(args);
    if (delim != ',') {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
        return;
    }

    if (query_id == QUERY_YEAR) {

        int begin, end;             /* range parameters */

        /* get begin year */
        if (fscanf(args, "%d", &begin) != 1) {
            printinstruction(args, position);
            printf("Invalid instruction format.\n");
            return;
        }

        delim = fgetc(args);
        if (delim != ',') {
            printinstruction(args, position);
            printf("Invalid instruction format.\n");
            return;
        }

        /* get end year */
        if (fscanf(args, "%d", &end) != 1) {
            printinstruction(args, position);
            printf("Invalid instruction format.\n");
            return;
        }

        /* print instruction */
        printf("Q,%d,%d,%d\n", query_id, begin, end);

        if (begin < 0 || end < 0) {
            printf("Invalid arguments: Year parameters must be positive\n");
            return;
        }

        unsigned int result = query_year(list, begin, end); /* call function */
        char *msg;                  /* output string */
        if (result == 1) {
            msg = " student was admitted from ";
        } else {
            msg = " students were admitted from ";
        }

        /* print output*/
        printf("%u%s%d through %d.\n", result, msg, begin, end);
        advance(args);

    } else if (query_id == QUERY_MAJOR) {

        size = nextsize(args) + 1;  /* get size */
        char major[size];           /* query parameter */
        fgets(major, size, args);   /* get parameter */

        /* print instruction */
        printf("Q,%d,%s\n", query_id, major);

        nodeptr result = query_major(list, major);  /* query & store result */

        /* print result */
        printf("The students studying %s are:\n", major);

        if (result == NULL) {
            printf("There are no students studying %s.\n", major);
        } else {
            while (result != NULL) {
                printf("%s, ", result->lastname);
                printf("%s", result->firstname);
                if (result->middle != ' ')
                    printf(", %c\n", result->middle);
                else
                    printf("\n");
                result = result->next_by_major;
            }
        }

        advance(args);

    } else if (query_id == QUERY_HOMETOWN) {

        size = nextsize(args) + 1;  /* get size */
        char hometown[size];        /* query parameter */
        fgets(hometown, size, args);/* get parameter */

        /* print instruction */
        printf("Q,%d,%s\n", query_id, hometown);

        /* make query and store result */
        int year;                   /* last year of graduation */
        nodeptr result = query_hometown(list, hometown, &year);

        /* print result header */
        printf("The student(s) from %s with the latest expected ", hometown);
        printf("year of graduation:\n");

        if (result == NULL) {
            printf("There are no students from %s\n", hometown);
        } else {
            do {
                printf("%s, ", result->lastname);
                printf("%s", result->firstname);
                if (result->middle != ' ')
                    printf(", %c\n", result->middle);
                else
                    printf("\n");
                result = result->next_by_hometown;
            } while (result != NULL && result->graduation_year == year);
        }

        advance(args);

    } else {
        printinstruction(args, position);
        printf("Invalid instruction format.\n");
    }
}

        /*--------------------------------------------------------- purge -----
         |  Function purge
         |
         |  Purpose:  This function clears an orthogonal list by calling
         |            destroy_list and create_list in succession.
         |
         |  Parameters:
         |      list (IN/OUT) -- The list to be purged
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/


void purge(OrthogonalList **list) {
    destroy_list(list);
    create_list(list);
    printf("P\n");
}

        /*------------------------------------------------------- advance -----
         |  Function advance
         |
         |  Purpose:  This function advances a file pointer to the start of the
         |            next line in the file.
         |
         |  Parameters:
         |      fp (IN/OUT) -- The file pointer to advance
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/


void advance(FILE *fp) {
    char c = fgetc(fp); /* holds characters read from fp */
    while (c != '\n' && c != EOF)
        c = fgetc(fp);
}

        /*------------------------------------------------------ nextsize -----
         |  Function nextsize
         |
         |  Purpose:  Finds the size of the next token pointed to by the file
         |            pointer parameter. The file pointer is then restored to
         |            its original position.
         |
         |  Parameters:
         |      fp (IN/OUT) -- The file pointer to advance.
         |
         |  Returns:  The number of characters in the next token.
         *-------------------------------------------------------------------*/


int nextsize(FILE *fp) {
    fpos_t *position;   /* store file pointer position */
    position = malloc(sizeof(fpos_t));   /* allocate memory */
    fgetpos(fp, position);
    int len = 0;        /* length count */
    char c = fgetc(fp); /* get each char to count */
    while (c != ',' && c != '\n' && c != EOF) {
        len++;
        c = fgetc(fp);
    }
    fsetpos(fp, position);  /* restore position */
    free(position);
    return len;
}

        /*---------------------------------------------- printinstruction -----
         |  Function printinstruction
         |
         |  Purpose:  Prints an instruction from an instruction file, used in
         |            the event of an error. On return, the file pointer is set
         |            to the position of the next instruction, if there is one.
         |
         |  Parameters:
         |      fp (IN/OUT) -- The file pointer to advance.
         |      position (IN) -- The start of the instruction to print.
         |
         |  Returns:  None.
         *-------------------------------------------------------------------*/


void printinstruction(FILE *fp, fpos_t *position) {
    fsetpos(fp, position);
    int len = 0;                /* count for string */
    char c = fgetc(fp);         /* get each char to count */
    while (c != '\n' && c != EOF) {
        len++;
        c = fgetc(fp);
    }
    len++;                      /* for nul char */
    char instruction[len];      /* holds instruction */
    fsetpos(fp, position);      /* reset fp to read instruction */
    fgets(instruction, len, fp);/* get instruction */
    fgetc(fp);                  /* read newline or EOF */
    printf("%s\n", instruction);/* print instruction */
}
