/**
 * The Heap class implements an array-based heap.
 */
public class Heap {

    // An array to store heap elements.
    int[] heapArray;

    /**
     * Class constructor.
     * 
     * @param array source array to create heap.
     */
    public Heap(int[] array) {
        this.heapArray = array; 
    }

    /** 
     * Method for comparing heap elements.
     * 
     * @param firstId first arg.
     * 
     * @param secondId second arg.
     * 
     * @return true if fist element is greater than second.
     */
    private boolean isLarger(int firstId, int secondId) {
        return this.heapArray[firstId] > this.heapArray[secondId];
    }

    /**
     * Method for finding the largest child.
     *
     * @param arrayLength length of array.
     *
     * @param curNodeId current Id in array.
     *
     * @return greatest child index.
     */
    private int findNodeForSiftUp(int arrayLength, int curNodeId) {
        
        // Calculating the indexes of descendants
        int leftChildId = curNodeId * 2 + 1;
        int rightChildId = curNodeId * 2 + 2;

        int largestNodeId = curNodeId;
        
        // Comparing with left descendant
        if (leftChildId < arrayLength && isLarger(leftChildId, largestNodeId)) {
            largestNodeId = leftChildId;
        }
        
        // Comparing with right descendant
        if (rightChildId < arrayLength && isLarger(rightChildId, largestNodeId)) {
            largestNodeId = rightChildId;
        }

        return largestNodeId;
    }

    /**
     * Method for sifting an element down.
     * 
     * @param arrayLength length of array.
     * 
     * @param curNodeId current Id in array.
     */
    public void siftDown(int arrayLength, int curNodeId) {
       
        int largestNodeId = findNodeForSiftUp(arrayLength, curNodeId);

        if (largestNodeId != curNodeId) {
        
            // Swap elements
            int temp = this.heapArray[curNodeId];
            this.heapArray[curNodeId] = this.heapArray[largestNodeId];
            this.heapArray[largestNodeId] = temp;

            // Recursively lower the element even lower
            siftDown(arrayLength, largestNodeId);
        }
    }

    /**
     * Method for sorting heap.
     * 
     * @return sorted array.
     */
    public int[] heapSort() {

        int arrayLength = this.heapArray.length;

        // Sift elements
        for (int i = arrayLength / 2 - 1; i >= 0; i--) {
            siftDown(arrayLength, i);
        }

        // Pulling out the elements one by one
        for (int curLength = arrayLength - 1; curLength >= 0; curLength--) {
            int temp = this.heapArray[0];
            this.heapArray[0] = this.heapArray[curLength];
            this.heapArray[curLength] = temp;
            siftDown(curLength, 0);
        }

        return this.heapArray;
    }

    /**
     * Method for heap printing.
     */
    public void showHeap() {
        // Print all the elements 
        for (int number : this.heapArray) {
            System.out.printf(number + " ");
        }
        System.out.println();
    }
}