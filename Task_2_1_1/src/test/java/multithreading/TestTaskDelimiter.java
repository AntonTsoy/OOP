package multithreading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Класс для тестирования Делителя задач между потоками.
 */
class TaskDelimiterTest {

    @Test
    void lenThreadCorrectLength() {
        assertEquals(4, TaskDelimiter.lenThreadPart(0, 3, 10));
        assertEquals(2, TaskDelimiter.lenThreadPart(1, 3, 7));
        assertEquals(3, TaskDelimiter.lenThreadPart(2, 3, 10));
    }

    @Test
    void offsetThreadCorrectOffset() {
        assertEquals(4, TaskDelimiter.offsetThreadPart(1, 3, 10));
        assertEquals(7, TaskDelimiter.offsetThreadPart(2, 3, 10));
        assertEquals(0, TaskDelimiter.offsetThreadPart(0, 3, 7));
    }

    @Test
    void checkArgumentsThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            TaskDelimiter.offsetThreadPart(3, 11, 10);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            TaskDelimiter.lenThreadPart(-1, 3, 10);
        });
    }
}