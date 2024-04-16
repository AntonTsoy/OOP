package pizzeria;

import com.google.gson.annotations.Expose;
import java.util.ArrayDeque;

/**
 * Представляет блокирующуюся очередь заказов.
 */
public class BlockingDesk {
    @Expose
    private ArrayDeque<Order> desk;
    private int limit;

    /**
     * Создает новый блокирующуюся очередь без ограничения по количеству заказов.
     */
    public BlockingDesk() {
        this.limit = Integer.MIN_VALUE;
        this.desk = new ArrayDeque<Order>();
    }

    /**
     * Создает новый блокирующуюся очередь с указанным ограничением по количеству заказов.
     *
     * @param deskLimit предельное количество заказов на столе.
     */
    public BlockingDesk(int deskLimit) {
        this.limit = deskLimit;
        this.desk = new ArrayDeque<Order>();
    }

    /**
     * Добавляет новый заказ в начало очереди.
     *
     * @param newOrder новый заказ.
     * @throws InterruptedException если поток был прерван во время ожидания.
     */
    public synchronized void addFirst(Order newOrder) throws InterruptedException {
        if (this.limit != Integer.MIN_VALUE) {
            while (this.desk.size() == this.limit) {
                wait();
            }
        }
        this.desk.addFirst(newOrder);
        notifyAll();
    }

    /**
     * Добавляет новый заказ в конец очереди.
     *
     * @param newOrder новый заказ.
     * @throws InterruptedException если поток был прерван во время ожидания.
     */
    public synchronized void push(Order newOrder) throws InterruptedException {
        if (this.limit != Integer.MIN_VALUE) {
            while (this.desk.size() == this.limit) {
                wait();
            }
        }
        this.desk.add(newOrder);
        notifyAll();
    }

    /**
     * Извлекает заказ из начала очереди.
     *
     * @return извлеченный заказ.
     * @throws InterruptedException если поток был прерван во время ожидания.
     */
    public synchronized Order pop() throws InterruptedException {
        Order takenOrder;
        while (this.desk.isEmpty()) {
            wait();
        }
        takenOrder = this.desk.poll();
        notifyAll();
        return takenOrder;
    }

    /**
     * Извлекает заказ из начала очереди, если он есть.
     *
     * @return извлеченный заказ, или null, если стол пуст.
     */
    public synchronized Order freePop() {
        if (this.desk.isEmpty()) {
            return null;
        }
        return this.desk.poll();
    }

    /**
     * Проверяет, пуста ли очередь. Эта информация может быстро стать не актуальной.
     *
     * @return true, если список пуст, в противном случае - false.
     */
    public synchronized boolean isEmpty() {
        return this.desk.isEmpty();
    }

    /**
     * Возвращает количество заказов на списке заказов. Эта информация может быстро стать не
     * актуальной.
     *
     * @return количество заказов в очерди.
     */
    public synchronized int size() {
        return this.desk.size();
    }

    @Override
    public String toString() {
        return this.desk.toString();
    }
}
