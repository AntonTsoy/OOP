package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;

/**
 * Represents an addition operation that adds the last two numbers in a collection.
 */
public final class Addition extends Operation{

    /**
     * Applies the addition operation to the provided collection of numbers.
     * Removes the last two numbers from the collection, adds them, and inserts the result back into the collection.
     *
     * @param collection The list of numbers on which the addition operation is applied.
     * @throws CalculationException if there are fewer numbers in the collection than required for addition.
     */
    @Override
    public void apply(List<Double> collection) throws CalculationException {
        int idOfLastNumber = collection.size() - 1;
        if (idOfLastNumber < 1) {
            throw new CalculationException("There are fewer numbers in collection than required");
        }

        Double firstNumber = collection.remove(idOfLastNumber);
        idOfLastNumber--;
        Double secondNumber = collection.remove(idOfLastNumber);
        
        collection.add(firstNumber + secondNumber);
    }
}
