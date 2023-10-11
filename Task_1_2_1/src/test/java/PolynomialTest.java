import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class PolynomialTest {

    // Equality
    @Test
    void testStdEquality() {
        Polynomial p = new Polynomial(new int[] {1, 2, 7, -10, 15, 0, 0, 30});
        assertTrue(p.equals(new Polynomial(new int[] {1, 2, 7, -10, 15, 0, 0, 30})));
    }

    @Test
    void testStdUnEquality() {
        Polynomial p = new Polynomial(new int[] {0, 13, 40, 5, 0, 26});
        assertFalse(p.equals(new Polynomial(new int[] {0, 13, 40, 5, 0, 26, -1, 0})));
    }

    @Test
    void testExtremeEquality() {
        Polynomial p = new Polynomial(new int[] {0});
        assertTrue(p.equals(new Polynomial(new int[] {})));
    }
    
    @Test
    void testExtremeUnEquality() {
        Polynomial p = new Polynomial(new int[] {});
        assertFalse(p.equals(null));
    }

    @Test
    void testTypeUnEquality() {
        Polynomial p = new Polynomial(new int[] {0});
        assertFalse(p.equals(new String("TEST")));
    }

    @Test 
    void testSymmetry() {
        Polynomial p1 = new Polynomial(new int[] {24, -34, 0, 32, 12, -98, 0});
        Polynomial p2 = new Polynomial(new int[] {24, -34, 0, 32, 12, -98});
        assertTrue(p1.equals(p2) && p2.equals(p1));
    }

    @Test 
    void testTransitivity() {
        Polynomial p1 = new Polynomial(new int[] {0, -24, 0, 9348534});
        Polynomial p2 = new Polynomial(new int[] {0, -24, 0, 9348534, 0, 0, 0, 0});
        Polynomial p3 = new Polynomial(new int[] {0, -24, 0, 9348534, 0});
        assertTrue(p1.equals(p2) && p2.equals(p3) && p1.equals(p3));
    }


    // Presentation
    @Test
    void testStdPresent() {
        Polynomial p = new Polynomial(new int[] {2, 1, 3});
        assertEquals(p.toString(), "3x^2 + x + 2");
    }

    @Test
    void testZeroPresent() {
        Polynomial p = new Polynomial(new int[] {0, 0, 0, 0});
        assertEquals(p.toString(), "0");
    }

    @Test
    void testPassLongPresent() {
        Polynomial p = new Polynomial(new int[] {-839, 0, -3, 0, 0, 1000, -1, -10, 0});
        assertEquals(p.toString(), "-10x^7 - x^6 + 1000x^5 - 3x^2 - 839");
    }

    @Test
    void testWeakPresent() {
        Polynomial p = new Polynomial(new int[] {-2});
        assertEquals(p.toString(), "-2");
    }

    @Test
    void testExtremePresent() {
        Polynomial p = new Polynomial(new int[] {-1, -2});
        assertEquals(p.toString(), "-2x - 1");
    }


    // Additional
    @Test
    void testStdSum() {
        Polynomial p1 = new Polynomial(new int[] {1, 2, 3});
        Polynomial p2 = p1.add(new Polynomial(new int[] {-10, 9}));
        assertEquals(p2, new Polynomial(new int[] {-9, 11, 3}));
    }

    @Test
    void testExtremeSum() {
        Polynomial p1 = new Polynomial(new int[] {});
        Polynomial p2 = p1.add(new Polynomial(new int[] {-1000}));
        assertEquals(p2, new Polynomial(new int[] {-1000}));
    }

    @Test 
    void testLongSum() {
        Polynomial p1 = new Polynomial(new int[] {-9, 11, 3});
        Polynomial p2 = p1.add(new Polynomial(new int[] {0, 5, 2, -9, -8, 1, 0}));
        assertEquals(p2, new Polynomial(new int[] {-9, 16, 5, -9, -8, 1}));
    }


    // Difference
    @Test
    void testStdDiff() {
        Polynomial p1 = new Polynomial(new int[] {0});
        Polynomial p2 = p1.subtract(new Polynomial(new int[] {10}));
        assertEquals(p2, new Polynomial(new int[] {-10}));
    }

    @Test
    void testLongDiff() {
        Polynomial p1 = new Polynomial(new int[] {-10, 10, -10, 10, 0, -3458, 112});
        Polynomial p2 = p1.subtract(new Polynomial(new int[] {-10, -10, -10, -10, -5000, 1}));
        assertEquals(p2, new Polynomial(new int[] {0, 20, 0, 20, 5000, -3459, 112}));
    }

    @Test
    void testExtremeDiff() {
        Polynomial p1 = new Polynomial(new int[] {});
        assertEquals(p1.subtract(new Polynomial(new int[] {0, 0})), new Polynomial(new int[] {0}));
    }


    // Multiplication
    @Test
    void testExtremeProduct() {
        Polynomial p1 = new Polynomial(new int[] {});
        Polynomial p2 = p1.multiply(new Polynomial(new int[] {1, 20, 3, 40, 5}));
        assertEquals(p2, new Polynomial(new int[] {0}));
    }

    @Test
    void testLongProduct() {
        Polynomial p1 = new Polynomial(new int[] {5, 16, 7, 28});
        Polynomial p2 = p1.multiply(new Polynomial(new int[] {100, 0, -3, 0}));
        assertEquals(p2, new Polynomial(new int[] {500, 1600, 685, 2752, -21, -84}));
    }

    @Test
    void testStdProduct() {
        Polynomial p1 = new Polynomial(new int[] {1, -2, 3});
        Polynomial p2 = p1.multiply(new Polynomial(new int[] {4, -5}));
        assertEquals(p2, new Polynomial(new int[] {4, -13, 22, -15}));
    }


    // Evaluation
    @Test
    void testStdEval() {
        Polynomial p = new Polynomial(new int[] {-1, 2, 0, 5});
        assertEquals(p.evaluate(0), -1L);
    }

    @Test
    void testLongEval() {
        Polynomial p = new Polynomial(new int[] {0, 100, -2, 1, 0, 0, 0});
        assertEquals(p.evaluate(-3), -345L);
    }

    @Test 
    void testExtremeEval() {
        Polynomial p = new Polynomial(new int[] {});
        assertEquals(p.evaluate(3458238), 0L);
    }


    // Differencial
    @Test 
    void testLongDerivat() {
        Polynomial p = new Polynomial(new int[] {1, 2});
        assertEquals(p.differentiate(3), new Polynomial(new int[] {0}));
    }

    @Test
    void testExtremeDerivat() {
        Polynomial p = new Polynomial(new int[] {9, -1, 0});
        assertEquals(p.differentiate(0), new Polynomial(new int[] {9, -1}));
    }
        
    @Test
    void testStdDerivat() {
        Polynomial p = new Polynomial(new int[] {0, 100, -3, 25, 0, 1, 0});
        assertEquals(p.differentiate(2), new Polynomial(new int[] {-6, 150, 0, 20}));
    }
}
