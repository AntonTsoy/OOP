package pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ChefTest {

    BlockingDesk tasksList;

    @BeforeEach
    void setUp() {
        tasksList = new BlockingDesk();
    }
    
    @Test
    void testChefsCommunication() throws InterruptedException {

        Thread chef1 = new Thread(new ChefRun(tasksList));
        Thread chef2 = new Thread(new ChefRun(tasksList));
        chef1.start();
        chef2.start();
        chef1.join();
        chef2.join();
        System.err.println(tasksList);
        assertEquals(2, tasksList.size());
    }

    private static class ChefRun implements Runnable {

        BlockingDesk kithenTasks;

        public ChefRun(BlockingDesk tasks) {
            this.kithenTasks = tasks;
        }

        @Override
        public void run() {
            Order completedOrder = Order.COOKING;
            try {
                this.kithenTasks.push(completedOrder);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}