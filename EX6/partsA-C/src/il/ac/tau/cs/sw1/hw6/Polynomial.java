package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class Polynomial {

	private int degre = -1;//TODO CHAECKKKK
	double[] coef;
	boolean voidPoly = false;


	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */
	public Polynomial() {//todo
		this(new double[0]);
		degre++;
		voidPoly = true;
	}


	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) {//p2 = {1,2,3} ---> 1.0+2.0*x+3.0*x^2 todo
		coef = new double[coefficients.length];
		//boolean endOfZerosTail = false;
		for (int i = 0; i < coefficients.length; i++) {
			coef[i] = coefficients[i];
			degre++;
		}
	}



	/*
	 * Adds this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial) {//good
		int limit1 = polynomial.getDegree();
		int limit2 = this.getDegree();
		int leng = limit1;

		if (limit1 < limit2) {//max
			leng = limit2;
		}

		double[] coeffSum = new double[leng+1];


		if (limit1 < limit2) {
			for (int i = 0; i < limit1 + 1; i++)
				coeffSum[i] = polynomial.getCoefficient(i) + this.getCoefficient(i);
			for (int j = limit1; j < limit2 +1; j++)
				coeffSum[j] = this.getCoefficient(j);
		}
		else {
			for (int i = 0; i < limit2 + 1; i++)
				coeffSum[i] = polynomial.getCoefficient(i) + this.getCoefficient(i);
			for (int j = limit2; j < limit1 + 1; j++)
				coeffSum[j] = polynomial.getCoefficient(j);
		}



		return new Polynomial(coeffSum);
	}


	/*
	 * Multiplies a to this polynomial and returns
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a) {
		int limit2 = this.getDegree();
		double[] coeffNew = new double[limit2+1];

		for (int i=0; i< limit2+1;i++){
			coeffNew[i] = (this.getCoefficient(i))*a;

		}
		return new Polynomial(coeffNew);
	}



	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree() {
		return degre;
	}




	/*
	 * Returns the coefficient of the variable x
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n) {
		if (voidPoly)
			return 0.0;
		return coef[n];
	}


	/*
	 * set the coefficient of the variable x
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x
	 * with degree n was 0, and now it will change to c.
	 */
	public void setCoefficient(int n, double c) {//TODO hasama to poly array
		int curr = this.getDegree();
		if (curr < n) {
			coef = Arrays.copyOf(coef, n + 1);
			this.coef[n] = c;
		}
		else
			this.coef[n] = c;
	}


	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.

	 */
	public Polynomial getFirstDerivation() {//{1*x^0,2*x^1,3*x^2}
		if (voidPoly)
			return new Polynomial();
		double[] derCoef = new double[coef.length];

		for (int i = 0; i<coef.length; i++)
			derCoef[i] =  this.coef[i]*i;

		derCoef = Arrays.copyOfRange(derCoef,1,derCoef.length);// no need of the last//TODO check of length -1

		return new Polynomial(derCoef);
	}



	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x) {
		double res = 0;
		if (this.voidPoly)
			return 0.0;
		for (int i = 0; i < degre+1; i++)
			res += (coef[i] * Math.pow(x,i));

		return res;
	}

	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x) {
		Polynomial first = this.getFirstDerivation();
		if (first.computePolynomial(x) == 0){
			Polynomial second = first.getFirstDerivation();
			return second.computePolynomial(x) != 0;
		}
		return false;
	}

}