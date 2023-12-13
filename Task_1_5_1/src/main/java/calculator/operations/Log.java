package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;
import calculator.exceptions.IncorrectLogarithmError;

/**
 * Represents a logarithm operation that calculates the natural logarithm of the last number in a collection.
 */
public final class Log extends Operation{

    /**
     * Applies the logarithm operation to the provided collection of numbers.
     * Removes the last number from the collection, checks for correctness (zero or negative), calculates the logarithm, and inserts the result back into the collection.
     *
     * @param collection The list of numbers on which the logarithm operation is applied.
     * @throws CalculationException if there are fewer numbers in the collection than required for the logarithm operation.
     * @throws IncorrectLogarithmError if the number is almost zero or negative, indicating an incorrect logarithm argument.
     */
    @Override
    public void apply(List<Double> collection) throws CalculationException, IncorrectLogarithmError {
        int idOfLastNumber = collection.size() - 1;
        if (idOfLastNumber < 0) {
            throw new CalculationException("There are fewer numbers in collection than required");
        }

        Double number = collection.remove(idOfLastNumber);

        if (almostZero(number) || isNegative(number)) {
            throw new IncorrectLogarithmError();
        }

        collection.add(Math.log(number));
    }
}
