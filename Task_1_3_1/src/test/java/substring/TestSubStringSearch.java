package substring;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals("[1]", found.toString());
    }

    @Test
    void go() {
        ArrayList<Long> found = finder.find("src/test/resources/go.txt", "bo");
        assertEquals("[14, 39]", found.toString());
    }

}