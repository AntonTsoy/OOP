package pizzeria;

import com.google.gson.annotations.Expose;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 
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


    public Chef(int chefId, int chefSpeed) {
        this.id = chefId;
        this.speed = chefSpeed;
    }


    public Chef(int chefId, int chefSpeed, BlockingDesk orders, BlockingDesk storehouse) {
        this(chefId, chefSpeed);
        setQueues(orders, storehouse);
    }


    public int getId() {
        return this.id;
    }


    public int getSpeed() {
        return this.speed;
    }


    public void setQueues(BlockingDesk orders, BlockingDesk storehouse) {
        if (this.orderQueue == null && orders != null) {
            this.orderQueue = orders;
        } else {
            logger.error(this + " повар пытается поменять текущую очередь склада!");
            throw new RuntimeException();
        }

        if (this.storeQueue == null && storehouse != null) {
            this.storeQueue = storehouse;
        } else {
            logger.error(this + " повар пытается поменять текущую очередь склада!");
            throw new RuntimeException();
        }
    }


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
