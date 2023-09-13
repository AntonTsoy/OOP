/**
 * Entry-point class.
 */
public class CheckSort {

    /**
     * Static entry method.
     *
     * @param args Cmd args.
     */
    public static void main(String[] args){
        Heap heap = new Heap(new int[] {5, 4, 3, 2, 1});
        heap.heapSort();
        heap.showHeap();


        int[] array1 = {5, 3, 8, 4, 1, 2};

        Heap heap1 = new Heap(array1);
        System.out.println(heap1.heapArray.length);


        int[] array2 = {7, 2, 5, 3, 8, 4};

        Heap heap2 = new Heap(array2);

        heap2.siftDown(6, 0);

        heap2.showHeap(); //{7, 2, 5, 3, 8, 4}


        int[] array3 = {5, 3, 8, 4, 1, 2};

        Heap heap3 = new Heap(array3);

        int[] result = heap3.heapSort();

        heap3.showHeap(); //{1, 2, 3, 4, 5, 8}

    }
}
