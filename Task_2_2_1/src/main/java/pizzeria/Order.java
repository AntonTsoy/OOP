package pizzeria;

import com.google.gson.annotations.Expose;

/**
 * 
 */
public enum Order {

    ORDERS_LIST ("Заказ лежит в очереди"),
    COOKING ("Пиццу готовит повар"),
    STORE_QUEUE ("Повар стоит в очереди на склад"),
    STOREHOUSE ("Пицца лежит на складе"),
    DELIVERING ("Пиццу везёт курьер"),
    COMPLETE ("Заказ окончен. Доставка выполнена");

    @Expose
    private String stateInfo;

    Order(String info) {
        this.stateInfo = info;
    }

    @Override
    public String toString() {
        return this.stateInfo;
    }
}
