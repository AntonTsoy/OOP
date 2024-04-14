package pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CourierTest {

    BlockingDesk tasksList;
    static int cnt;

    @BeforeEach
    void setUp() throws InterruptedException {
        cnt = 0;
        tasksList = new BlockingDesk(3);
        tasksList.addFirst(Order.STOREHOUSE);
        tasksList.push(Order.DELIVERING);
    }

    @Test
    void test() {
        
    }

}