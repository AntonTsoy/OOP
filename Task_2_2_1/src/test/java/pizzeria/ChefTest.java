package pizzeria;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Тест поваров.
 */
public class ChefTest {

    BlockingDesk toDoList;
    BlockingDesk doneList;

    @BeforeEach
    void setUp() {
        toDoList = new BlockingDesk();
        doneList = new BlockingDesk();
    }
    
    @Test
    void testChefsCommunication() throws InterruptedException {
        toDoList.push(new Order());
        toDoList.push(new Order());
        toDoList.push(new Order());
        System.out.println("TODO: " + toDoList);
        Thread chef1 = new Thread(new Chef(1, 30, toDoList, doneList));
        Thread chef2 = new Thread(new Chef(2, 50, toDoList, doneList));
        chef1.start();
        chef2.start();
        Thread.sleep(120);
        chef1.interrupt();
        chef2.interrupt();
        chef1.join();
        chef2.join();
        System.out.println("TODO: " + toDoList);
        System.out.println("DONE: " + doneList);
        assertTrue(toDoList.size() == 0 && doneList.size() == 3);
    }
}