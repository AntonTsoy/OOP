package multithreading;

import java.lang.Thread;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс, реализующий многопоточное решение.
 */
public class ThreadedDetector implements PrimeNumbersDetector {

    private final int threadQuantity;
    private Integer[] numbers;

    /**
     * Метод инициализатор многопоточного решения.
     *
     * @param threadQuantity количество желаемых потоков.
     */
    public ThreadedDetector(int threadQuantity) {
        this.threadQuantity = threadQuantity;
    }

    /**
     * Функция создаёт новый поток.
     *
     * @param offset с какого индекса поток будет проверять массив.
     * @param len сколько элементов проверит поток.
     * @param result есть ли НЕпростое число.
     * @return новый поток.
     */
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

    /**
     * Функция для проверки присутствия непростых чисел в массиве.
     *
     * @param numbers is checking numbers.
     * @return true if numbers are not all prime.
     */
    @Override
    public boolean isNotPrimeNumbers(Integer[] numbers) {
        if (numbers.length == 0) {
            return false;
        }
        if (threadQuantity <= 0) {
            throw new RuntimeException("It's stupid to create zero and less number of threads!");
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
}
