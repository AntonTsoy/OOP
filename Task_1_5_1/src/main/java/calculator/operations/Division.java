package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;
import calculator.exceptions.ZeroDivisionError;

/**
 * 
 */
public final class Division extends Operation{

    /**
     * 
     */
    @Override
    public void apply(List<Double> collection) throws CalculationException, ZeroDivisionError {
        int idOfLastNumber = collection.size() - 1;
        if (idOfLastNumber < 1) {
            throw new CalculationException("There are fewer numbers in collection than required");
        }

        Double firstNumber = collection.remove(idOfLastNumber);
        idOfLastNumber--;
        Double secondNumber = collection.remove(idOfLastNumber);

        if (almostZero(secondNumber)) {
            throw new ZeroDivisionError();
        }

        collection.add(firstNumber / secondNumber);
    }
}
