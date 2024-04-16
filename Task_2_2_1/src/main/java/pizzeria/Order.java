package pizzeria;

import com.google.gson.annotations.Expose;

/**
 * Представляет заказ в пиццерии.
 */
public class Order {
    private static final int statesSize = 6;
    private static final String[] states = {
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

    /**
     * Создает новый заказ с начальным состоянием "В очереди заказов".
     */
    public Order() {
        this.currStateId = 0;
        this.orderId = ordersCnt++;
    }

    /**
     * Сбрасывает состояние заказа на начальное.
     */
    public void resetState() {
        this.currStateId = 0;
    }

    /**
     * Переводит заказ в следующее состояние.
     */
    public void nextState() {
        if (this.currStateId < Order.statesSize - 1) {
            this.currStateId++;
        }
    }

    /**
     * Переводит заказ в предыдущее состояние.
     */
    public void prevState() {
        if (this.currStateId > 0) {
            this.currStateId--;
        }
    }

    /**
     * Возвращает идентификатор текущего состояния заказа.
     *
     * @return идентификатор состояния заказа.
     */
    public int getStateId() {
        return this.currStateId;
    }

    /**
     * Возвращает текущее состояние заказа.
     *
     * @return текущее состояние заказа.
     */
    public String getCurrentState() {
        return Order.states[this.currStateId];
    }

    @Override 
    public String toString() {
        return "{#" + orderId + " " + getCurrentState() + "}";
    }
}
