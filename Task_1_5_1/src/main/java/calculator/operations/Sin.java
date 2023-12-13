package calculator.operations;

import java.util.List;

/**
 * 
 */
public final class Sin extends Operation{

    /**
     * 
     */
    @Override
    public void apply(List<Double> collection) {
        int idOfLastNumber = collection.size() - 1;
        Double number = collection.remove(idOfLastNumber);
        
        collection.add(Math.sin(number));
    }
}
