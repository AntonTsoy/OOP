package calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;

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

    private static boolean almostZero(Double number) {
        return Math.abs(number) < 0.00001;
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
    public static double calculate(String expr) {
        ArrayList<String> exprElements = parseExpression(expr);
        Stack<Double> stackNumbers = fillStackWithDoubles(exprElements);
        fillStackWithDoubles(exprElements);

        int lastOperator = exprElements.size() - 1;
        Double first = 0.0;
        Double second = 0.0;
        while (lastOperator >= 0) {
            switch (exprElements.get(lastOperator)) {
                case "+":
                    stackNumbers.push(stackNumbers.pop() + stackNumbers.pop());
                    break;
                case "-":
                    stackNumbers.push(stackNumbers.pop() - stackNumbers.pop());
                    break;
                case "*":
                    stackNumbers.push(stackNumbers.pop() * stackNumbers.pop());
                    break;
                case "/":
                    first = stackNumbers.pop();
                    second = stackNumbers.pop();
                    if (almostZero(second)) {
                        throw new ZeroDivisionError("Division by zero");
                    }
                    stackNumbers.push(first / second);
                    break;
                case "log":
                    first = stackNumbers.pop();
                    if (almostZero(first) || first < -0.000001) {
                        throw new IncorrectLogarithm();
                    }
                    stackNumbers.push(Math.log(first));
                    break;
                case "pow":
                    first = stackNumbers.pop();
                    second = stackNumbers.pop();
                    if (almostZero(first) && almostZero(second)) {
                        throw new ZeroToZeroPower();
                    }
                    stackNumbers.push(Math.pow(first, second));
                    break;
                case "sqrt":
                    first = stackNumbers.pop();
                    if (first < -0.000001) {
                        throw new NegativeRoot();
                    }
                    stackNumbers.push(Math.sqrt(first));
                    break;
                case "sin":
                    first = stackNumbers.pop();
                    stackNumbers.push(Math.sin(first));
                    break;
                case "cos":
                    first = stackNumbers.pop();
                    stackNumbers.push(Math.cos(first));
                    break;
                default:
                    throw new InvalidOperation();
            }

            exprElements.remove(lastOperator);
            lastOperator--;
        }

        return stackNumbers.pop();
    }
}