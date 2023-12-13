package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;
import calculator.exceptions.ZeroDivisionError;

/**
 * Represents a division operation that divides the last two numbers in a collection.
 */
public final class Division extends Operation{

    /**
     * Applies the division operation to the provided collection of numbers.
     * Removes the last two numbers from the collection, checks for division by zero, divides them, and inserts the result back into the collection.
     *
     * @param collection The list of numbers on which the division operation is applied.
     * @throws CalculationException if there are fewer numbers in the collection than required for division.
     * @throws ZeroDivisionError if the second number is almost zero, indicating a division by zero.
     */
    @Override
    public void apply(List<Double> collection) throws CalculationException, ZeroDivisionError {
        int idOfLastNumber = collection.size() - 1;
        if (idOfLastNumber < 1) {
            throw new CalculationException("There are fewer numbers in collection than required");
        }

        Double firstNumber = collection.remove(idOfLastNumber);
        idOfLastNumber--;
        Double secondNumber = collection.remove(idOfLastNumber);

        if (almostZero(secondNumber)) {
            throw new ZeroDivisionError();
        }

        collection.add(firstNumber / secondNumber);
    }
}
