package calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Represents an exam book for a student with information about their semesters and performance.
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

    private static void fillStackWithDoubles(Stack<Double> stack, ArrayList<String> parsedExpr) {
        int lastElement = parsedExpr.size() - 1;
        while (isNumber(parsedExpr.get(lastElement))) {
            stack.push(Double.parseDouble(parsedExpr.get(lastElement)));
            parsedExpr.remove(lastElement);
            lastElement--;
        }
    }

    //TODO: У меня уже есть разбиение строки по пробелам и проверка работаем ли мы сейчас с числом
    // или с оператором. Основная работа будет в CALCLATE там мы будем идти по элементам парсинга
    // с конца. 
    // Думаю случаи, когда пользователь ввел пустую строку или строку только из чисел или операций 
    // мы можем не учитывать.

    // operators = "+", "-", "*", "/", "log", "pow", "sqrt", "sin", "cos"


    public static double calculate(String expr) {
        ArrayList<String> exprElements = parseExpression(expr);
        Stack<Double> numbersStack = new Stack<>();
        fillStackWithDoubles(numbersStack, exprElements);

        int lastOperator = exprElements.size() - 1;
        while (lastOperator >= 0) {
            switch (exprElements.get(lastOperator)) {
                case "+": continue;
                case "-": continue;
                case "*": continue;
                case "/": continue;
                case "log": continue;
                case "pow": continue;
                case "sqrt": continue;
                case "sin": continue;
                case "cos": continue;
                default: continue;
            };

            exprElements.remove(lastOperator);
            lastOperator--;
        }

        if (lastOperator >= 0) {

        }
        //TODO: make it better and normal
        return 0.0;
    }

}