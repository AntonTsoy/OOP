package multithreading;

/**
 * Класс реализующий последовательное решение.
 * Просто идём по всему массиву и проверяем встретившееся число.
 */
public class SequentialDetector implements PrimeNumbersDetector {

    /**
     * Функция для проверки присутствия непростых чисел в массиве.
     *
     * @param numbers is checking numbers.
     * @return true if numbers are not all prime.
     */
    @Override
    public boolean isNotPrimeNumbers(Integer[] numbers) {

        for (Integer number : numbers) {
            if (!PrimeNumbersDetectorUtils.isPrimeNumber(number)) {
                return true;
            }
        }

        return false;
    }
}