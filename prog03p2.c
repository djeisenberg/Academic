/*=============================================================================
 |   Assignment: Program #3: File Permissions and Hex Dumps
 |                  Part 2: Reading an Octal Dump
 |
 |       Author:  Daniel Eisenberg
 |     Language:  C, tested on lectura using gcc compiled code
 |   To Compile:  gcc, no arguments necessary
 |
 |        Class:  CSc 352 -- Systems Programming and UNIX
 |   Instructor:  Lester McCann
 |     Due Date:  September 15th, 2011, 5:00pm
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  This program reads in an octal dump in the format of the GNU
 |                  od command and outputs the text corresponding to that octal 
 |                  dump.
 |
 |        Input:  A character stream from stdin. The input is assumed to be
 |                  the output of an execution of the GNU od command with no
 |                  flags. Input is expected to not contain the asterisk
 |                  character ('*'), which is output by od when two consecutive
 |                  lines of od output have the same content.
 |                  Input violating these criteria will be considered invalid.
 |
 |       Output:  A character stream to stdout which should match an 
 |                  invocation of the cat command on the input, assuming input 
 |                  is valid. If input invalid, an error message is printed 
 |                  and the program terminates.
 |
 |                  In certain cases, invalid input may follow what appears to 
 |                  be valid input (for example, an octal dump with other text
 |                  concatenated afterward.) In these cases, output will proceed
 |                  as if the input were valid before an error message is
 |                  printed and output halted once bad input is read in.
 |
 |    Algorithm:  The first token from stdin is checked for equality to zero,
 |                  as an invocation of od will produce a leading byte count,
 |                  which is necessarily zero. Subsequent tokens are read in
 |                  as unsigned octal numbers. Since an invocation of od will
 |                  dump 2-byte octal numbers where the lower byte represents
 |                  the character which comes first and the upper byte the
 |                  character which comes seecond, these numbers are converted
 |                  to characters by taking bitwise logical AND operations with
 |                  0x00FF for the lower byte and 0xFF00 for the upper byte.
 |                  In the case of the upper byte, the result is bit shifted
 |                  right by 8 places. These characters are then printed
 |                  lower before upper.
 |
 |                  Since od prints a leading byte count on every line,
 |                  followed by up to 8 octal numbers, every 9th token from
 |                  stdin, not counting the first token, is first checked for 
 |                  validity and then ignored.
 |
 |                  Since scanf will return 0 if it fails to find valid input,
 |                  testing scanf for a 0 value is used to stop the main loop.
 |                  If there is still more input, a bad input error message
 |                  is printed before termination.
 |
 |
 |   Required Features Not Included:  None known.
 |
 |   Known Bugs:  None.
 |
 *===========================================================================*/


#include <stdio.h>

#define BAD_INPUT "Invalid input.\n" /* error message */

void printChars(unsigned int);

int main(void) {
    int first; /* stores first token from stdin */
    unsigned int token; /* stores each further token from stdin */
    unsigned int byte_count = 0; /* tally of byte count; can be 1 too large */
    unsigned int num_tokens = 0; /* tally of tokens read in (zero indexed) */

    /* scan first token and check it is valid input */
    if ((token = scanf("%d", &first)) != 1 || first != 0) {
        printf(BAD_INPUT);
        return 0; /* invalid input; exit main */
    }

    /* main loop */
    while (scanf("%o", &token) == 1) {
        num_tokens++;

        if (num_tokens % 9 != 0) { /* token not expected byte count */

            if (byte_count == token || byte_count - 1 == token) { 
                    /* token may be last byte count. 
                        last byte count can be 1 less than byte_count */

                unsigned int next_token; /* temp int tests for end-of-input */

                if (scanf("%o", &next_token) != 1) { /* no more valid tokens */
                    break;
                } else { /* not end-of-input; continue */
                    num_tokens++; /* scanned an extra token */

                    if (num_tokens % 9 != 0) { /* extra token not byte count */
                        byte_count += 2;
                        /* print all four bytes */
                        printChars(token);
                        token = next_token;
                    }
                }
            }
            byte_count += 2;
            printChars(token);
        }
    }
    /* loop may break from end of input or bad input; check input */
    char check_err; /* used to check for bad input */
    if (scanf("%c", &check_err) != 0 && check_err != 0) {
        printf(BAD_INPUT);
    }
}

        /*------------------------------------------------- printChars --------
         |  Function printChars
         |
         |  Purpose:  printChars reads in an unsigned integer which contains
         |              two ASCII characters. These are converted to two char
         |              variables which are then printed to stdout.
         |
         |  Parameters:
         |      token (IN) -- this is an unsigned int assumed to contain two
         |              ASCII characters, one in the least significant byte,
         |              one in the next least significant byte. The character
         |              in the least significant byte is printed, and then the
         |              remaining character is printed.
         |
         |  Returns:  None (void function)
         *-------------------------------------------------------------------*/

void printChars(unsigned int token /* octal dump of one or two characters */) {
    char lower; /* stores the lower byte of token */
    char upper; /* stores the upper byte of token */
    lower = token & 0x00FF; /* take lower byte of token */
    upper = (token & 0xFF00) >> 8; /* take upper byte of token */
    printf("%c%c", lower, upper);
}
