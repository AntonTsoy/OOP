package pizzeria;

import java.util.Date;
import java.util.Stack;

import com.google.gson.annotations.Expose;


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

    public Courier(int courierId, int courierSpeed, int packCapacity) {
        this.id = courierId;
        this.speed = courierSpeed;
        this.capacity = packCapacity;
        this.isPizzeriaOpen = true;
        this.backpack = new Stack<Order>();
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

    public void setStore(BlockingDesk storeQueue) {
        if (this.storeQueue == null && storeQueue != null) {
            this.storeQueue = storeQueue;
        } else {
            System.err.println("Курьер пытается поменять текущую очередь склада!!!");
            throw new RuntimeException();
        }
    }


    @Override
    public void run() {
        
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
