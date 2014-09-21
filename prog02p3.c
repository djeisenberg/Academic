/*============================================================================
 |   Assignment:  Program 2: Shell I/O and Basic C Programs
 |                  Part 3: Amicable Numbers
 |
 |       Author:  Daniel Eisenberg
 |     Language:  C, tested using gcc compiled code.
 |   To Compile:  Type "gcc prog02p3.c" into shell.
 |
 |        Class:  C Sc 352 -- Systems Programming and UNIX
 |   Instructor:  Lester McCann
 |     Due Date:  Semptember 8th, 2011, 5:00pm
 |
 +----------------------------------------------------------------------------
 |
 |  Description:  This program is intended to find all pairs of amicable 
 |                  numbers (or amicable pairs) where at least one number of 
 |                  the pair is less than a user-specified integer, and print 
 |                  these pairs to stdout.
 |
 |        Input:  One integer to provide an upper bound on the amicable pairs 
 |                  to be found; this integer is assumed to be of type 
 |                  unsigned int.
 |
 |       Output:  0 or more lines printed to stdout containing all amicable
 |                  pairs where at least one of the numbers is less than the
 |                  limiting integer provided by the user; each line is
 |                  formatted as
 |                  (X,Y)
 |                  where X, Y are amicable numbers and X < Y
 |
 |    Algorithm:  Given an upper bound b, each integer at least 220 and less
 |                  than b can be checked to see whether it belongs to an
 |                  amicable pair by taking the sum of all its positive 
 |                  factors other than itself and checking whether the sum  
 |                  is a different number whose factors (other than itself)  
 |                  sum to the original number. Factors of a number n are  
 |                  checked by modulus operations on each integer from 2 to 
 |                  n/2 inclusive. If n % k is 0, then k is a factor of n,  
 |                  where k is an integer. Numbers below 220 are not checked 
 |                  because the smallest amicable pair is (220, 284). To 
 |                  prevent repitition, (and to avoid reporting perfect 
 |                  numbers as amicable pairs with themselves) a number is  
 |                  only checked when the sum of its factors is larger than 
 |                  the number itself.
 |
 |   Required Features Not Included:  None known.
 |
 |   Known Bugs: None. 
 |
 *==========================================================================*/

#include <stdio.h>

#define LOWER_BOUND 220L /* smallest number with an amicable pair */

unsigned long sumFactors(unsigned long n);

int main(void) {

    unsigned int limit; /* this is the user-specified upper bound */

    scanf("%u", &limit);

    unsigned long i; /* for loop iterator */
    for (i = LOWER_BOUND; i < limit; i++) {
        unsigned long result = sumFactors(i); /* sum of i's factors */
        if (result > i)
            if (sumFactors(result) == i)
                printf("(%lu,%lu)\n", i, result);
    }

}

        /*------------------------------------------------- sumFactors -----
         |  Function sumFactors
         |
         |  Purpose:  sumFactors sums all positive factors of n which are not
         |              n itself. sumFactors is required by main to find
         |              amicable pairs.
         |
         |  Parameters:
         |      n (IN) -- an unsigned long type integer; n is assumed to be at
         |              least 220.
         |
         |  Returns:  An integer of type unsigned long representing the sum  
         |              of all positive factors of n not including n itself.
         *------------------------------------------------------------------*/

unsigned long sumFactors(unsigned long n /* an arbitrary pos. integer */) {
    unsigned long i; /* for loop iterator */
    unsigned long sum_of_factors = 1L; /* sum of n's pos. factors save n */
    for (i = 2L; i <= n / 2; i++) {
        if (n % i == 0)
            sum_of_factors += i;
    }

    return sum_of_factors;
}
