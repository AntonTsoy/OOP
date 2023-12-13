package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;
import calculator.exceptions.PowerException;

/**
 * Represents a power operation that raises the first number to the power of the second number.
 */
public final class Pow extends Operation{

    /**
     * Applies the power operation to the provided collection of numbers.
     * Removes the last two numbers from the collection, checks for correctness (zero base and exponent or negative base),
     * raises the base to the power of the exponent, and inserts the result back into the collection.
     *
     * @param collection The list of numbers on which the power operation is applied.
     * @throws CalculationException if there are fewer numbers in the collection than required for the power operation.
     * @throws PowerException if both the base and exponent are almost zero or the base is negative.
     */
    @Override
    public void apply(List<Double> collection) throws CalculationException, PowerException {
        int idOfLastNumber = collection.size() - 1;
        if (idOfLastNumber < 1) {
            throw new CalculationException("There are fewer numbers in collection than required");
        }

        Double firstNumber = collection.remove(idOfLastNumber);
        idOfLastNumber--;
        Double secondNumber = collection.remove(idOfLastNumber);

        if (almostZero(firstNumber) && almostZero(secondNumber) || isNegative(firstNumber)) {
            throw new PowerException();
        }

        collection.add(Math.pow(firstNumber, secondNumber));
    }
}
