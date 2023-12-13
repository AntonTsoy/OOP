package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;
import calculator.exceptions.NegativeRootException;

/**
 * 
 */
public final class Sqrt extends Operation{
    
    /**
     * 
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
