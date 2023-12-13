package calculator.operations;

import calculator.exceptions.CalculationException;
import calculator.exceptions.IncorrectLogarithmError;
import java.util.List;

/**
 * Represents logarithm operation that calculates natural logarithm of last number in collection.
 */
public final class Log extends Operation {

    /**
     * Applies the logarithm operation to the provided collection of numbers.
     * Removes the last number from the collection, checks for correctness (zero or negative).
     * Calculates the logarithm, and inserts the result back into the collection.
     *
     * @param collection The list of numbers on which the logarithm operation is applied.
     * @throws CalculationException if there less numbers than required for logarithm operation.
     * @throws IncorrectLogarithmError if number is almost zero or negative.
     */
    @Override
    public void apply(List<Double> collection) 
        throws CalculationException, IncorrectLogarithmError {
        int idOfLastNumber = collection.size() - 1;
        if (idOfLastNumber < 0) {
            throw new CalculationException(
                "There are fewer numbers in collection than required"
            );
        }

        Double number = collection.remove(idOfLastNumber);

        if (almostZero(number) || isNegative(number)) {
            throw new IncorrectLogarithmError();
        }

        collection.add(Math.log(number));
    }
}
