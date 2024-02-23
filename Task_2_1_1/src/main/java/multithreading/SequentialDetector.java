package multithreading;

import java.util.Arrays;
import java.util.List;

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
        List<Integer> numList = Arrays.asList(numbers);
        return !numList.stream().noneMatch(num -> !PrimeNumbersDetectorUtils.isPrimeNumber(num));
    }
}