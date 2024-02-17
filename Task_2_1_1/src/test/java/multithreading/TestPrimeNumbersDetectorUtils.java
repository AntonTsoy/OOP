package multithreading;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimeNumbersDetectorUtilsTest {

    @Test
    void isPrimeNegativeNumbers() {
        assertFalse(PrimeNumbersDetectorUtils.isPrimeNumber(-1));
        assertFalse(PrimeNumbersDetectorUtils.isPrimeNumber(-10));
    }

    @Test
    void isPrimeZeroAndOne() {
        assertFalse(PrimeNumbersDetectorUtils.isPrimeNumber(0));
        assertFalse(PrimeNumbersDetectorUtils.isPrimeNumber(1));
    }

    @Test
    void isPrimeForPrimeNumbers() {
        assertTrue(PrimeNumbersDetectorUtils.isPrimeNumber(2));
        assertTrue(PrimeNumbersDetectorUtils.isPrimeNumber(7));
        assertTrue(PrimeNumbersDetectorUtils.isPrimeNumber(11));
    }

    @Test
    void isPrimeNonPrimeNumbers() {
        assertFalse(PrimeNumbersDetectorUtils.isPrimeNumber(4));
        assertFalse(PrimeNumbersDetectorUtils.isPrimeNumber(9));
        assertFalse(PrimeNumbersDetectorUtils.isPrimeNumber(10));
    }

    @Test
    void isPrimeLargePrimeNumbers() {
        assertTrue(PrimeNumbersDetectorUtils.isPrimeNumber(7919));
        assertTrue(PrimeNumbersDetectorUtils.isPrimeNumber(7907));
        assertTrue(PrimeNumbersDetectorUtils.isPrimeNumber(7879));
    }
}
