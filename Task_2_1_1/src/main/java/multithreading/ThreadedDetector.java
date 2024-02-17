package multithreading;

import java.lang.Thread;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс, реализующий многопоточное решение.
 */
public class ThreadedDetector implements PrimeNumbersDetector {

    private final int threadQuantity;
    private Integer[] numbers;

    public ThreadedDetector(int threadQuantity) {
        this.threadQuantity = threadQuantity;
    }

    private Thread createThread(int offset, int len, AtomicBoolean result) {
        var newThread = new Thread(
            () -> {
                for (int idx = offset; idx < offset + len; idx++) {
                    if (!PrimeNumbersDetectorUtils.isPrimeNumber(this.numbers[idx])) {
                        result.set(true);
                        break;
                    }
                }
            });
        
        newThread.setUncaughtExceptionHandler(
            (thread, exception) -> {
                synchronized (System.err) {
                    System.err.println("Thread with offset - " + offset + ":");
                    exception.printStackTrace(System.err);
                    // попробуй здесь бросить исключение
                    // https://stackoverflow-com.translate.goog/questions/6546193/how-to-catch-an-exception-from-a-thread?_x_tr_sl=en&_x_tr_tl=ru&_x_tr_hl=ru&_x_tr_pto=sc
                }
            }
        );
        return newThread;
    }

    @Override
    public boolean isNotPrimeNumbers(Integer[] numbers) {
        if (threadQuantity <= 0) {
            throw new RuntimeException("It's stupid to create zero and less number of threads!");
        }

        if (numbers.length == 0) {
            return true;
        }

        this.numbers = numbers;
        AtomicBoolean result = new AtomicBoolean(false);
        final Thread[] threads = new Thread[threadQuantity];
        for (int threadIdx = 0; threadIdx < threadQuantity; threadIdx++) {
            int offset = TaskDelimiter.offsetThreadPart(threadIdx, threadQuantity, numbers.length);
            int len = TaskDelimiter.lenThreadPart(threadIdx, threadQuantity, numbers.length);
            threads[threadIdx] = createThread(offset, len, result);

            threads[threadIdx].start();
        }

        try {
            // тут главный поток ждёт, но можно его тоже загрузить
            for (int threadIdx = 0; threadIdx < threadQuantity; threadIdx++) {
                threads[threadIdx].join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("It is impossible situation!");
        }

        return result.get();
    }

    public static void main(String[] args) {
        ThreadedDetector solution1 = new ThreadedDetector(5);
        Integer[] numbers = new Integer[200000];
        Integer num = 2;
        int counter = 0;
        while (counter < 200000) {
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
