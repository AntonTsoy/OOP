package calculator.operations;

import java.util.List;

import calculator.exceptions.CalculationException;

/**
 * 
 */
public final class Cos extends Operation{
    
    /**
     * 
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
