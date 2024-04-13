package pizzeria;

import com.google.gson.annotations.Expose;

public class Chef implements Runnable {
    
    @Expose
    private final int id;
    @Expose
    private final int speed;

    private BlockingDesk orderQueue;
    private BlockingDesk storeQueue;
    private Order currOrder;

    public Chef(int chefId, int chefSpeed) {
        this.id = chefId;
        this.speed = chefSpeed;
    }

    public Chef(int chefId, int chefSpeed, BlockingDesk orders, BlockingDesk storehouse) {
        this.id = chefId;
        this.speed = chefSpeed;
        setQueues(orders, storehouse);
    }


    public int getChefId() {
        return this.id;
    }

    public int getChefSpeed() {
        return this.speed;
    }

    public void setQueues(BlockingDesk orders, BlockingDesk storehouse) {
        if (this.orderQueue == null && orders != null) {
            this.orderQueue = orders;
        } else {
            System.err.println("Повар пытается поменять текущую очередь заказов!!!");
            throw new RuntimeException();
        }

        if (this.storeQueue == null && storehouse != null) {
            this.storeQueue = storehouse;
        } else {
            System.err.println("Повар пытается поменять текущую очередь склада!!!");
            throw new RuntimeException();
        }
    }

    @Override
    public void run() {
        while (true) {
            
        }
    }

    @Override
    public String toString() {
        return "Повар #" + this.id + " co скоростью " + this.speed;
    }
}
