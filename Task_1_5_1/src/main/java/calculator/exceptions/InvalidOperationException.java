package calculator.exceptions;

/**
 * Comment.
 */
public class InvalidOperationException extends IllegalArgumentException {
    
    public InvalidOperationException() {
        super("Trying to do invalid operation");
    }
}
