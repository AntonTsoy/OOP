package calculator.exceptions;

/**
 * Represents an exception thrown when encountering issues with power operations.
 * Extends the CalculationException class.
 */
public class PowerException extends CalculationException {
    
    /**
     * Exception class constructor.
     */
    public PowerException() {
        super("Zero to zeroth Power or Negative base of Pow");
    }
}
