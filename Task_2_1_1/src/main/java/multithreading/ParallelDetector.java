package multithreading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс, реализующий параллельное решение.
 */
public class ParallelDetector implements PrimeNumbersDetector {

    /**
     * Функция для проверки присутствия непростых чисел в массиве.
     *
     * @param numbers is checking numbers.
     * @return true if numbers are not all prime.
     */
    @Override
    public boolean isNotPrimeNumbers(Integer[] numbers) {
        List<Integer> numList = new ArrayList<>(Arrays.asList(numbers));
        return numList.parallelStream().anyMatch(
            num -> !PrimeNumbersDetectorUtils.isPrimeNumber(num)
        );
    }

    public static void main(String[] args) {
        ParallelDetector solution1 = new ParallelDetector();
        
        Integer[] numbers = new Integer[500000];
        Integer num = 2;
        int counter = 0;
        while (counter < 500000) {
            if (PrimeNumbersDetectorUtils.isPrimeNumber(num)) {
                numbers[counter] = num;
                counter++;
            }
            num++;
        }

        long startTime = System.currentTimeMillis();

        boolean answer = solution1.isNotPrimeNumbers(numbers);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Time working: " + executionTime + " ms");
        System.out.println(answer);
    }
}
