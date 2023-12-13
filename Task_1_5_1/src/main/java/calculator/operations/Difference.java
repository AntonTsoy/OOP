package calculator.operations;

import java.util.List;

/**
 * 
 */
public final class Difference extends Operation{

    /**
     * 
     */
    @Override
    public void apply(List<Double> collection) {
        int idOfLastNumber = collection.size() - 1;
        Double firstNumber = collection.remove(idOfLastNumber);
        idOfLastNumber--;
        Double secondNumber = collection.remove(idOfLastNumber);
        
        collection.add(firstNumber - secondNumber);
    }
}
