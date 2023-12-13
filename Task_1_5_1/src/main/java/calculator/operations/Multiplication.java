package calculator.operations;

import calculator.exceptions.CalculationException;

import java.util.List;

/**
 * Represents a multiplication operation that multiplies the last two numbers in a collection.
 */
public final class Multiplication extends Operation{

    /**
     * Applies the multiplication operation to the provided collection of numbers.
     * Removes the last two numbers from the collection, multiplies them.
     * And inserts the result back into the collection.
     *
     * @param collection The list of numbers on which the multiplication operation is applied.
     * @throws CalculationException if there less numbers than required for multiplication.
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
        
        collection.add(firstNumber * secondNumber);
    }
}
