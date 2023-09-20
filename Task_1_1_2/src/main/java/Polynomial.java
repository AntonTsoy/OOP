import java.util.Arrays;

/**
 * The Polynomial class implements a math polynomial.
 */
public class Polynomial {

    public final int[] polynomCoeffs;
    public final int polynomDegree;


    public Polynomial(int[] polynomCoeffs) {
        this.polynomCoeffs = polynomCoeffs.clone();
        this.polynomDegree = this.polynomCoeffs.length;
    }


    public Polynomial add(Polynomial summand) {
        int[] sumOfCoeffs = new int[Math.max(this.polynomDegree, summand.polynomDegree)];

        System.arraycopy(this.polynomCoeffs, 0, sumOfCoeffs, 0, this.polynomDegree);

        for (int i = 0; i < summand.polynomDegree; i++) {
            sumOfCoeffs[i] += summand.polynomCoeffs[i];
        }

        return new Polynomial(sumOfCoeffs);
    }

    
    public Polynomial subtract(Polynomial subtracted) {
        int[] differenceOfCoeffs = subtracted.polynomCoeffs.clone();

        for (int i = 0; i < subtracted.polynomDegree; i++) {
            differenceOfCoeffs[i] *= -1;
        }

        return this.add(new Polynomial(differenceOfCoeffs));
    }

    
    public Polynomial multiply(Polynomial multiplier) {
        int[] multOfCoeffs = new int[this.polynomDegree + multiplier.polynomDegree - 1];

        for (int i = 0; i < this.polynomDegree; i++) {
            for (int j = 0; j < multiplier.polynomDegree; j++) {
                multOfCoeffs[i+j] += this.polynomCoeffs[i] * multiplier.polynomCoeffs[j];
            }
        }

        return new Polynomial(multOfCoeffs);
    }


    private static long fastPow(int base, int degree) {
        long resultPow = 1L;
        long accum = base;
        
        while (degree > 0) {
            if (degree % 2 != 0) {
                resultPow *= accum;
            }
            accum *= accum;
            degree /= 2;
        }
        
        return resultPow;
    }


    public long evaluate(int paramValue) {
        long valueOfPolynom = 0L;
        
        for (int i = 0; i < this.polynomDegree; i++) {
            valueOfPolynom += this.polynomCoeffs[i] * fastPow(paramValue, i);
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


    public Polynomial differentiate(int derivativeDegree) {
        int[] derevativedCoeffs = new int[this.polynomDegree - derivativeDegree];
        
        System.arraycopy(this.polynomCoeffs, derivativeDegree, derevativedCoeffs, 0, derevativedCoeffs.length);

        for (int i = derivativeDegree; i < this.polynomDegree; i++) {
            derevativedCoeffs[i - derivativeDegree] *= reverseFactorial(i, derivativeDegree);
        }

        return new Polynomial(derevativedCoeffs);
    }


    public boolean isEqual(Polynomial comparable) {
        return Arrays.equals(this.polynomCoeffs, comparable.polynomCoeffs);
    }


    private static Polynomial transformToPolynom(int[] supPolynom) {
        int polynomDegree = supPolynom.length;
        
        for (int i = polynomDegree - 1; i > 0; i--) {
            if (supPolynom[i] != 0) {
                polynomDegree = i + 1;
            }
        }
        
        int[] polynomCoeffs = new int[polynomDegree];
        System.arraycopy(supPolynom, 0, polynomCoeffs, 0, polynomDegree);

        return new Polynomial(polynomCoeffs);
    }



    public void toString() {
        curPolynom = transformToPolynom(this.polynomCoeffs);

        if (curPolynom.polynomDegree = 1 && curPolynom.polynomCoeffs[0] == 0) {
            System.out.println("0");
        }
        else {
            //
            System.out.println();
        }
    }


}