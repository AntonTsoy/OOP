package pizzeria;

import java.util.ArrayDeque;

import com.google.gson.annotations.Expose;


public class BlockingDesk {
    @Expose
    private ArrayDeque<Order> desk;
    @Expose
    private int limit;

    private Object pushLock = new Object();
    private Object popLock = new Object();

    public BlockingDesk() {
        this.limit = -1;
        this.desk = new ArrayDeque<Order>();
    }

    public BlockingDesk(int deskLimit) {
        this.limit = deskLimit;
        this.desk = new ArrayDeque<Order>();
    }


    public void push(Order newOrder) throws InterruptedException {
        synchronized (this.desk) {
            if (this.limit > 0 && this.desk.size() >= this.limit) {
                this.pushLock.wait();
            }
            this.desk.add(newOrder);
            this.popLock.notify();
        }
    }

    public Order pop() throws InterruptedException {
        Order takenOrder;
        synchronized (this.desk) {
            if (this.desk.size() == 0) {
                this.popLock.wait();
            }
            takenOrder = desk.poll();
            this.pushLock.notify();
        }

        return takenOrder;
    }

    public boolean isEmpty() {
        synchronized (this.desk) {
            return this.desk.size() == 0;
        }
    }
}
