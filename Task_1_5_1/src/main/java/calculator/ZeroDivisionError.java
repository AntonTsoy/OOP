package calculator;


public class ZeroDivisionError extends ArithmeticException {

    public ZeroDivisionError(String message) {
        super(message);
    }
}