package network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Класс для тестирования парсинга и распарсинга числовой коллекции.
 */
public class TestParser {

    @Test
    public void testParseStrToIntegerList() {
        String input = "1 2 3 4 5 6 7 8 9";
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> result = Parser.parseStrToIntegerList(input);
        Assertions.assertEquals(expected, result);
    }


    @Test
    public void testMakeTaskListFromIntegerList() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> expected = Arrays.asList("1 2 3 4 5 6", "7 8 9 10");
        List<String> result = Parser.makeTaskListFromIntegerList(input);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testMakeTaskListFromIntegerListWithTail() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<String> expected = Arrays.asList("1 2 3 4 5 6", "7 8 9");
        List<String> result = Parser.makeTaskListFromIntegerList(input);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testMakeTaskListFromIntegerListExactMultiple() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        List<String> expected = Arrays.asList("1 2 3 4 5 6", "7 8 9 10 11 12");
        List<String> result = Parser.makeTaskListFromIntegerList(input);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testMakeTaskListFromIntegerListSingleElement() {
        List<Integer> input = Arrays.asList(1);
        List<String> expected = Arrays.asList("1");
        List<String> result = Parser.makeTaskListFromIntegerList(input);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testMakeTaskListFromIntegerListEmptyList() {
        List<Integer> input = Arrays.asList();
        List<String> expected = new ArrayList<String>();
        expected.add("");
        List<String> result = Parser.makeTaskListFromIntegerList(input);
        Assertions.assertIterableEquals(expected, result);
    }
}
