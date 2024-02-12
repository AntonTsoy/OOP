package multithreading;

import java.lang.Thread;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс, реализующий многопоточное решение.
 */
public class ThreadedDetector implements PrimeNumbersDetector {

    private final int threadQuantity;

    public ThreadedDetector(int threadQuantity) {
        this.threadQuantity = threadQuantity;
    }

    private Thread newThread(ArrayList<Integer> numbers, AtomicBoolean result) {
        
    }

    @Override
    public boolean isNotPrimeNumbers(Integer[] numbers) {

        return false;
    }
}
