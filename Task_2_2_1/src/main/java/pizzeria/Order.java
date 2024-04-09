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

    @Expose
    private int currStateId;

    public Order() {
        this.currStateId = 0;
    }

    public void nextState() {
        if (this.currStateId < statesSize - 1) {
            this.currStateId++;
        }
    }

    public void prevState() {
        if (this.currStateId > 0) {
            this.currStateId--;
        }
    }
}
