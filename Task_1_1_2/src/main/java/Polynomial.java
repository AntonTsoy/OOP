import java.util.Arrays;

/**
 * The Polynomial class represents a polynomial with integer coefficients.
 */
public class Polynomial {

    // Array of polynomial coefficients
    private final int[] polynomCoeffs;
    // Degree of senior coeffincient
    private final int polynomDegree;

    /**
     * Converts an array of coefficients into a polynomial.
     * Deletes zero senior coefficients.
     *
     * @param supPolynom initial array of coefficients.
     * @return polynomial coefficient array. 
     */
    private static int[] transformToPolynom(int[] supPolynom) {
        int polynomDegree = supPolynom.length;

        if (0 == polynomDegree) {
            return new int[] {0};
        }
        
        while (supPolynom[polynomDegree - 1] == 0 && polynomDegree > 1) {
            polynomDegree -= 1;
        }

        int[] polynomCoeffs = new int[polynomDegree];
        System.arraycopy(supPolynom, 0, polynomCoeffs, 0, polynomDegree);

        return polynomCoeffs;
    }

    /**
     * Creates a polynomial based on an array of coefficients.
     *
     * @param polynomCoeffs array of coefficients. 
     */
    public Polynomial(int[] polynomCoeffs) {
        this.polynomCoeffs = transformToPolynom(polynomCoeffs);
        this.polynomDegree = this.polynomCoeffs.length;
    }

    /**
     * Adds this polynomial to another polynomial.
     *
     * @param summand polynomial.   
     * @return sum of polynomials.
     */
    public Polynomial add(Polynomial summand) {
        int[] sumOfCoeffs = new int[Math.max(this.polynomDegree, summand.polynomDegree)];

        System.arraycopy(this.polynomCoeffs, 0, sumOfCoeffs, 0, this.polynomDegree);

        for (int i = 0; i < summand.polynomDegree; i++) {
            sumOfCoeffs[i] += summand.polynomCoeffs[i];
        }

        return new Polynomial(transformToPolynom(sumOfCoeffs));
    }

    /**
     * Subtracts another polynomial from this polynomial.
     *
     * @param subtracted polynomial.
     * @return polynomial difference.
     */   
    public Polynomial subtract(Polynomial subtracted) {
        int[] differenceOfCoeffs = subtracted.polynomCoeffs.clone();

        for (int i = 0; i < subtracted.polynomDegree; i++) {
            differenceOfCoeffs[i] *= -1;
        }

        return this.add(new Polynomial(differenceOfCoeffs));
    }

    /**
     * Multiplies this polynomial by another polynomial.
     *
     * @param multiplier polynomial.
     * @return product of polynomials.
     */ 
    public Polynomial multiply(Polynomial multiplier) {
        int[] multOfCoeffs = new int[this.polynomDegree + multiplier.polynomDegree - 1];

        for (int i = 0; i < this.polynomDegree; i++) {
            for (int j = 0; j < multiplier.polynomDegree; j++) {
                multOfCoeffs[i + j] += this.polynomCoeffs[i] * multiplier.polynomCoeffs[j];
            }
        }

        return new Polynomial(transformToPolynom(multOfCoeffs));
    }

    /**
     * Calculates the value of a polynomial at a point.
     *
     * @param paramValue argument.
     * @return polynomial value in point.
     */ 
    public long evaluate(int paramValue) {
        long valueOfPolynom = 0L;
        long curParamPow = 1;
        
        for (int i = 0; i < this.polynomDegree; i++) {
            valueOfPolynom += this.polynomCoeffs[i] * curParamPow;
            curParamPow *= paramValue;
        }

        return valueOfPolynom;
    }

    private static int reverseFactorial(int factorialBase, int factorialPow) {
        int result = 1;
        
        for (int i = factorialPow; i > 0; i--) {
            result *= factorialBase--; 
        }

        return result;
    }

    /**
     * Calculates the derivative of a polynomial of a given degree.
     *
     * @param derivativeDegree argument.
     * @return polynomial derivative.  
     */ 
    public Polynomial differentiate(int derivativeDegree) {
        if (derivativeDegree < 1) {
            return this;
        }

        int newDegree = this.polynomDegree - derivativeDegree;
        if (newDegree < 1) {
            return new Polynomial(new int[] {0});
        }
        
        int[] derevativedCoeffs = new int[newDegree];
        
        System.arraycopy(this.polynomCoeffs, derivativeDegree, derevativedCoeffs, 0, newDegree);

        for (int i = derivativeDegree; i < this.polynomDegree; i++) {
            derevativedCoeffs[i - derivativeDegree] *= reverseFactorial(i, derivativeDegree);
        }

        return new Polynomial(derevativedCoeffs);
    }

    /**
     * Checks the equality of this polynomial and the other polynomial. 
     *
     * @param comparable another polynomial.
     * @return true if polynomials are equal, otherwise false.
     */ 
    public boolean isEqual(Polynomial comparable) {
        int[] firstCoeffs = transformToPolynom(this.polynomCoeffs);
        int[] secondCoeffs = transformToPolynom(comparable.polynomCoeffs);
        return Arrays.equals(firstCoeffs, secondCoeffs);
    }

    private static String monomSign(int monom) {
        if (monom < 0) {
            return " - ";
        } else if (monom > 0) {
            return " + ";
        }
        return "";
    }

    private static String monomForm(int monom, int pow, boolean sign) {
        String monomStr = "";
        // If the coefficient of the monomial is zero, return an empty string
        if (0 == monom) {
            return monomStr;
        }

        if (sign) {
            monomStr += monomSign(monom);
            if (1 != monom && -1 != monom || 0 == pow) {
                monomStr += Math.abs(monom);
            }
        }
        else if (-1 == monom && pow != 0) {
            monomStr += "-";
        }
        else if (1 != monom || 0 == pow) {
            monomStr += monom;
        }

        // Adding the variable degree part
        if (pow > 0) {
            monomStr += "x";
            if (1 != pow) {
                monomStr += "^" + pow;
            }
        }

        return monomStr;
    }

    /**
     * Represents a polynomial as a string.
     *
     * @return string representation of a polynomial. 
     */
    public String toString() {
        int[] curPolynom = transformToPolynom(this.polynomCoeffs);

        // If the polynomial is zero, return (0)
        if (1 == curPolynom.length && 0 == curPolynom[0]) {
            return "0";
        }
        else {
            // We start forming the string with a monomial of higher degree
            int curId = curPolynom.length - 1;
            String polynomStr = monomForm(curPolynom[curId], curId--, false);

            // Adding the rest of the monomials
            while (curId >= 0) {
                polynomStr += monomForm(curPolynom[curId], curId--, true);
            }

            return polynomStr;
        }
    }
}