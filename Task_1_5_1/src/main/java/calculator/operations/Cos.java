package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;

/**
 * Represents a cosine operation that calculates the cosine of the last number in a collection.
 */
public final class Cos extends Operation{
    
    /**
     * Applies the cosine operation to the provided collection of numbers.
     * Removes the last number from the collection, calculates the cosine, and inserts the result back into the collection.
     *
     * @param collection The list of numbers on which the cosine operation is applied.
     * @throws CalculationException if there are fewer numbers in the collection than required for the cosine operation.
     */
    @Override
    public void apply(List<Double> collection) throws CalculationException{
        int idOfLastNumber = collection.size() - 1;
        if (idOfLastNumber < 0) {
            throw new CalculationException("There are fewer numbers in collection than required");
        }

        Double number = collection.remove(idOfLastNumber);
        
        collection.add(Math.cos(number));
    }
}
