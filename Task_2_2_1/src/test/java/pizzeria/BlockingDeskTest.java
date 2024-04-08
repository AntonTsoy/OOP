package pizzeria;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
                Order freeCrminal = prison.pop();
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
        police1.start();
        police2.start();
        police1.join();
        police2.join();
        police3.start();

        Thread lawyer1 = new Thread(taskLawyer);
        Thread lawyer2 = new Thread(taskLawyer);
        lawyer1.start();
        lawyer1.join();
        police3.join();
        lawyer2.start();
        lawyer2.join();

        lawyer1.run();
        lawyer2.run();
        police1.run();

        police1.join();
        lawyer2.join();

        assertTrue(prison.isEmpty());
    }
}