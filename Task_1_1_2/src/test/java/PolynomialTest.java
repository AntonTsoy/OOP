import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class PolynomialTest {

    @Test
    void testPolynomialPresentation() {
        Polynomial p1 = new Polynomial(new int[] {2, 1, 3});
        assertEquals(p1.toString(), "3x^2 + x + 2");

        Polynomial p2 = new Polynomial(new int[] {0, 0, 0, 0});
        assertEquals(p2.toString(), "0");

        Polynomial p3 = new Polynomial(new int[] {-839, 0, -3, 0, 0, 1000, -1, -10, 0});
        assertEquals(p3.toString(), "-10x^7 - x^6 + 1000x^5 - 3x^2 - 839");

        Polynomial p4 = new Polynomial(new int[] {-1});
        assertEquals(p4.toString(), "-1");
    }

    @Test
    void testSum() {
        Polynomial p1 = new Polynomial(new int[] {1, 2, 3});
        
        Polynomial p2 = p1.add(new Polynomial(new int[] {-10, 9}));
        assertEquals(p2.toString(), "3x^2 + 11x - 9");
        
        Polynomial p3 = p2.add(new Polynomial(new int[] {0, 5, 2, -9, -8, 1, 0}));
        assertEquals(p3.toString(), "x^5 - 8x^4 - 9x^3 + 5x^2 + 16x - 9");
    }

    @Test
    void testDiff() {
        Polynomial p1 = new Polynomial(new int[] {0});

        Polynomial p2 = p1.subtract(new Polynomial(new int[] {10}));
        assertEquals(p2.toString(), "-10");

        Polynomial p3 = p2.subtract(new Polynomial(new int[] {-10, 10, -10, 10}));
        assertEquals(p3.toString(), "-10x^3 + 10x^2 - 10x");
    }

    @Test
    void testProduct() {
        Polynomial p1 = new Polynomial(new int[] {});

        Polynomial p2 = p1.multiply(new Polynomial(new int[] {1, 20, 3, 40, 5}));
        assertEquals(p2.toString(), "0");

        Polynomial p3 = new Polynomial(new int[] {5, 16, 7, 28});
        Polynomial p4 = p3.multiply(new Polynomial(new int[] {100, 0, -3, 0}));
        assertEquals(p4.toString(), "-84x^5 - 21x^4 + 2752x^3 + 685x^2 + 1600x + 500");
    }

    @Test
    void testEval() {
        Polynomial p1 = new Polynomial(new int[] {-1, 2, 0, 5});
        assertEquals(p1.evaluate(0), -1);

        Polynomial p2 = new Polynomial(new int[] {0, 100, -2, 1, 0});
        assertEquals(p2.evaluate(-3), -345);
    }

    @Test 
    void testDerivat() {
        Polynomial p1 = new Polynomial(new int[] {1, 2});
        assertEquals(p1.differentiate(3).toString(), "0");

        Polynomial p2 = new Polynomial(new int[] {9, -1, 0});
        assertEquals(p2.differentiate(0).toString(), "-x + 9");
        
        Polynomial p3 = new Polynomial(new int[] {0, 100, -3, 25, 0, 1, 0});
        assertEquals(p3.differentiate(2).toString(), "20x^3 + 150x - 6");
    }

    @Test
    void testEquality() {
        Polynomial p1 = new Polynomial(new int[] {1, 2});
        assertTrue(p1.equals(new Polynomial(new int[] {1, 2, 0, 0})));

        Polynomial p2 = new Polynomial(new int[] {0, 13, 40, 5, 0, 26});
        assertFalse(p2.equals(new Polynomial(new int[] {0, 13, 40, 5, 0, 26, -1, 0})));

        Polynomial p3 = new Polynomial(new int[] {});
        assertTrue(p3.equals(new Polynomial(new int[] {0})));
    }
}
