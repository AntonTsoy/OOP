package calculator.exceptions;

/**
 * Represents an exception thrown when an invalid logarithm argument is encountered.
 * Extends the CalculationException class. The error message is set to "Invalid logarithm argument".
 */
public class IncorrectLogarithmError extends CalculationException {
    
    /**
     * Exception class constructor.
     */
    public IncorrectLogarithmError() {
        super("Invalid logarithm argument");
    }
}
