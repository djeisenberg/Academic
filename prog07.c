/*=============================================================================
 |   Assignment:  Program #7: Flesch-Kincaid Grade Level Readability
 |
 |       Author:  Daniel Eisenberg
 |     Language:  C, tested on lectura
 |   To Compile:  gcc (no required arguments)
 |
 |        Class:  CSc 352 -- Systems Programming and UNIX
 |   Instructor:  Lester McCann
 |     Due Date:  October 20th, 2011, 5:00pm
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  This program reads text from stdin and prints the
 |                  Flesch-Kincaid grade level readability of that text.
 |                  This is calculated as follows:
 |                      Grade level = 0.39 * MWPS + 11.8 MSPW - 15.59
 |                  where MWPS is the mean words per sentence, and MSPW is the
 |                  mean syllables per word.
 |                  We define the sentence count as the count of '.', '?', and
 |                  '!' characters in the text.
 |                  We define the word count as the count of contiguous strings
 |                  beginning with a letter and consisting of letters, and the
 |                  '-' and ''' (ASCII 39) characters. Any substring of a
 |                  specific string which qualifies as a word will not be
 |                  considered a word (for example, "cat" is one word, despite
 |                  containing "at").
 |                  We define the number of syllables in a word to be the count
 |                  of the vowels, reduced by 1 for each consecutive pair of
 |                  vowels, and by 1 for an 'e' (or 'E') which appears at the
 |                  end of the word, to a minimum of 1 syllable for a word.
 |
 |        Input:  Text from stdin.
 |
 |       Output:  A report containing information about the text, consisting of
 |                  number of sentences, words, syllables and the grade level.
 |                  Sample output:
 |                  16 sentences
 |                  71 words
 |                  138 syllables
 |                  9.1 is the text's grade level
 |
 |    Algorithm:  Read in a character at a time, incrementing the sentence
 |                  count whenever a sentence terminator is read in.
 |                  When a letter is found, set state to in-word mode and
 |                  increment word count.
 |                  While processing a word, maintain a vowel count.
 |                  When a vowel is encountered, read more input until a
 |                  non-vowel is found. When a non-vowel is found, if that
 |                  character does not mark the end of a word or the last vowel
 |                  read was not an 'e' (nor an 'E'), increment vowel count by
 |                  one. When the word which triggered the in-word state ends,
 |                  add the vowel count for that word to the syllable count
 |                  unless the vowel count is zero, in which case, increment
 |                  syllables by one.
 |                  Repeat until EOF reached, calculate and print output.
 |
 |   Required Features Not Included:  None.
 |
 |   Known Bugs:  None.
 |
 *===========================================================================*/
 
 #include <stdio.h>
 #include <ctype.h>
 
 #define isvowel(c) (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
 #define isterminator(c) (c == '.' || c == '?' || c == '!')
 #define iswordpart(c) (isalpha(c) || c == '-'|| c == '\'')
 
 #define MWPS_WEIGHT 0.39
 #define MSPW_WEIGHT 11.8
 #define OFFSET 15.59
 
 int main(void) {
    unsigned int sentences = 0,  /* running sentence count */
            words = 0,           /* running word count */
            syllables = 0,       /* running syllable count */
            vowels = 0;          /* per-word syllable count */
    char    token,               /* the current character from stdin */
            inword = 0,          /* flag for in-word state */
            haveterminator = 1;  /* flag for sentence end since last word */
    
    while ((token = getchar()) != EOF) {
        token = tolower(token);
        if (inword) {
            if (iswordpart(token)) {
                if (isvowel(token)) {
                    char prev = token; /* the previous value of token */
                    token = tolower(getchar());
                    while (token != EOF && isvowel(token)) {
                        prev = token;
                        token = tolower(getchar());
                    }
                    if (!iswordpart(token)) {
                        if (token == EOF) {
                            sentences++;
                        }
                        if (isterminator(token)) {
                            sentences++;
                            haveterminator = 1;
                        }
                        if (prev != 'e') {
                            vowels++;
                        }
                        inword = 0;
                        syllables += (vowels? vowels : 1);
                        vowels = 0;
                    } else {
                        vowels++;
                    }
                }
            } else { /* end inword state */
                if (isterminator(token)) {
                    sentences++;
                    haveterminator = 1;
                }
                if (token == EOF && !haveterminator) {
                    sentences++;
                }
                inword = 0;
                syllables += (vowels? vowels : 1);
                vowels = 0;
            }
        } else { /* not in a word */
            if (isterminator(token)) {
                sentences++;
                haveterminator = 1;
            }
            if (isalpha(token)) {
                haveterminator = 0;
                inword = 1;
                words++;
                if (isvowel(token)) {
                    ungetc(token, stdin); /* return vowel to buffer */
                    continue; /* send vowel to inword vowel subroutine */
                }
            }
        }
    }
    if (!haveterminator) {
        sentences++;
    }

    printf("%u sentences\n", sentences);
    printf("%u words\n", words);
    printf("%u syllables\n", syllables);
    if (words) {
        double gradelevel = MWPS_WEIGHT * words / ((double) sentences);
        gradelevel += MSPW_WEIGHT * syllables / ((double) words);
        gradelevel -= OFFSET;
        printf("%.1f is the text's grade level\n", gradelevel);
    } else {
        printf("Input contains no words, grade level cannot be computed.\n");
    }
}
