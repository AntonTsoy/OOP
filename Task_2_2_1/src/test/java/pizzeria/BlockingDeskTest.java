package pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;


public class BlockingDeskTest {

    BlockingDesk prison;
    Runnable taskPolice;
    Runnable taskLawyer;

    @BeforeEach
    void setUp() {
        prison = new BlockingDesk(2);

        taskPolice = () -> {
            Order caughtCriminal = new Order();
            try {
                prison.push(caughtCriminal);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        taskLawyer = () -> {
            try {
                prison.pop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    @Test
    void checkMultithreading() throws InterruptedException {
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
            assertEquals(2, prison.size());
        } else {
            throw new ConcurrentModificationException();
        }
    }
}