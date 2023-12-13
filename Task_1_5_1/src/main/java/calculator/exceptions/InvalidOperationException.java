package calculator.exceptions;

/**
 * Represents an exception thrown when an invalid operation is attempted.
 * Extends the IllegalArgumentException class. Message is "Trying to do an invalid operation".
 */
public class InvalidOperationException extends IllegalArgumentException {
    
    /**
     * Exception class constructor.
     */
    public InvalidOperationException() {
        super("Trying to do invalid operation");
    }
}
