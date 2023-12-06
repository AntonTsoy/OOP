package calculator;

public class IncorrectLogarithm extends ArithmeticException{
    
    public IncorrectLogarithm() {
        super("Invalid logarithm argument");
    }
}
