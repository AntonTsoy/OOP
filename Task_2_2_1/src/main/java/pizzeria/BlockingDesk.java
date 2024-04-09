package pizzeria;

import java.util.ArrayDeque;

import com.google.gson.annotations.Expose;


public class BlockingDesk {
    @Expose
    private ArrayDeque<Order> desk;
    private int limit;


    public BlockingDesk() {
        this.limit = -1;
        this.desk = new ArrayDeque<Order>();
    }

    public BlockingDesk(int deskLimit) {
        this.limit = deskLimit;
        this.desk = new ArrayDeque<Order>();
    }


    public synchronized void addFirst(Order newOrder) throws InterruptedException {
        if (this.limit > 0) {
            while (this.desk.size() == this.limit) {
                wait();
            }
        }
        this.desk.addFirst(newOrder);
        notifyAll();
    }

    public synchronized void push(Order newOrder) throws InterruptedException {
        if (this.limit > 0) {
            while (this.desk.size() == this.limit) {
                wait();
            }
        }
        this.desk.add(newOrder);
        notifyAll();
    }

    public synchronized Order pop() throws InterruptedException {
        Order takenOrder;
        while (this.desk.size() == 0) {
            wait();
        }
        takenOrder = this.desk.poll();
        notifyAll();
        return takenOrder;
    }

    public synchronized Order freePop() {
        if (this.desk.size() == 0) {
            return null;
        }
        return this.desk.poll();
    }

    public synchronized boolean isEmpty() {
        return this.desk.size() == 0;
    }

    public synchronized int size() {
        return this.desk.size();
    }
}
