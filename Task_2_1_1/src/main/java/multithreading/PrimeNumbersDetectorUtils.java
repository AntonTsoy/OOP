package multithreading;

/** 
 * Полезные функции, которые нужны и в последовательном, и в параллельном решении.
 */
public class PrimeNumbersDetectorUtils {

    /**
     * Функция для проверки поданного числа на простоту.
     *
     * @param number is checking number.
     * @return true if number is prime.
     */
    static boolean isPrimeNumber(Integer number) {
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
