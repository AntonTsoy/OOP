package calculator;

/**
 * Comment.
 */
public class InvalidOperation extends IllegalArgumentException {
    
    public InvalidOperation() {
        super("Trying to do invalid operation");
    }
}
