package substring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



/**
 * Test class for string finders.
 */
public class TestSubStringSearch {

    SubStringSearch finder;

    @BeforeEach
    void setUp() {
        finder = new SubStringSearch();
    }

    @Test
    void easyTest() {
        ArrayList<Long> found = finder.find("easy.txt", "Z", true);
        assertTrue(found.size() == 1 && found.get(0) == 1);
    }

    @Test
    void go() {
        ArrayList<Long> found = finder.find("src/test/resources/go.txt", "v");
        assertEquals(found.get(0), 10);
    }

}