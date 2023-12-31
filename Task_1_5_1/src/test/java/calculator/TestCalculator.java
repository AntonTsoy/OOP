package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import calculator.exceptions.IncorrectLogarithmError;
import calculator.exceptions.InvalidOperationException;
import calculator.exceptions.NegativeRootException;
import calculator.exceptions.PowerException;
import calculator.exceptions.ZeroDivisionError;
import org.junit.jupiter.api.Test;


/**
 * Class for tests.
 */
public class TestCalculator {

    @Test
    public void testSimpleAddition() {
        String expr = "+ 2 3";
        double result = Calculator.calculate(expr);
        assertEquals(5.0, result, 0.01);
    }

    @Test
    public void testSimpleMult() {
        String expr = "* 4 9";
        double result = Calculator.calculate(expr);
        assertEquals(36.0, result, 0.01);
    }

    @Test
    public void testSimpleDivision() {
        String expr = "/ 18 2";
        double result = Calculator.calculate(expr);
        assertEquals(9.0, result, 0.01);
    }

    @Test
    public void testSimpleDif() {
        String expr = "- -11 -12";
        double result = Calculator.calculate(expr);
        assertEquals(1.0, result, 0.01);
    }

    @Test
    public void testDivisionByZero() {
        String expr = "/ 2 0";
        assertThrows(ZeroDivisionError.class, () -> {
            Calculator.calculate(expr);
        });
    }

    @Test
    public void testLogarithmOfNegativeNumber() {
        String expr = "log -2";
        assertThrows(IncorrectLogarithmError.class, () -> {
            Calculator.calculate(expr);
        });
    }

    @Test
    public void testZeroToZeroPower() {
        String expr = "pow 0 0";
        assertThrows(PowerException.class, () -> {
            Calculator.calculate(expr);
        });
    }

    @Test
    public void testNegativeRoot() {
        String expr = "sqrt -1";
        assertThrows(NegativeRootException.class, () -> {
            Calculator.calculate(expr);
        });
    }

    @Test
    public void testInvalidOperation() {
        String expr = "dub * 2 3 5";
        assertThrows(InvalidOperationException.class, () -> {
            Calculator.calculate(expr);
        });
    }
        
    @Test
    public void testLogarithm() {
        String expr = "log 10";
        double result = Calculator.calculate(expr);
        assertEquals(2.302585092994046, result, 0.01);
    }

    @Test
    public void testExponentiation() {
        String expr = "pow 2 3";
        double result = Calculator.calculate(expr);
        assertEquals(8.0, result, 0.01);
    }

    @Test
    public void testSquareRoot() {
        String expr = "sqrt 16";
        double result = Calculator.calculate(expr);
        assertEquals(4.0, result, 0.01);
    }

    @Test
    public void testSine() {
        String expr = "sin 0";
        double result = Calculator.calculate(expr);
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCosine() {
        String expr = "cos 0";
        double result = Calculator.calculate(expr);
        assertEquals(1.0, result, 0.01);
    }
    
}