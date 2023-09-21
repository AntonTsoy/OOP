import java.util.Arrays;

/**
 * Entry-point class.
 */
public class Main {

    public static void main(String[] args) {
        Polynomial pol1 = new Polynomial(new int[] {1, 2, 3});
        Polynomial pol2 = pol1.multiply(new Polynomial(new int[] {0, 1, 2}));
    
        //System.out.println(Arrays.toString(pol2.polynomCoeffs));

        Polynomial pol3 = new Polynomial(new int[] {5, 4, 3, 2, 6, 7, 8});
        //System.out.println(Arrays.toString(pol3.differentiate(3).polynomCoeffs));

        System.out.println(pol1.evaluate(10));

        String a = "";
        System.out.println(a+1);

        // {-1, 0, 1, 2, -3} === -3x^4 + 2x^3 + x^2 - 1
        // {-10} === -10
        // {0, 5} === 5x
        Polynomial p = new Polynomial(new int[] {0, 0});
        System.out.println(p.toString());
    }
}
