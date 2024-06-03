package network;


import java.util.List;

/**
 * Класс, реализующий параллельное решение.
 */
public class PrimeNumberDetector {

    /**
     * Функция для проверки присутствия непростых чисел в массиве.
     *
     * @param numbers is checking numbers.
     * @return true if numbers are not all prime.
     */
    public static boolean isNotPrimeNumbers(List<Integer> numList) {
        return !numList.parallelStream().noneMatch(
            num -> !isPrimeNumber(num)
        );
    }

    public static boolean isPrimeNumber(int number) {
        if (number < 2) {
            return false;
        } else {
            for (int divider = 2; divider * divider <= number; divider++) {
                if (number % divider == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}