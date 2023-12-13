package calculator.operations;

import calculator.exceptions.CalculationException;

import java.util.List;

/**
 * Represents an addition operation that adds the last two numbers in a collection.
 */
public final class Addition extends Operation {

    /**
     * Applies the addition operation to the provided collection of numbers.
     * Removes last two numbers from collection, adds them, inserts result back into collection.
     *
     * @param collection The list of numbers on which the addition operation is applied.
     * @throws CalculationException if there less numbers in collection than required for plus.
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
