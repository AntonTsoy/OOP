package calculator.operations;

import java.util.List;

/**
 * Abstract class representing a mathematical operation.
 */
public abstract class Operation {
    
    /**
     * Applies the operation to the provided collection of numbers.
     *
     * @param collection The list of numbers on which the operation is applied.
     */
    public abstract void apply(List<Double> collection);

    /**
     * Checks if a number is close to zero.
     *
     * @param number The number to check for proximity to zero.
     * @return true if the number is close to zero, false otherwise.
     */
    protected boolean almostZero(Double number) {
        return Math.abs(number) < 0.000001;
    }

    /**
     * Checks if a number is negative.
     *
     * @param number The number to check for negativity.
     * @return true if the number is negative, false otherwise.
     */
    protected boolean isNegative(Double number) {
        return Double.compare(number, 0.0) < 0;
    }
}
