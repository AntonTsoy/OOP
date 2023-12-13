package calculator.operations;

import calculator.exceptions.InvalidOperationException;

/**
 * Factory class for creating instances of different mathematical operations.
 */
public class OperationFactory {
    
    /**
     * Retrieves the corresponding Operation instance based on the provided operation name.
     *
     * @param operationName string that contains name of Operation.
     * @return corresponding Operation class for this name.
     * @throws InvalidOperationException if class don't process this Operation.
     */
    public static Operation getOperation(String operationName) throws InvalidOperationException {
    
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
