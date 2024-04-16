package pizzeria;

import com.google.gson.annotations.Expose;
import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Класс рабочего курьера, который может быть задачей для потока.
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


    /**
     * Базовая инициализация без очереди склада.
     *
     * @param courierId номер курьера.
     * @param courierSpeed сколько милисекунд работает курьер.
     * @param packCapacity сколько пицц можно переносить в рюкзаке.
     */
    public Courier(int courierId, int courierSpeed, int packCapacity) {
        this.id = courierId;
        this.speed = courierSpeed;
        this.capacity = packCapacity;
        this.isPizzeriaOpen = true;
        wearBackpack();
    }


    /**
     * Полная инициализация всех параметров.
     *
     * @param courierId номер курьера.
     * @param courierSpeed сколько милисекунд работает курьер.
     * @param packCapacity сколько пицц можно переносить в рюкзаке.
     * @param storeQueue очередь склада.
     */
    public Courier(int courierId, int courierSpeed, int packCapacity, BlockingDesk storeQueue) {
        this(courierId, courierSpeed, packCapacity);
        setStore(storeQueue);
    }


    /**
     * Функция вернет номер курьера.
     *
     * @return courierId.
     */
    public int getId() {
        return this.id;
    }


    /**
     * Функция вернет скорость доставки заказов курьером.
     *
     * @return courierSpeed.
     */
    public int getSpeed() {
        return this.speed;
    }


    /**
     * Функция вернет вместимость курьерского рюкзака.
     *
     * @return backpackCapacity.
     */
    public int getPackCapacity() {
        return this.capacity;
    }


    /**
     * Определяет рюкзак курьера, как пустой стек.
     */
    public void wearBackpack() {
        this.backpack = new Stack<Order>();
    }


    /**
     * Определяем склад, из которого курьер будет получать заказы.
     *
     * @param storeQueue
     */
    public void setStore(BlockingDesk storeQueue) {
        if (this.storeQueue == null && storeQueue != null) {
            this.storeQueue = storeQueue;
        } else {
            logger.error(this + " курьер пытается поменять текущую очередь склада!");
            throw new RuntimeException();
        }
    }


    /**
     * Исоплнение задачи курьера по доставке заказов со склада. Курьер сначала будет ждать,
     * пока не появится первая пицца, которую он может сложить в свой рюкзак. Затем, сложив её он
     * попробует взять ещё пиццы со склада, если пиццы больше нет, то он поедет развозить взятые
     * заказы. Если пицца ещё есть, то он будет брать новые заказы, пока не заполнится его
     * портфель. Потом он доставить взятые пиццы. Курьер будет исполнять этот цикл, пока пиццерия
     * не закрылась или пока не опустел склад.
     * Если пиццерия закрылась в разгар работы курьера, то курьер будет доставлять заказы пока
     * не опустеет склад.
     */
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
