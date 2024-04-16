package pizzeria;

import java.util.ArrayDeque;

import com.google.gson.annotations.Expose;


public class BlockingDesk {
    @Expose
    private ArrayDeque<Order> desk;
    private int limit;


    public BlockingDesk() {
        this.limit = Integer.MIN_VALUE;
        this.desk = new ArrayDeque<Order>();
    }

    public BlockingDesk(int deskLimit) {
        this.limit = deskLimit;
        this.desk = new ArrayDeque<Order>();
    }


    public synchronized void addFirst(Order newOrder) throws InterruptedException {
        if (this.limit != Integer.MIN_VALUE) {
            while (this.desk.size() == this.limit) {
                wait();
            }
        }
        this.desk.addFirst(newOrder);
        notifyAll();
    }

    public synchronized void push(Order newOrder) throws InterruptedException {
        if (this.limit != Integer.MIN_VALUE) {
            while (this.desk.size() == this.limit) {
                wait();
            }
        }
        this.desk.add(newOrder);
        notifyAll();
    }

    public synchronized Order pop() throws InterruptedException {
        Order takenOrder;
        while (this.desk.isEmpty()) {
            wait();
        }
        takenOrder = this.desk.poll();
        notifyAll();
        return takenOrder;
    }

    public synchronized Order freePop() {
        if (this.desk.isEmpty()) {
            return null;
        }
        return this.desk.poll();
    }


    public synchronized boolean isEmpty() {
        return this.desk.isEmpty();
    }


    public synchronized int size() {
        return this.desk.size();
    }

    @Override
    public String toString() {
        return this.desk.toString();
    }
}
