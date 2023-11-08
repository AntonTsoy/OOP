package substring;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        var found = finder.find("easy.txt", "z", true);
        assertTrue(found.size() == 1 && found.get(0) == 250);
    }


    
}