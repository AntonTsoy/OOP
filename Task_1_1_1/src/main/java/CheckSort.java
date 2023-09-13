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
    }
}
