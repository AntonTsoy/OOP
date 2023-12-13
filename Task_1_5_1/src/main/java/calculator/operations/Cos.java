package calculator.operations;

import calculator.exceptions.CalculationException;
import java.util.List;

/**
 * Represents a cosine operation that calculates the cosine of the last number in a collection.
 */
public final class Cos extends Operation{
    
    /**
     * Applies the cosine operation to the provided collection of numbers.
     * Removes last number from collection, calculates cosine, inserts result back in collection.
     *
     * @param collection The list of numbers on which the cosine operation is applied.
     * @throws CalculationException if there less numbers than required for cosine operation.
     */
    @Override
    public void apply(List<Double> collection) throws CalculationException {
        int idOfLastNumber = collection.size() - 1;
        if (idOfLastNumber < 0) {
            throw new CalculationException("There are fewer numbers in collection than required");
        }

        Double number = collection.remove(idOfLastNumber);
        
        collection.add(Math.cos(number));
    }
}
