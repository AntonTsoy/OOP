/**
 * The Heap class implements an array-based heap.
 */
public class HeapSort {

    /** 
     * Method for comparing heap elements.
     *
     * @param array is receiving array, which has two args.
     *
     * @param firstId first arg.
     *
     * @param secondId second arg.
     *
     * @return true if fist element is greater than second.
     */
    private static boolean isLarger(int[] array, int firstId, int secondId) {
        return array[firstId] > array[secondId];
    }

    /**
     * Method for finding the largest child.
     *
     * @param array of numbers.
     *
     * @param arrayLength length of array.
     *
     * @param curNodeId current Id in array.
     *
     * @return greatest child index.
     */
    private static int findNodeForSiftUp(int[] array, int arrayLength, int curNodeId) {
        
        // Calculating the indexes of descendants
        int leftChildId = curNodeId * 2 + 1;
        int rightChildId = curNodeId * 2 + 2;

        int largestNodeId = curNodeId;
        
        // Comparing with left descendant
        if (leftChildId < arrayLength && isLarger(array, leftChildId, largestNodeId)) {
            largestNodeId = leftChildId;
        }
        
        // Comparing with right descendant
        if (rightChildId < arrayLength && isLarger(array, rightChildId, largestNodeId)) {
            largestNodeId = rightChildId;
        }

        return largestNodeId;
    }

    /**
     * Method for sifting an element down.
     *
     * @param array sequence of numbers, which we balance to heap.
     *
     * @param arrayLength length of array.
     *
     * @param curNodeId current Id in array.
     *
     * @return sifted array 
     */
    public static int[] siftDown(int[] array, int arrayLength, int curNodeId) {

        int largestNodeId;

        do {
            largestNodeId = findNodeForSiftUp(array, arrayLength, curNodeId);

            if (largestNodeId != curNodeId) {
                // Swap elements
                int temp = array[curNodeId];
                array[curNodeId] = array[largestNodeId];
                array[largestNodeId] = temp;
            }

        } while (largestNodeId != curNodeId);

        return array;
    }

    /**
     * Method for sorting heap.
     *
     * @param heapArray heap in array presentation.
     *
     * @return sorted array.
     */
    public static int[] heapSort(int[] heapArray) {

        int arrayLength = heapArray.length;

        // Sift elements
        for (int i = arrayLength / 2 - 1; i >= 0; i--) {
            heapArray = siftDown(heapArray, arrayLength, i);
        }

        // Pulling out the elements one by one
        for (int curLength = arrayLength - 1; curLength >= 0; curLength--) {
            int temp = heapArray[0];
            heapArray[0] = heapArray[curLength];
            heapArray[curLength] = temp;
            heapArray = siftDown(heapArray, curLength, 0);
        }

        return heapArray;
    }
}