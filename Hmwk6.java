/*=============================================================================
 |   Assignment:  Program: Homework #6
 |       Author:  Daniel Eisenberg
 |       Grader:  Isabel Kishi
 |
 |       Course:  CSc 245 - Introduction to Discrete Structures
 |   Instructor:  L. McCann
 |     Due Date:  April 8th, 2011, 2:00PM
 |
 |  Description:  This program evaluates parameters for linear congruential
 |                functions of the form
 |                        x_n+1 = (a * x_n + c) % m
 |                to probabilistically determine the suitability of the
 |                resulting function as a pseudo-random number generator for
 |                applications requiring a simple linear congruential pseudo-
 |                random number generator (LCPRNG) which need not hold up to
 |                significant scrutiny.
 |
 |                The program takes command line arguments of a, c, m, x_0,
 |                and n; where x_0 is the initial value for the function and
 |                n is the number of outputs for which the function should be
 |                tested.
 |
 |                For example, to test the suitability of the linear
 |                congruential function (LCF)
 |                        x_n+1 = (211 * x_n + 907) % 1900
 |                with initial value x_0 = 28 for all outputs from x_1 to
 |                x_30000 inclusive, this program should be invoked with
 |                command line arguments:
 |                        211 907 1900 28 30000
 |
 |                The program will check that the arguments satisfy the
 |                following conditions:
 |                  1. a > 0 and c > 0
 |                  2. max(a, c, x_0) < m
 |                  3. c and m are relatively prime (That is, GCD(c, m) = 1)
 |                The program will print to standard output whether these
 |                conditions were satisfied; if one or more were not satisfied
 |                the violated conditions will be specified using the
 |                condition numbers given above.
 |
 |                The LCF is evaluated by Pearson's chi-square test using an
 |                assumption of uniform distribution of the LCF's outputs
 |                over the range of the LCF. The resulting calculation,
 |                hereafter referred to as V, will be printed to standard
 |                output along with a determination of "ok", "suspicious", or
 |                "rejected". The general rubric for this determination is
 |                that a good candidate function should have a distribution
 |                of outputs across its range which is neither very uniform
 |                nor too heavily skewed toward some subset of the range.
 |
 |                For the example given above, the program will print
 |                        Conditions satisfied
 |                        V = 15.1700; this function is ok
 |
 |                Note that if input conditions are violated, the output
 |                value of V is only mathematically consistent by java's
 |                definition of modulus (or any equivalent definition).
 |
 | Deficiencies:  The distribution algorithm used requires that the value of
 |                m be no greater than (2^31 - 1) / 10.
 *===========================================================================*/

import java.text.DecimalFormat;

public class Hmwk6
{
	public static void main(String[] args) {
		// resolve command line arguments to int values
		// name parameters per LCF definition in header comment
		int a = Integer.parseInt(args[0]);
		int c = Integer.parseInt(args[1]);
		int m = Integer.parseInt(args[2]);
		int x0 = Integer.parseInt(args[3]);
		int n = Integer.parseInt(args[4]);

		// conditions 1, 2, and 3 as defined in header comment
		boolean condition1 = (a > 0 && c > 0);
		boolean condition2 = (a < m && c < m && x0 < m);
		boolean condition3 = gcd(c, m) == 1;

		// with conditions checked, and since x % y = x % (-y)
		// under java's modulus operator, we will take the
		// absolute value of m to prevent any possible
		// exceptions resulting from negative values for m
		if (m < 0) {
			m *= -1;
		}

		// create 10 buckets to tally the LCF's distribution
		// each bucket is initialized to 0
		int[] distribution = new int[10];

		// find distribution
		int x = x0; // function initial value
		for (int i = 1; i <= n; i++) {
			x = (a * x + c) % m; // x has value x_i

			// since we want outputs from 0 to m-1
			// inclusive, and since java's modulus
			// operator produces negative output only
			// when the dividend is negative, and since
			// negative values for x_0 are acceptable;
			// we must perform negative checks on x
			if (x < 0) { // -m < x < 0
				x += m; // 0 < x < m
			}

			// find bucket for this x
			int bucket = 9;

			// since x < m, 10x < 10m
			// in this way, each bucket has a range
			// differing by at most 1 from each other
			// bucket
			while (10 * x < bucket * m) {
				bucket--;
			}
			// increment count for the bucket of this x
			distribution[bucket]++;
		}

		// calculate V as defined in header comment
		double v = pearsonChiSquare(distribution, n);

		// assemble output
		String output = "";

		// report condition check results
		if (condition1 && condition2 && condition3) {
			output += "Conditions satisfied";
		} else { // at least one condition not satisfied
			if (!condition1) {
				output += "Condition 1 not satisfied";
			}

			if (!condition2) {
				// check if line break needed
				if (!condition1) {
					output += "\n";
				}

				output += "Condition 2 not satisfied";
			}

			if (!condition3) {
				// check if line break needed
				if (!(condition1 && condition2)) {
					output += "\n";
				}

				output+= "Condition 3 not satisfied";
			}
		}


		// report V and decide function suitability

		// format V
		DecimalFormat fourPlaces = new DecimalFormat("0.0000");
		output += "\nV = " + fourPlaces.format(v) + "; ";

		// report conclusion
		output += "this function is ";
		if (v >= 3.325 && v <= 16.92) {
			output += "ok";
		} else if (v >= 2.088 && v <= 21.67) {
			output += "suspicious";
		} else {
			output += "rejected";
		}

		// print report to standard output
		System.out.println(output);
	}


       /*---------------------------------------------------------------------
        |  Method  gcd
        |
        |  Purpose:  Find the greatest common divisor (GCD) of two
        |	integers. This method implements the Euclidean algorithm.
        |
        |  Pre-condition:  a and b are integers.
        |
        |  Post-condition:  The GCD of a and b has been identified.
        |
        |  Parameters:
        |	a, b -- the integers whose GCD is to be determined.
        |
        |  Returns:  An int representing the GCD of a and b.
        *-------------------------------------------------------------------*/

	public static int gcd(int a, int b) {
		// take absolute values
		if (a < 0) {
			a *= -1;
		}
		if (b < 0) {
			b *= -1;
		}

		// ensure a > b
		if (a < b) {
			int temp = a;
			a = b;
			b = temp;
		}
		// a = b * (a / b) + (a % b)
		// then b = (a % b) * ((a % b) / b) + (a % b) % b
		// by the division algorithm. Execute until some
		// such product has no remainder, the remainder of
		// the previous equation is GCD(a, b).
		while (b > 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}


       /*---------------------------------------------------------------------
        |  Method  pearsonChiSquare
        |
        |  Purpose:  Calculate the output of Pearson's chi-sqaure test for
        |	a given distribution (buckets) of some sample size (size)
        |	under a hypothesis of uniform distribution of values.
        |	buckets is assumed to contain the counts of each class
        |	(or bucket) under some distribution of a number of
        |       elements equal to size.
        |
        |  Pre-condition:  buckets has positive length and is populated with
        |	nonnegative values; the sum of the values of buckets is size;
        |	size is positive; the length of buckets is the number of
        |	classes (or buckets) used in the distribution.
        |
        |  Post-condition:  buckets and size are unchanged; the result of
        |	Pearson's chi-square test under the assumption of uniform
        |	distribution has been calculated; this result is nonnegative.
        |
        |  Parameters:
        |	buckets -- the distribution array. This is an array of
        |		nonnegative integers.
        |	size -- the sample size of elements in the distribution.
        |
        |  Returns:  A double representing the result of Pearson's chi-square
        |	test for inputs of buckets and size, under a hypothesis of
        |	uniform distribution.
        *-------------------------------------------------------------------*/

	public static double pearsonChiSquare(int[] buckets, int size) {
		double result = 0.0;

		// expected value for uniform distribution
		double expected = ((double) size) / buckets.length;

		// find the sqaure of the difference of each actual value
		// and the expected value; normalize by expected value;
		// sum all these normalized squares
		for (int i = 0; i < buckets.length; i++) {
			result += (buckets[i] - expected)
				 * (buckets[i] - expected) / expected;
		}

		return result;
	}
}
