package calculator;

import calculator.exceptions.CalculationException;
import calculator.exceptions.InvalidOperationException;
import calculator.operations.Operation;
import calculator.operations.OperationFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;


/**
 * Class that works like a Calculator.
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
     * Calculates the result of a mathematical expression in Reverse Polish Notation.
     * Supported operations include addition, subtraction, multiplication, division, logarithm, 
     * exponentiation, square root, sine, and cosine.
     * This method takes string representing the expression and returns the result of calculation.
     *
     * @param expr a string representing the mathematical expression in Reverse Polish Notation.
     * @return the result of the calculation.
     * @throws InvalidOperationException if the expression contains incorrect operation.
     * @throws CalculationException if the expression attempts to do bad things for calculator.
     */
    public static double calculate(String expr) 
        throws InvalidOperationException, CalculationException {
        ArrayList<String> exprElements = parseExpression(expr);
        Stack<Double> stackNumbers = fillStackWithDoubles(exprElements);
        fillStackWithDoubles(exprElements);

        String operationStr = null;
        Operation operationObj = null;
        int lastOperatorIdx = exprElements.size() - 1;
        while (lastOperatorIdx >= 0) {
            operationStr = exprElements.remove(lastOperatorIdx);
            operationObj = OperationFactory.getOperation(operationStr);
            operationObj.apply(stackNumbers);
            lastOperatorIdx--;
        }

        return stackNumbers.pop();
    }
}