package calculator.exceptions;

/**
 * 
 */
public class CalculationException extends ArithmeticException{

    /**
     * 
     * @param message for users about current exception.
     */
    public CalculationException(String message) {
        super(message);
    }
}
