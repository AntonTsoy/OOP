package pizzeria;

import java.util.Date;
import java.util.Stack;

import com.google.gson.annotations.Expose;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 
 */
public class Courier implements Worker {
    
    @Expose
    private final int id;
    @Expose
    private final int speed;
    @Expose
    private final int capacity;

    private boolean isPizzeriaOpen;

    private BlockingDesk storeQueue;
    private Stack<Order> backpack;

    final static Logger logger = LogManager.getLogger(Courier.class);


    public Courier(int courierId, int courierSpeed, int packCapacity) {
        this.id = courierId;
        this.speed = courierSpeed;
        this.capacity = packCapacity;
        this.isPizzeriaOpen = true;
        wearBackpack();
    }


    public Courier(int courierId, int courierSpeed, int packCapacity, BlockingDesk storeQueue) {
        this(courierId, courierSpeed, packCapacity);
        setStore(storeQueue);
    }


    public int getId() {
        return this.id;
    }


    public int getSpeed() {
        return this.speed;
    }


    public int getPackCapacity() {
        return this.capacity;
    }


    public void wearBackpack() {
        this.backpack = new Stack<Order>();
    }


    public void setStore(BlockingDesk storeQueue) {
        if (this.storeQueue == null && storeQueue != null) {
            this.storeQueue = storeQueue;
        } else {
            logger.error(this + " курьер пытается поменять текущую очередь склада!");
            throw new RuntimeException();
        }
    }


    @Override
    public void run() {
        this.isPizzeriaOpen = true;

        if (this.backpack == null) {
            wearBackpack();
        }
        logger.info(this + " курьер пришел на работу.");
        while (this.isPizzeriaOpen || !this.storeQueue.isEmpty()) {
            Order firstNewOrder = null;
            try {
                firstNewOrder = this.storeQueue.pop();
            } catch (InterruptedException e) {
                this.isPizzeriaOpen = false;
                logger.info(this + " курьер получил сообщение о закрытии пока клал первую пиццу");
            }

            if (firstNewOrder != null) {
                firstNewOrder.nextState();
                logger.info(this + " курьер взял первый заказ: " + firstNewOrder);
                this.backpack.add(firstNewOrder);
            }

            while (this.backpack.size() < this.capacity) {
                Order newOrder = this.storeQueue.freePop();
                if (newOrder != null) {
                    logger.info(this + " курьер взял заказ: " + newOrder);
                    newOrder.nextState();
                    this.backpack.add(newOrder);
                } else {
                    break;
                }
            }

            if (!this.backpack.empty()) {
                try {
                    Thread.sleep(this.speed);
                } catch (InterruptedException e) {
                    this.isPizzeriaOpen = false;
                    logger.info(this + " курьер получил сообщение о закрытии пока довозил пиццу");
                }
                logger.info(this + " курьер доставил набор пицц: " + this.backpack);
                this.backpack.clear();
            }

            if (Thread.currentThread().isInterrupted()) {
                this.isPizzeriaOpen = false;
                logger.info(this + " курьер понял, что пиццерия закрылась, вернувшись на склад");
            }
        }
    }


    @Override
    public String toString() {
        return getInfo();
    }
}
