package multithreading;


public class TaskDelimiter {

    public static int lenThreadPart(int threadIdx, int threadQnt, int taskQnt)
    throws IllegalArgumentException {
        checkArguments(threadIdx, threadQnt, taskQnt);
        int baseLenPart = taskQnt / threadQnt ;
        return threadIdx < taskQnt % threadQnt ? baseLenPart + 1 : baseLenPart;
    }

    
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
