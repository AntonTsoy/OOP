/**
 * Entry-point class.
 */
public class CheckSort {

    /**
     * Static entry method.
     *
     * @param args Cmd args.
     */
    public static void main(String[] args) {
        
        int[] heap = HeapSort.heapSort(new int[] {5, 4, 0, 3, -7, 2, 1, 0, 0, -100000});

        for (int number : heap) {
            System.out.print(number + " ");
        }
        System.out.println();

    }
}
