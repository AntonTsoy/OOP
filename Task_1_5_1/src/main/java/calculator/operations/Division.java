package calculator.operations;

import java.util.List;

import calculator.exceptions.ZeroDivisionError;


public final class Division extends Operation{

    @Override
    public void apply(List<Double> collection) throws ZeroDivisionError{
        int idOfLastNumber = collection.size() - 1;
        Double firstNumber = collection.remove(idOfLastNumber);
        idOfLastNumber--;
        Double secondNumber = collection.remove(idOfLastNumber);

        if (almostZero(secondNumber)) {
            throw new ZeroDivisionError();
        }

        collection.add(firstNumber / secondNumber);
    }
}
