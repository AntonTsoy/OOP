public class CheckSort {
    public static void main(String[] args){
        Heap heap = new Heap(new int[] {5, 4, 3, 2, 1});
        heap.heapSort();
        heap.showHeap();
    }
}
