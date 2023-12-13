package calculator.operations;

import java.util.List;


public sealed abstract class Operation 
permits Addition, Difference, Division, Multiplication, Sqrt, Sin, Cos, Log, Pow {
    
    public abstract void apply(List<Double> collection);

    protected boolean almostZero(Double number) {
        return Math.abs(number) < 0.000001;
    }

    protected boolean isNegative(Double number) {
        return Double.compare(number, 0.0) < 0;
    }
}
