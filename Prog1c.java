/*=============================================================================
 |   Assignment:  Program 1 (part 3) Sorted Unique Argument List 
 |       Author:  Daniel Eisenberg
 |       Grader:  Balaji Prasad
 |
 |       Course:  C Sc 352
 |   Instructor:  L. McCann
 |     Due Date:  9/1/2011
 |
 |  Description:  This simple java command-line program accepts arbitrarily
 |            many command line arguments and prints these arguments
 |            to standard output in ascending ASCII orderng
 |            beginning at the first letter in the String.
 |            Arguments are expected to begin with '-' or 
 |            alphanumeric characters. Arguments which include spaces
 |            will be printed surrounded by double quotes.
 |        
 |            If run with no arguments, the program will do nothing and
 |            return. If run with only invalid arguments, the program
 |            will return a blank line.
 |                
 | Deficiencies:  None known.
 *===========================================================================*/

public class Prog1c {

    public static void main(String[] args) {
        if (args.length > 0) {
            //sort args
            alphaSort(args);

            //print args
            for (int i = 0; i < args.length; i++) {
                //check for space(s)
                if (args[i].indexOf(' ') != -1) {
                    System.out.print('"' + args[i] + '"');
                } else {
                    System.out.print(args[i] + " ");
                }
                //delineate arguments
                System.out.print(" ");

                //check for duplicates
                for (int j = i + 1; j < args.length; j++) {
                    if (args[j].equals(args[i]))
                        args[j] = "";
                }
            }
            System.out.println();
        }
    }

       /*---------------------------------------------------------------------
        |  Method alphaSort
        |
        |  Purpose:  This method sorts a String array in ascending ASCII
        |        order starting from the first instance of a letter
        |        character.
        |        The sorting algorithm is insertion sort.
        |        
        |
        |  Pre-condition:  toSort has at least 1 String; toSort may be in any
        |            ordering; toSort contains String objects whose
        |            initial character is alphanumeric or the
        |            hyphen character ('-'); toSort is assumed to
        |            contain only Strings which contain at least
        |            one letter.
        |
        |  Post-condition: toSort is sorted in ascending ASCII ordering by
        |            the first alphabetic character in each String;
        |            Strings in toSort which did not meet the
        |            pre-conditions have been replaced by the
        |            empty String; these empty Strings are not
        |            considered to have any ordering, and so may be
        |            in any location in toSort.
        |
        |  Parameters:
        |    toSort -- this is an array of String objects to be sorted.
        |
        |  Returns:  None.
        *-------------------------------------------------------------------*/

    private static void alphaSort(String[] toSort) {
        if (!goodInput(toSort[0]))
            toSort[0] = "";
        for (int i = 1; i < toSort.length; i++) {
            if (!goodInput(toSort[i])) {
                toSort[i] = "";
            } else {
                String current = toSort[i]; //String to be ordered
                int marker = i - 1; //array position marker
                while (marker >= 0) {
                    if (toSort[marker].equals("")) {
                        marker--; //ignore empty String
                    } else if (firstGreaterAlpha(toSort[marker], current)) {
                        toSort[marker + 1] = toSort[marker];
                        marker--;
                    } else {
                        break;
                    }
                }
                toSort[marker + 1] = current;
            }
        }
    }

       /*---------------------------------------------------------------------
        |  Method firstGreaterAlpha
        |
        |  Purpose:  This method compares two String objects by their earliest
        |        substrings beginning with an alphabetic character,
        |        returning true if the first String's substring is
        |        larger than the second.
        |        alphaSort calls this method to make its comparisons.
        |
        |  Pre-condition:  first and second are assumed to be Strings with 
        |            initial characters that are either hyphens or
        |            alphanumeric; first and second are each
        |            assumed to contain an alphabetic character.
        |
        |  Post-condition: first and second have been compared using their 
        |            respective substrings beginning with the first
        |            alphabetic character in each String; first
        |            and second are both unchanged.
        |
        |  Parameters: 
        |    first -- this is the first String whose substring is to be 
        |        compared.
        |    second -- this is the second String whose substring is to be 
        |        compared.
        |
        |  Returns:  True if the substring of first from the first instance of 
        |        an alphabetic character to the end of the String has 
        |        a larger ASCII value than the substring of second from
        |        the first instance of an alphabetic character to the
        |        end of second (both inclusive); and returns false 
        |        otherwise.
        *-------------------------------------------------------------------*/

    private static boolean firstGreaterAlpha(String first, String second) {
        int firstLetterIndex = -1;
        for (int i = 0; i < first.length(); i++) {
            if (first.substring(i, i + 1).matches("[a-zA-Z]")) {
                firstLetterIndex = i;
                break;
            }
        }
        int secondLetterIndex = -1;
        for (int i = 0; i < second.length(); i++) {
            if (second.substring(i, i + 1).matches("[a-zA-Z]")) {
                secondLetterIndex = i;
                break;
            }
        }
        if (firstLetterIndex != -1 && secondLetterIndex != -1 
                && first.substring(firstLetterIndex).compareTo(
                second.substring(secondLetterIndex)) > 0)
            return true;
        return false;
    }

       /*---------------------------------------------------------------------
        |  Method goodInput
        |
        |  Purpose:  This method checks whether the String argument is a valid
        |        input String. A valid input String begins with an
        |        alphanumeric character or a hyphen, and contains at
        |        least one letter.
        |
        |  Pre-condition:  None.
        |
        |  Post-condition: input is unchanged; input has been verified as 
        |            an acceptable input String or has been
        |            rejected.
        |
        |  Parameters: 
        |    input -- the String to be verified.
        |
        |  Returns:  True if input begins with an alphanumeric character or a 
        |        hyphen and contains at least one letter; false 
        |        otherwise.
        *-------------------------------------------------------------------*/

    private static boolean goodInput(String input) {
        if (!input.matches(".*[a-zA-Z].*"))
            return false;
        if (!input.substring(0,1).matches("[-a-zA-Z0-9]"))
            return false;
        return true;
    }
}
