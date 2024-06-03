package network;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;


/**
 * Класс для тестирования работы Детектеров.
 */
public class TestPrimeNumberDetector {

    void primeCornerCase() {
        Integer[] arr = new Integer[]{};
        Assertions.assertFalse(PrimeNumberDetector.isNotPrimeNumbers(Arrays.asList(arr)));
    }

    void nonPrimeHardTest() {
        Integer[] arr = new Integer[]{
            2, 13, 6997927, 6997937, 17858849, 6997967, 10000000, 999999999
        };
        Assertions.assertTrue(PrimeNumberDetector.isNotPrimeNumbers(Arrays.asList(arr)));
    }
}
