package multithreading;


import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;


public class TestPrimeNumbersDetector {

    @ParameterizedTest()
    @ArgumentsSource(PrimeDetectorProvider.class)
    void primeCornerCase(PrimeNumbersDetector primeDetector) {
        Assertions.assertFalse(primeDetector.isNotPrimeNumbers(new Integer[]{}));
    }

    @ParameterizedTest()
    @ArgumentsSource(PrimeDetectorProvider.class)
    void nonPrimeSimpleTest(PrimeNumbersDetector primeDetector) {
        Assertions.assertTrue(primeDetector.isNotPrimeNumbers(new Integer[]{
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9
        }));
    }

    @ParameterizedTest()
    @ArgumentsSource(PrimeDetectorProvider.class)
    void primeHardTest(PrimeNumbersDetector primeDetector) {
        Assertions.assertFalse(primeDetector.isNotPrimeNumbers(new Integer[]{
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053, 20319251, 6997901
        }));
    }

    @ParameterizedTest()
    @ArgumentsSource(PrimeDetectorProvider.class)
    void nonPrimeHardTest(PrimeNumbersDetector primeDetector) {
        Assertions.assertTrue(primeDetector.isNotPrimeNumbers(new Integer[]{
            2, 13, 6997927, 6997937, 17858849, 6997967, 10000000, 999999999
        }));
    }

    
    static class PrimeDetectorProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new SequentialDetector()),
                Arguments.of(new ThreadedDetector(1)),
                Arguments.of(new ThreadedDetector(2)),
                Arguments.of(new ThreadedDetector(4)),
                Arguments.of(new ThreadedDetector(8)),
                Arguments.of(new ParallelDetector())
            );
        }
    }
}
