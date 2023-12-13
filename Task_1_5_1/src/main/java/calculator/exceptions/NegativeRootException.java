package calculator.exceptions;

/**
 * Represents an exception thrown when attempting to take the root of a negative number.
 * Extends the CalculationException class.
 */
public class NegativeRootException extends CalculationException {
    
    /**
     * Exception class constructor.
     */
    public NegativeRootException() {
        super("Taking the root of the negative number");
    }
}
