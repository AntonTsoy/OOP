package calculator.exceptions;

/**
 * Common exception class for different types mistakes in calculator.
 */
public class CalculationException extends ArithmeticException {

    /**
     * Exception class constructor.
     *
     * @param message for users about current exception.
     */
    public CalculationException(String message) {
        super(message);
    }
}
