package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;
import calculator.exceptions.IncorrectLogarithmError;

/**
 * 
 */
public final class Log extends Operation{

    /**
     * 
     */
    @Override
    public void apply(List<Double> collection) throws CalculationException, IncorrectLogarithmError {
        int idOfLastNumber = collection.size() - 1;
        if (idOfLastNumber < 0) {
            throw new CalculationException("There are fewer numbers in collection than required");
        }

        Double number = collection.remove(idOfLastNumber);

        if (almostZero(number) || isNegative(number)) {
            throw new IncorrectLogarithmError();
        }

        collection.add(Math.log(number));
    }
}
