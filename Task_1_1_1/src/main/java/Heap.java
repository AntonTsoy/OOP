/**
 * 
 */
public class Heap {

    int[] heapArray;

    public Heap(int[] array){
        this.heapArray = array;
    }

    /**
     * 
     */
    public static void printHelloWorld() {
        System.out.println("Hello world!");
    }

    /**
     * 
     */
    private boolean isLarger(int firstId, int secondId){
        return  this.heapArray[firstId] > this.heapArray[secondId];
    }

    /**
     * 
     */
    private int findNodeForSiftUp(int arrayLength, int curNodeId){
        int leftChildId = curNodeId * 2 + 1;
        int rightChildId = curNodeId * 2 + 2;

        int largestNodeId = curNodeId;
            
        if(leftChildId < arrayLength && isLarger(leftChildId, largestNodeId)){
            largestNodeId = leftChildId;
        }
        if(rightChildId < arrayLength && isLarger(rightChildId, largestNodeId)){
            largestNodeId = rightChildId;
        }

        return largestNodeId;
    }

    /**
     * 
     */
    private void siftDown(int arrayLength, int curNodeId){
        int largestNodeId = findNodeForSiftUp(arrayLength, curNodeId);

        if(largestNodeId != curNodeId){
            int temp = this.heapArray[curNodeId];
            this.heapArray[curNodeId] = this.heapArray[largestNodeId];
            this.heapArray[largestNodeId] = temp;

            siftDown(arrayLength, largestNodeId);
        }
    }

    /**
     * 
     */
    public int[] heapSort(){
        arrayLength = this.heapArray.length;

        for (int i = arrayLength / 2 - 1; i >= 0; i--){
            siftDown(arrayLength, i);
        }
  
        for (int curLength = arrayLength - 1; curLength >= 0; curLength--){
            int temp = this.heapArray[0];
            this.heapArray[0] = this.heapArray[i];
            this.heapArray[i] = temp;

            siftDown(arrayLength, i);
        }

        return this.heapArray;
    }

    public void showHeap(){
        for(int i = 0; i < this.heapArray.length; i++){
            System.out.print(this.heapArray[i]+" ");
        }
        System.out.println();
    }
}