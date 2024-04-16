package pizzeria;

import com.google.gson.annotations.Expose;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Представляет повора в пиццерии.
 */
public class Chef implements Worker {
    
    @Expose
    private final int id;
    @Expose
    private final int speed;

    private BlockingDesk orderQueue;
    private BlockingDesk storeQueue;
    private Order currOrder = null;

    final static Logger logger = LogManager.getLogger(Chef.class);

    /**
     * Создает нового повора с указанным идентификатором и скоростью.
     *
     * @param chefId    идентификатор повора.
     * @param chefSpeed скорость приготовления пиццы.
     */
    public Chef(int chefId, int chefSpeed) {
        this.id = chefId;
        this.speed = chefSpeed;
    }

    /**
     * Создает нового повора с указанным идентификатором, скоростью, очередью заказов и складом.
     *
     * @param chefId       идентификатор повора.
     * @param chefSpeed    скорость приготовления пиццы.
     * @param orders       очередь заказов.
     * @param storehouse   склад для готовых заказов.
     */
    public Chef(int chefId, int chefSpeed, BlockingDesk orders, BlockingDesk storehouse) {
        this(chefId, chefSpeed);
        setQueues(orders, storehouse);
    }

    /**
     * Возвращает идентификатор повора.
     *
     * @return идентификатор повора.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Возвращает скорость приготовления пиццы.
     *
     * @return скорость приготовления пиццы.
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Устанавливает очереди для заказов и склада.
     *
     * @param orders     очередь заказов.
     * @param storehouse склад для готовых заказов.
     */
    public void setQueues(BlockingDesk orders, BlockingDesk storehouse) {
        if (this.orderQueue == null && orders != null) {
            this.orderQueue = orders;
        } else {
            logger.error(this + " повар пытается поменять текущую очередь заказов!");
            throw new RuntimeException();
        }

        if (this.storeQueue == null && storehouse != null) {
            this.storeQueue = storehouse;
        } else {
            logger.error(this + " повар пытается поменять текущую очередь склада!");
            throw new RuntimeException();
        }
    }

    /**
     * Запускает работу повора.
     */
    @Override
    public void run() {
        logger.info(this + " повар пришел на работу.");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                currOrder = orderQueue.pop();
            } catch (InterruptedException e) {
                logger.info(this + " повар получил сообщение о закрытии, пока брал заказ.");
                break;
            }

            logger.info(this + " повар взял заказ: " + currOrder);
            this.currOrder.nextState();
            logger.info(this + " повар готовит пиццу.");
            try {
                Thread.sleep(this.speed);
            } catch (InterruptedException e) {
                logger.info(this + " повар получил сообщение о закрытии, пока готовил пиццу.");
                break;
            }

            this.currOrder.nextState();
            logger.info(this + " повар c заказом стоит в очереди на склад.");
            try {
                storeQueue.push(currOrder);
            } catch (InterruptedException e) {
                logger.info(this + " повар получил сообщение о закрытии, пока стоял у склада.");
                break;
            }
            this.currOrder.nextState();
            logger.info(this + " повар доставил пиццу на склад: " + this.currOrder);
        }
        logger.info(this + " повар ушел с работы.");

        if (this.currOrder != null && this.currOrder.getStateId() < 3) {
            try {
                currOrder.resetState();
                orderQueue.addFirst(currOrder);
            } catch (InterruptedException e) {
                logger.error("Нить была прервана второй раз подряд!!!");
            }
        }
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
