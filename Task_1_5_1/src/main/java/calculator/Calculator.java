package calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;

import calculator.exceptions.CalculationException;
import calculator.exceptions.InvalidOperationException;
import calculator.operations.OperationFactory;
import calculator.operations.Operation;

/**
 * Class which working like a Calculator.
 */
public class Calculator {

    private static ArrayList<String> parseExpression(String expr) {
        return new ArrayList<String>(
            Arrays.stream(expr.split(" "))
                  .filter(elem -> !elem.isEmpty())
                  .collect(Collectors.toList())
            );
    }

    private static boolean isNumber(String element) {
        try {
            Double.parseDouble(element);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }   
    }

    private static Stack<Double> fillStackWithDoubles(ArrayList<String> parsedExpr) {
        Stack<Double> stack = new Stack<>();
        int lastElement = parsedExpr.size() - 1;
        while (isNumber(parsedExpr.get(lastElement))) {
            stack.push(Double.parseDouble(parsedExpr.get(lastElement)));
            parsedExpr.remove(lastElement);
            lastElement--;
        }

        return stack;
    }

    /**
     * Calculates the result of a mathematical expression in Reverse Polish Notation (RPN).
     * Supported operations include addition, subtraction, multiplication, division, logarithm, 
     * exponentiation, square root, sine, and cosine.
     * This method takes string representing the expression and returns the result of calculation.
     *
     * @param expr a string representing the mathematical expression in Reverse Polish Notation
     * @return the result of the calculation
     * @throws ZeroDivisionError if the expression attempts to divide by zero
     * @throws IncorrectLogarithm if expression attempts to take logarithm of negative number
     * @throws ZeroToZeroPower if the expression attempts to raise 0 to the power of 0
     * @throws NegativeRoot if the expression attempts to take the square root of a negative
     * @throws InvalidOperation if the expression contains an invalid operation
     */
    public static double calculate(String expr) 
    throws InvalidOperationException, CalculationException {
        ArrayList<String> exprElements = parseExpression(expr);
        Stack<Double> stackNumbers = fillStackWithDoubles(exprElements);
        fillStackWithDoubles(exprElements);

        String operationStr = null;
        Operation operationObj = null;
        int lastOperator = exprElements.size() - 1;
        while (lastOperator >= 0) {
            operationStr = exprElements.remove(lastOperator);
            operationObj = OperationFactory.getOperation(operationStr);
            operationObj.apply(stackNumbers);
            lastOperator--;
        }

        return stackNumbers.pop();
    }
}