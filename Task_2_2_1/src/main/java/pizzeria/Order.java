package pizzeria;

import com.google.gson.annotations.Expose;


/**
 * 
 */
public class Order {
    private final static int statesSize = 6;
    private final static String[] states = {
        "В очереди заказов",
        "Пиццу готовит повар",
        "В очереди на склад",
        "Лежит на складе",
        "Доставляется курьером",
        "Заказ выполнен"
    };
    private static int ordersCnt = 0;

    @Expose
    private int orderId;
    @Expose
    private int currStateId;

    public Order() {
        this.currStateId = 0;
        this.orderId = ordersCnt++;
    }

    public void resetState() {
        this.currStateId = 0;
    }

    public void nextState() {
        if (this.currStateId < Order.statesSize - 1) {
            this.currStateId++;
        }
    }

    public void prevState() {
        if (this.currStateId > 0) {
            this.currStateId--;
        }
    }

    public int getStateId() {
        return this.currStateId;
    }
 
    public String getCurrentState() {
        return Order.states[this.currStateId];
    }

    @Override 
    public String toString() {
        return "{#" + orderId + " " + getCurrentState() + "}";
    }
}