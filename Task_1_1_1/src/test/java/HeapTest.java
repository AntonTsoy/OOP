import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для класса Heap.
 */
class HeapTest {

    /**
     * Тест конструктора Heap.
     * Проверяет, создается ли объект без ошибок.
     */
    @Test
    void testHeapConstructor() {
        int[] array = {5, 3, 8, 4, 1, 2};

        assertDoesNotThrow(() -> new Heap(array));
    }

    /**
     * Тест создания Heap.
     * Проверяет, что созданный объект имеет ожидаемую длину.
     */
    @Test
    void testHeapCreation() {
        int[] array = {5, 3, 8, 4, 1, 2};

        Heap heap = new Heap(array);

        assertEquals(6, heap.heapArray.length);
    }

    /**
     * Тест метода siftDown.
     * Проверяет, что элементы смещаются вниз в соответствии с правилами кучи.
     */
    @Test
    void testSiftDown() {
        int[] array = {7, 2, 5, 3, 8, 4};

        Heap heap = new Heap(array);

        heap.siftDown(6, 0);

        assertArrayEquals(new int[]{7, 5, 8, 3, 2, 4}, heap.heapArray);
    }

    /**
     * Тест метода heapSort.
     * Проверяет, что массив сортируется в правильном порядке.
     */
    @Test
    void testHeapSort() {
        int[] array = {5, 3, 8, 4, 1, 2};

        Heap heap = new Heap(array);

        int[] result = heap.heapSort();

        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 8}, result);
    }

    /**
     * Тест метода showHeap.
     * Проверяет, что метод выполняется без ошибок.
     */
    @Test
    void testShowHeap() {
        int[] array = {5, 3, 8, 4, 1, 2};

        Heap heap = new Heap(array);

        assertDoesNotThrow(heap::showHeap);
    }
}
