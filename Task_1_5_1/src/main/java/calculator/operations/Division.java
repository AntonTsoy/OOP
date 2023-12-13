package calculator.operations;

import calculator.exceptions.CalculationException;
import calculator.exceptions.ZeroDivisionError;
import java.util.List;

/**
 * Represents a division operation that divides the last two numbers in a collection.
 */
public final class Division extends Operation{

    /**
     * Applies the division operation to the provided collection of numbers.
     * Removes the last two numbers from the collection, checks for division by zero.
     * Divides them, and inserts the result back into the collection.
     *
     * @param collection The list of numbers on which the division operation is applied.
     * @throws CalculationException if there less numbers in collection than required for division.
     * @throws ZeroDivisionError if the second number is almost zero, indicating division by zero.
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
