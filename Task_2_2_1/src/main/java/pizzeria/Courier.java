package pizzeria;

import java.util.Date;
import java.util.Stack;

import com.google.gson.annotations.Expose;

public class Courier implements Runnable {
    
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
        this.id = courierId;
        this.speed = courierSpeed;
        this.capacity = packCapacity;
        this.isPizzeriaOpen = true;
        this.backpack = new Stack<Order>();
        setStore(storeQueue);
    }


    public int getCourierId() {
        return this.id;
    }

    public int getCourierSpeed() {
        return this.speed;
    }

    public int getCourierPackCapacity() {
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
        Date timer = new Date();
        while (this.isPizzeriaOpen || !this.storeQueue.isEmpty()) {
            try {
                Order takenOrder = this.storeQueue.pop();
                takenOrder.nextState();
                this.backpack.push(takenOrder);
            } catch (InterruptedException e) {
                this.isPizzeriaOpen = false;
            }
            if (Thread.currentThread().isInterrupted()) {
                System.err.println();
                this.isPizzeriaOpen = false;
            }

            while (this.backpack.size() < this.capacity) {
                Order triedTakenOrder = this.storeQueue.freePop();
                if (triedTakenOrder != null) {
                    this.backpack.push(triedTakenOrder);
                } else {
                    break;
                }
                try {
                    triedTakenOrder.nextState();
                } catch (InterruptedException e) {
                    this.isPizzeriaOpen = false;
                }
            }
            if (Thread.currentThread().isInterrupted()) {
                this.isPizzeriaOpen = false;
            }

            while (!this.backpack.empty()) {
                long difTime;
                long startTime = timer.getTime();
                try {
                    Thread.sleep(this.speed);
                } catch (InterruptedException e) {
                    difTime = timer.getTime() - startTime;
                    this.isPizzeriaOpen = false;
                    try {
                        Thread.sleep(this.speed - difTime);
                    } catch (InterruptedException error) {
                        System.err.println("Курьер #" + this.id + " был остановлен, пока доставлял заказы уже после закрытия");
                        error.printStackTrace();
                        throw new RuntimeException();
                    }
                }
                try {
                    this.backpack.pop().nextState();
                } catch (InterruptedException e) {
                    this.isPizzeriaOpen = false;
                }
            }
            if (Thread.currentThread().isInterrupted()) {
                this.isPizzeriaOpen = false;
            }
        }
    }

    @Override
    public String toString() {
        return "Курьер #" + this.id + " со скоростью " + this.speed +
               " и вместимостью " + this.capacity;
    }
}
