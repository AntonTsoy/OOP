package pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CourierTest {

    BlockingDesk tasksList;

    @BeforeEach
    void setUp() {
        tasksList = new BlockingDesk(6);
    }

    @Test
    void test() throws InterruptedException {
        tasksList.addFirst(new Order());
        tasksList.push(new Order());
        tasksList.addFirst(new Order());
        tasksList.push(new Order());
        tasksList.addFirst(new Order());
        tasksList.push(new Order());
        Thread deliver1 = new Thread(new Courier(100, 60, 2, tasksList));
        Thread deliver2 = new Thread(new Courier(200, 55, 3, tasksList));
        deliver1.start();
        deliver2.start();
        Thread.sleep(90);
        deliver1.interrupt();
        deliver2.interrupt();
        deliver1.join();
        deliver2.join();
        System.out.println(tasksList);
        assertEquals(0, tasksList.size());
    }

}