package calculator;

public class InvalidOperation extends IllegalArgumentException{
    
    public InvalidOperation() {
        super("Trying to do invalid operation");
    }
}
