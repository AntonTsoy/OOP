package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;
import calculator.exceptions.NegativeRootException;

/**
 * Represents a square root operation that calculates the square root of the last number in a collection.
 */
public final class Sqrt extends Operation{
    
    /**
     * Applies the square root operation to the provided collection of numbers.
     * Removes the last number from the collection, checks for negativity, calculates the square root, and inserts the result back into the collection.
     *
     * @param collection The list of numbers on which the square root operation is applied.
     * @throws CalculationException if there are fewer numbers in the collection than required for the square root operation.
     * @throws NegativeRootException if the number is negative, indicating an attempt to calculate the square root of a negative number.
     */
    @Override
    public void apply(List<Double> collection) throws CalculationException, NegativeRootException {
        int idOfLastNumber = collection.size() - 1;
        if (idOfLastNumber < 0) {
            throw new CalculationException("There are fewer numbers in collection than required");
        }

        Double number = collection.remove(idOfLastNumber);
        if (isNegative(number)) {
            throw new NegativeRootException();
        }
        
        collection.add(Math.sqrt(number));
    }
}
