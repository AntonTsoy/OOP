package calculator.operations;

import java.util.List;

import calculator.exceptions.NegativeRootException;


public final class Sqrt extends Operation{

    @Override
    public void apply(List<Double> collection) {
        int idOfLastNumber = collection.size() - 1;
        Double number = collection.remove(idOfLastNumber);
        if (isNegative(number)) {
            throw new NegativeRootException();
        }
        
        collection.add(Math.sqrt(number));
    }
}
