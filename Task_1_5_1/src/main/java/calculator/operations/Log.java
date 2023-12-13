package calculator.operations;

import java.util.List;

import calculator.exceptions.IncorrectLogarithmError;


public final class Log extends Operation{

    @Override
    public void apply(List<Double> collection) {
        int idOfLastNumber = collection.size() - 1;
        Double number = collection.remove(idOfLastNumber);

        if (almostZero(number) || isNegative(number)) {
            throw new IncorrectLogarithmError();
        }

        collection.add(Math.log(number));
    }
}
