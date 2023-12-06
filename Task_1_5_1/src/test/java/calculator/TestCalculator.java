package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


/**
 * Class for tests.
 */
public class TestCalculator {

    @BeforeEach
    void setUp() {
        
    }

    /*@Test
    void splitingString() {
        var expected = new ArrayList<String>();
        expected.add("log");
        expected.add("sin");
        expected.add(".");
        expected.add("niga");
        expected.add("123");
        expected.add("r_l,");
        assertEquals(expected, Calculator.parseExpression("log sin . niga 123 r_l,"));
    }

    @Test 
    void testIsNumber() {
        assertFalse(Calculator.isNumber("log"));
        assertTrue(Calculator.isNumber("90.12"));
        assertTrue(Calculator.isNumber("-232"));
    }*/
}