package multithreading;

/**
 * Класс для разделения задач между потоками.
 */
public class TaskDelimiter {

    /**
     * Функция для получения количества проверяемых чисел.
     *
     * @param threadIdx номер текущего потока.
     * @param threadQnt сколько всего потоков.
     * @param taskQnt сколько всего чисел в массиве.
     * @return количество проверяемых чисел.
     * @throws IllegalArgumentException если номер нити больше их количества или нитей много.
     */
    public static int lenThreadPart(int threadIdx, int threadQnt, int taskQnt)
        throws IllegalArgumentException {
        checkArguments(threadIdx, threadQnt, taskQnt);
        int baseLenPart = taskQnt / threadQnt;
        return threadIdx < taskQnt % threadQnt ? baseLenPart + 1 : baseLenPart;
    }

    /**
     * Функция для получения начального индекса в массиве для проверки.
     *
     * @param threadIdx номер текущего потока.
     * @param threadQnt сколько всего потоков.
     * @param taskQnt сколько всего чисел в массиве.
     * @return количество проверяемых чисел.
     * @throws IllegalArgumentException если номер нити больше их количества или нитей много.
     */
    public static int offsetThreadPart(int threadIdx, int threadQnt, int taskQnt)
        throws IllegalArgumentException {
        checkArguments(threadIdx, threadQnt, taskQnt);
        int baseOffsetPart = taskQnt / threadQnt * threadIdx;
        int choosenThreads = taskQnt % threadQnt;
        
        if (threadIdx < choosenThreads) {
            return baseOffsetPart + threadIdx;
        }
        return baseOffsetPart + choosenThreads;
    }


    private static void checkArguments(int threadIdx, int threadQnt, int taskQnt) 
        throws IllegalArgumentException {
        if (threadIdx >= threadQnt || threadIdx < 0) {
            var message = String.format("Your threadIdx can be in [0, %d)", threadQnt);
            throw new IllegalArgumentException(message);
        } else if (threadQnt > taskQnt) {
            var message = String.format("You want to create too many threads");
            throw new IllegalArgumentException(message);
        }
    }
}
