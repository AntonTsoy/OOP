import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapTest {
    @Test
    void testHeapConstructor() {
        int[] array = {5, 3, 8, 4, 1, 2};

        assertDoesNotThrow(() -> {
            new Heap(array);  
        });
    }

    @Test
    void testHeapCreation() {
        int[] array = {5, 3, 8, 4, 1, 2};
        
        Heap heap = new Heap(array);
        
        assertEquals(6, heap.heapArray.length);
    }

    @Test
    void testSiftDown() {
        int[] array = {7, 2, 5, 3, 8, 4};
        
        Heap heap = new Heap(array);
        
        heap.siftDown(6, 0);
        
        assertArrayEquals(new int[]{7, 5, 8, 3, 2, 4}, heap.heapArray);
    }

    @Test
    void testHeapSort() {
        int[] array = {5, 3, 8, 4, 1, 2};
        
        Heap heap = new Heap(array);
        
        int[] result = heap.heapSort();
        
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 8}, result);
    }
    
    @Test
    void testShowHeap() {

        int[] array = {5, 3, 8, 4, 1, 2};
        
        Heap heap = new Heap(array);

        assertDoesNotThrow(heap::showHeap); 
    }

}