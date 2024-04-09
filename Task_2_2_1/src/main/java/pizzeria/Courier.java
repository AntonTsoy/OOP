package pizzeria;

import java.util.Stack;

import com.google.gson.annotations.Expose;


public class Courier implements Runnable {
    
    @Expose
    private final int id;
    @Expose
    private final int speed;
    @Expose
    private final int capacity;

    private boolean isPizzeriaOpened;

    private BlockingDesk storeQueue;
    private Stack<Order> backpack;

    public Courier(int courierId, int courierSpeed, int packCapacity) {
        this.id = courierId;
        this.speed = courierSpeed;
        this.capacity = packCapacity;
        this.isPizzeriaOpened = true;
        this.backpack = new Stack<Order>();
    }

    public Courier(int courierId, int courierSpeed, int packCapacity, BlockingDesk storeQueue) {
        this.id = courierId;
        this.speed = courierSpeed;
        this.capacity = packCapacity;
        this.isPizzeriaOpened = true;
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
        if (this.storeQueue == null && storeQueue == null) {
            this.storeQueue = storeQueue;
        } else {
            System.err.println("Курьер пытается поменять текущую очередь склада!!!");
            throw new RuntimeException();
        }
    }


    // Я не доделал эту хрень. Надо все внимательно проверить
    @Override
    public void run() {
        while (this.isPizzeriaOpened || !this.storeQueue.isEmpty()) {
            try {
                this.backpack.push(this.storeQueue.pop());
            } catch (InterruptedException e) {
                this.isPizzeriaOpened = false;
            }

            while (this.backpack.size() < this.capacity) {
                Order triedTakenOrder = this.storeQueue.freePop();
                if (triedTakenOrder != null) {
                    this.backpack.push(triedTakenOrder);
                } else {
                    break;
                }
            }

            while (!this.backpack.empty()) {
                try {
                    Thread.sleep(this.speed);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                }
                this.backpack.pop();
            }
            
            try {
                Thread.sleep(this.speed);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
            }
        }
    }

    @Override
    public String toString() {
        return "Курьер #" + this.id + " со скоростью " + this.speed +
               " и вместимостью " + this.capacity;
    }
}
