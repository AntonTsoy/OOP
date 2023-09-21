import java.util.Arrays;

/**
 * Entry-point class.
 */
public class Main {

    public static void main(String[] args) {
        // {-1, 0, 1, 2, -3} === -3x^4 + 2x^3 + x^2 - 1
        // {-10} === -10
        // {0, 5} === 5x
        Polynomial ppp = new Polynomial(new int[] {0, 1, 0, 0, 0});
        System.out.println(ppp.toString());

    }
}
