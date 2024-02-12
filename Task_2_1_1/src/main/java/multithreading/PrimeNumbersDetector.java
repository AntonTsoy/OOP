package multithreading;

/** 
 * Интерфейс, который должны реализовывать оба решения - последовательное и параллельное.
 */
public interface PrimeNumbersDetector {
    // функция принимает массив Integer-ов и, если там есть !непростое число, вернет true
    boolean isNotPrimeNumbers(Integer[] numbers);
}
