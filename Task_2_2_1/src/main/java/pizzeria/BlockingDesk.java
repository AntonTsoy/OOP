package pizzeria;

import java.util.ArrayDeque;


public class BlockingDesk {
    
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


    public void push(Order newOrder) throws InterruptedException {
        synchronized (this.desk) {
            while (this.limit > 0 && this.desk.size() >= this.limit) {
                this.desk.wait();
            }
            desk.add(newOrder);
            desk.notify();
        }
    }

    public Order pop() throws InterruptedException {
        Order takenOrder;

        synchronized (this.desk) {
            while (this.desk.size() == 0) {
                this.desk.wait();
            }
            takenOrder = desk.poll();
            desk.notify();
        }

        return takenOrder;
    }
}
