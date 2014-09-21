/*=============================================================================
 |   Assignment: Program #5: Making Movies and Gibberish
 |                  Part 2: 
 |
 |       Author:  Daniel Eisenberg
 |     Language:  C, tested on lectura using gcc compiled code
 |   To Compile:  gcc, no arguments necessary
 |
 |        Class:  CSc 352 -- Systems Programming and UNIX
 |   Instructor:  Lester McCann
 |     Due Date:  September 29th, 2011, 5:00pm
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  This C program takes in text from stdin and prints that text
 |                  encoded with ROT47 to stdout.
 |
 |        Input:  The text to be encrypted (or equivalently decrypted, as ROT47
 |                  is a symmetric encryption algorithm) from stdin.
 |
 |       Output:  The encrypted (or decrypted) text printed to stdout. If no
 |                  text is provided via stdin, this program will exit
 |                  silently.
 |
 |    Algorithm:  To encrypt text with ROT47, replace each input character in
 |                  the ASCII range 33 to 126 by shifting to the right by 47
 |                  and wrapping as appropriate. This yields a rotation on
 |                  all printable characters in the standard ASCII set
 |                  excepting the space character (ASCII decimal value 32).
 |
 |                  To implement this cipher, as each character is read in,
 |                  take its ASCII value, and check whether it is between 33
 |                  and 126 inclusive. If it is not, print it. If it is, shift
 |                  it 47 places to the right. To perform this shift, subtract
 |                  33, add 47, take the remainder from dividing the result by
 |                  94, and add 33 to get the ASCII value of the output for
 |                  that character. Finally print the character.
 |
 |                  As a shortcut, subtracting 33 and adding 47 can be combined
 |                  to adding 14. So the output ASCII value of an input
 |                  character with an ASCII value v of 33 or more and less than
 |                  127 is
 |
 |                          output value = (v + 14) % 94 + 33.
 |
 |
 |   Required Features Not Included:  None known.
 |
 |   Known Bugs:  None.
 |
 *===========================================================================*/


#include <stdio.h>
#include <ctype.h>

#define SPACE ' '

int main(void) {

    int next_token; /* stores return value of getchar() calls */

    next_token = getchar();

    while(next_token != EOF) {
        /* if char is not printable ascii or space, print char unchanged */
        if (!isprint(next_token) || toascii(next_token) == SPACE) {
            putchar(next_token);
        } else {
            char token_ascii; /* stores next_token */

            token_ascii = toascii(next_token);       /* take ascii value */
            token_ascii = ((token_ascii + 14) % 94) + 33; /* cipher char */
            putchar(token_ascii);                          /* print char */
        }
        /* update loop */
        next_token = getchar();
    }

}
