package calculator.exceptions;

/**
 * Comment.
 */
public class ZeroDivisionError extends CalculationException {

    public ZeroDivisionError() {
        super("Error: Division by zero");
    }
}