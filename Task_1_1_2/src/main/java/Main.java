import java.util.Arrays;

/**
 * Entry-point class.
 */
public class Main {

    /**
     * Static entry method.
     *
     * @param args Cmd args.
     */
    public static void main(String[] args) {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8});
        System.out.println(p1.add(p2.differentiate(1)).toString());
        System.out.println(p1.multiply(p2).evaluate(2));
        
        System.out.println(p1.equals(null));
    }
}
