package pizzeria;

import com.google.gson.annotations.Expose;

/**
 * 
 */
public enum Order {

    ORDERS_LIST ("{Заказ лежит в очереди}"),
    COOKING ("{Пиццу готовит повар}"),
    STORE_QUEUE ("{Повар стоит в очереди на склад}"),
    STOREHOUSE ("{Пицца лежит на складе}"),
    DELIVERING ("{Пиццу везёт курьер}"),
    COMPLETE ("{Заказ окончен. Доставка выполнена}");

    @Expose
    private int orderId;
    @Expose
    private String stateInfo;

    Order(String info) {
        this.stateInfo = info;
    }

    public void setId(int newId) {
        this.orderId = newId;
    }

    public int getId() {
        return this.orderId;
    }

    public void updateId() {
        this.orderId++;
    }

    @Override
    public String toString() {
        return "#" + getId() + " " + this.stateInfo;
    }
}
