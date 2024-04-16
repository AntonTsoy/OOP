package pizzeria;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.junit.jupiter.api.Test;


public class PizzeriaTest {

    @Test
    void mainWorkingTest() throws IOException, InterruptedException {
        Pizzeria dodoPizza = new Pizzeria(
            new FileReader("src/main/resources/PizzeriaConfig.json"),
            new FileReader("src/main/resources/Orders.json"),
            new FileReader("src/main/resources/Store.json")
        );

        long startTime = new Date().getTime();
        dodoPizza.workingDay(
            new FileWriter("src/test/resources/Orders.json"),
            new FileWriter("src/test/resources/Store.json")
        );
        long timeDiff = new Date().getTime() - startTime;
        assertTrue(timeDiff >= dodoPizza.getWorkMins());
    }

    /*
    @Test
    void mainPizzeriaTest() throws FileNotFoundException, InterruptedException, IOException {
        Pizzeria dodoPizza = new Pizzeria(
            new FileReader("src/main/resources/PizzeriaConfig.json"),
            new FileReader("src/main/resources/Orders.json"),
            new FileReader("src/main/resources/Store.json")
        );
        assertNotEquals(null, dodoPizza);
    }
    */
}
