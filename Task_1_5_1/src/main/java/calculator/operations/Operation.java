package calculator.operations;

import java.util.List;

/**
 * 
 */
public abstract class Operation {
    
    /**
     * 
     * @param collection of numbers.
     */
    public abstract void apply(List<Double> collection);

    /**
     * 
     * @param number that we want check on the equality to zero.
     * @return true if this number close equality to zero, false otherwise.
     */
    protected boolean almostZero(Double number) {
        return Math.abs(number) < 0.000001;
    }

    /**
     * 
     * @param number that we want check on the negativity.
     * @return true if this number is negative, false otherwise.
     */
    protected boolean isNegative(Double number) {
        return Double.compare(number, 0.0) < 0;
    }
}
