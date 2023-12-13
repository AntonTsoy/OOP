package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;
import calculator.exceptions.PowerException;

/**
 * 
 */
public final class Pow extends Operation{

    /**
     * 
     */
    @Override
    public void apply(List<Double> collection) throws CalculationException, PowerException {
        int idOfLastNumber = collection.size() - 1;
        if (idOfLastNumber < 1) {
            throw new CalculationException("There are fewer numbers in collection than required");
        }

        Double firstNumber = collection.remove(idOfLastNumber);
        idOfLastNumber--;
        Double secondNumber = collection.remove(idOfLastNumber);

        if (almostZero(firstNumber) && almostZero(secondNumber) || isNegative(firstNumber)) {
            throw new PowerException();
        }

        collection.add(Math.pow(firstNumber, secondNumber));
    }
}
