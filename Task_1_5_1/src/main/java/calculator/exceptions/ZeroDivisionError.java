package calculator.exceptions;

/**
 * Represents an exception thrown when attempting to perform division by zero.
 * Extends the CalculationException class.
 */
public class ZeroDivisionError extends CalculationException {

    /**
     * Exception class constructor.
     */
    public ZeroDivisionError() {
        super("Error: Division by zero");
    }
}