package pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;

import java.util.Date;


public class BlockingDeskTest {

    BlockingDesk deque;

    public static class Baker implements Runnable {

        private BlockingDesk bakerBox;

        public Baker(BlockingDesk box) {
            this.bakerBox = box;
        }

        public void run() {
            Order newCake = new Order();
            newCake.nextState();
            try {
                Thread.sleep(100);
                this.bakerBox.push(newCake);
            } catch (InterruptedException e) {
                newCake.resetState();
                return;
            }
            System.err.println("Пекарь завершил приготовление пирога и положил его в бокс");
        }
    }

    public static class Deliver implements Runnable {

        private BlockingDesk deliverBox;
        private Order currCake;

        public Deliver(BlockingDesk box) {
            this.deliverBox = box;
        }

        public void run() {
            try {
                this.currCake = this.deliverBox.pop();
            } catch (InterruptedException e) {
                System.err.println("Вафля закрылась, пока курьер доставал пирог из бокса");
            }

            System.err.println("Курьер жив");
            if (this.currCake == null) {
                this.currCake = this.deliverBox.freePop();
                System.err.println("Курьер хочет жить");
                if (this.currCake == null) {
                    return;
                }
            }
            System.err.println("Курьер до сих пор жив");

            this.currCake.nextState();

            long diffTime = -1;
            long startTime = new Date().getTime();
            try {
                Thread.sleep(90);
            } catch (InterruptedException e) {
                diffTime = new Date().getTime() - startTime;
                System.err.println("Вафля закрылась, пока курьер вёз пирог");
            }
            
            if (diffTime >= 0) {
                try {
                    Thread.sleep(90 - diffTime);
                } catch (InterruptedException e) {
                    System.err.println("ERROR!");
                    return;
                }
            }

            this.currCake.nextState();
            System.err.println("Курьер выполнил доставку: " + this.currCake);
        }
    }


    @Test
    void checkKitchen() throws InterruptedException {
        BlockingDesk box = new BlockingDesk(2);

        Thread baker1 = new Thread(new Baker(box));
        Thread baker2 = new Thread(new Baker(box));
        Thread deliver = new Thread(new Deliver(box));

        baker1.start();
        baker2.start();
        deliver.start();

        Thread.sleep(250);

        baker1.interrupt();
        baker2.interrupt();
        deliver.interrupt();

        if (deliver.isAlive()) {
            Thread.sleep(90);
        }
        assertFalse(baker1.isAlive());
        assertFalse(baker2.isAlive());

        System.err.println(box);
        assertEquals(1, box.size());
    }

    @Test
    void checkPrison() throws InterruptedException {
        deque = new BlockingDesk(2);

        Runnable taskPolice = () -> {
            Order caughtCriminal = new Order();
            try {
                deque.push(caughtCriminal);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable taskLawyer = () -> {
            try {
                deque.pop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread police1 = new Thread(taskPolice);
        Thread police2 = new Thread(taskPolice);
        Thread police3 = new Thread(taskPolice);
        Thread lawyer = new Thread(taskLawyer);
        lawyer.start();
        police1.start();
        police2.start();
        police3.start();

        police1.join();
        police2.join();
        police3.join();
        if (!lawyer.isAlive()) {
            assertEquals(2, deque.size());
        } else {
            throw new ConcurrentModificationException();
        }
    }
}
