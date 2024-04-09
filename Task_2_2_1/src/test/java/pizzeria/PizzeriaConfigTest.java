package pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class PizzeriaConfigTest {

    @Test
    void mainTestConfig() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();
        PizzeriaConfig config = gson.fromJson(new FileReader(
            "src/main/resources/PizzeriaConfig.json"), PizzeriaConfig.class);
        
        Chef[] chefs = config.getChefs();
        for (int i = 0; i < chefs.length; i++) {
            System.err.println(chefs[i]);
        }

        Courier[] couriers = config.getCouriers();
        for (int i = 0; i < couriers.length; i++) {
            System.err.println(couriers[i]);
        }

        System.err.println("Склад " + config.getStorehouseCapacity());
        System.err.println("Время " + config.getWorkMins());
        assertEquals(8, config.getStorehouseCapacity());
    }
}