package pizzeria;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;


public class PizzeriaTest {

    @Test
    void mainPizzeriaTest() throws FileNotFoundException, InterruptedException, IOException {
        Pizzeria dodoPizza = new Pizzeria(
            new FileReader("src/main/resources/PizzeriaConfig.json"),
            new FileReader("src/main/resources/Orders.json"),
            new FileReader("src/main/resources/Store.json")
        );
        assertNotEquals(null, dodoPizza);
    }

    @Test
    void mainWorkingTest() throws IOException, InterruptedException {
        Pizzeria dodoPizza = new Pizzeria(
            new FileReader("/src/main/resources/PizzeriaConfig.json"),
            new FileReader("/src/main/resources/Orders.json"),
            new FileReader("/src/main/resources/Store.json")
        );

        dodoPizza.workingDay(
            new FileWriter("src/test/resources/Orders.json"),
            new FileWriter("src/test/resources/Store.json")
        );
    }
}