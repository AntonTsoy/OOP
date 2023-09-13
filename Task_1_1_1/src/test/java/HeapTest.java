import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;


/**
 * Тестовый класс для класса Heap. 
 */
class HeapTest {

    /**
     * Тест CheckSort.
     */
    @Test
    void checkMain() {
        CheckSort.main(new String[] {});
        assertTrue(true);
    }

    /**
     * Проверка конструктора класса.
     */
    @Test
    void testHeapConstructor() {
        int[] array = {5, 3, 8, 4, 1, 2};

        assertDoesNotThrow(() -> new Heap(array));
    }

    /**
     * Тест на правильность создания кучи.
     */
    @Test
    void testHeapCreation() {
        int[] array = {5, 3, 8, 4, 1, 2};

        Heap heap = new Heap(array);

        assertEquals(6, heap.heapArray.length);
    }

    /**
     * Проверяем просеивание у одной семьи.
     */
    @Test
    void testSiftDown() {
        int[] array = {7, 2, 5, 3, 8, 4};

        Heap heap = new Heap(array);

        heap.siftDown(6, 0);

        assertArrayEquals(new int[] {7, 2, 5, 3, 8, 4}, heap.heapArray);
    }

    /**
     * Тест пирамидальной сортировки
     */
    @Test
    void testHeapSort() {
        int[] array = {5, 3, 8, 4, 1, 2};

        Heap heap = new Heap(array);

        int[] result = heap.heapSort();

        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 8}, result);
    }

    /**
     * Проверяем вывод кучи.
     */
    @Test
    void testShowHeap() {
        int[] array = {5, 3, 8, 4, 1, 2};

        Heap heap = new Heap(array);

        assertDoesNotThrow(heap::showHeap);
    }
}
