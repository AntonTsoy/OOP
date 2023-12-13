package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;

/**
 * 
 */
public final class Addition extends Operation{

    /**
     * 
     * @param collection comment.
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
