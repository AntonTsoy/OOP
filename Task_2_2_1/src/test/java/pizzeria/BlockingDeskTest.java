package pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class BlockingDeskTest {

    BlockingDesk deque;
    final static Logger logger = LogManager.getLogger(ChefTest.class);

    public static class Baker implements Runnable {

        private BlockingDesk bakerBox;

        public Baker(BlockingDesk box) {
            this.bakerBox = box;
        }

        public void run() {
            Order newCake = new Order();
            try {
                Thread.sleep(100);
                this.bakerBox.push(newCake);
            } catch (InterruptedException e) {
                return;
            }
            logger.info("Пекарь завершил приготовление пирога и положил его в бокс");
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

}
