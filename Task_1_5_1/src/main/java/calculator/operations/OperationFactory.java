package calculator.operations;

import calculator.exceptions.InvalidOperationException;



public class OperationFactory {
    
    public static Operation getOperation(String operationName) throws InvalidOperationException{
    
        switch (operationName) {
            case "+":
                return new Addition();
            case "-":
                return new Difference();
            case "*":
                return new Multiplication();
            case "/":
                return new Division();
            case "log":
                return new Log();
            case "pow":
                return new Pow();
            case "sqrt":
                return new Sqrt();
            case "sin":
                return new Sin();
            case "cos":
                return new Cos();
            default:
                throw new InvalidOperationException();
        }
    }
}
