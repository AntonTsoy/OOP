import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * Тестовый класс для класса Heap. 
 */
class SortTest {

    /**
     * Тест CheckSort.
     */
    @Test
    void checkMain() {
        CheckSort.main(new String[] {});
        assertTrue(true);
    }

    /**
     * Проверяем просеивание у одной семьи.
     */
    @Test
    void testSiftDown() {
        int[] array = {7, 2, 5, 3, 8, 4};

        int[] heapArray = HeapSort.siftDown(array, 6, 0);

        assertArrayEquals(new int[] {7, 2, 5, 3, 8, 4}, heapArray);
    }

    /**
     * Тест пирамидальной сортировки.
     */
    @Test
    void testHeapSort() {
        int[] array = {5, 3, 8, 4, 1, 2};

        int[] result = HeapSort.heapSort(array);

        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 8}, result);
    }

}
