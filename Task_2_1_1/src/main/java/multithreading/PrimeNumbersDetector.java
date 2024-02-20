package multithreading;

/**
 * Интерфейс решений по поиску непростых чисел в массиве.
 */
public interface PrimeNumbersDetector {
    // функция принимает массив Integer-ов и, если там есть НЕпростое число, вернет true
    boolean isNotPrimeNumbers(Integer[] numbers);
}
