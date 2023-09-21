import java.util.Arrays;

/**
 * Comment.
 */
public class Polynomial {

    private final int[] polynomCoeffs;
    public final int polynomDegree;

    /**
     * Comment.
     */
    private static int[] transformToPolynom(int[] supPolynom) {
        int polynomDegree = supPolynom.length;
        
        while (supPolynom[polynomDegree - 1] == 0 && polynomDegree > 1) {
            polynomDegree -= 1;
        }

        int[] polynomCoeffs = new int[polynomDegree];
        System.arraycopy(supPolynom, 0, polynomCoeffs, 0, polynomDegree);

        return polynomCoeffs;
    }

    /**
     * Comment.
     */
    public Polynomial(int[] polynomCoeffs) {
        this.polynomCoeffs = transformToPolynom(polynomCoeffs);
        this.polynomDegree = this.polynomCoeffs.length;
    }

    /**
     * Comment.
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
     * Comment.
     */    
    public Polynomial subtract(Polynomial subtracted) {
        int[] differenceOfCoeffs = subtracted.polynomCoeffs.clone();

        for (int i = 0; i < subtracted.polynomDegree; i++) {
            differenceOfCoeffs[i] *= -1;
        }

        return this.add(new Polynomial(differenceOfCoeffs));
    }

    /**
     * Comment.
     */      
    public Polynomial multiply(Polynomial multiplier) {
        int[] multOfCoeffs = new int[this.polynomDegree + multiplier.polynomDegree - 1];

        for (int i = 0; i < this.polynomDegree; i++) {
            for (int j = 0; j < multiplier.polynomDegree; j++) {
                multOfCoeffs[i+j] += this.polynomCoeffs[i] * multiplier.polynomCoeffs[j];
            }
        }

        return new Polynomial(transformToPolynom(multOfCoeffs));
    }

    /**
     * Comment.
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

    /**
     * Comment.
     */  
    private static int reverseFactorial(int factorialBase, int factorialPow) {
        int result = 1;
        
        for (int i = factorialPow; i > 0; i--) {
            result *= factorialBase--; 
        }

        return result;
    }

    /**
     * Comment.
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
     * Comment.
     */  
    public boolean isEqual(Polynomial comparable) {
        int[] firstCoeffs = transformToPolynom(this.polynomCoeffs);
        int[] secondCoeffs = transformToPolynom(comparable.polynomCoeffs);
        return Arrays.equals(firstCoeffs, secondCoeffs);
    }

    /**
     * Comment.
     */ 
    private static String monomSign(int monom) {
        if (monom < 0) {
            return " - ";
        }
        else if (monom > 0) {
            return " + ";
        }
        return "";
    }

    /**
     * Comment.
     */ 
    private static String monomForm(int monom, int pow, boolean sign) {
        String monomStr = "";
        if (monom == 0) {
            return monomStr;
        }

        monomStr += sign ? monomSign(monom) : "";
        if (monom != 1 && monom != -1 || pow == 0) {
            monomStr += sign ? Math.abs(monom) : monom;
        }

        if (pow > 0) {
            monomStr += "x";
            if (pow != 1) {
                monomStr += "^" + pow;
            }
        }

        return monomStr;
    }

    /**
     * Comment.
     */   
    public String toString() {
        int[] curPolynom = transformToPolynom(this.polynomCoeffs);

        if (curPolynom.length == 1 && curPolynom[0] == 0) {
            return "0";
        }
        else {
            int curId = curPolynom.length - 1;
            String polynomStr = monomForm(curPolynom[curId], curId--, false);

            while (curId >= 0) {
                polynomStr += monomForm(curPolynom[curId], curId--, true);
            }

            return polynomStr;
        }
    }
}