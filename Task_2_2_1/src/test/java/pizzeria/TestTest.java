package pizzeria;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


class TestTest {
    
    @Test
    void checkAbobus() throws IOException {
        System.err.println();
        User user = new User("Clark");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(user);

        try (FileWriter writer = new FileWriter(".\\src\\test\\java\\resources\\User.json")) {
            writer.write(json);
            writer.flush();
        } catch (Exception e) {
            System.err.println("Problems with opening Big10GB.txt");
            throw new RuntimeException(e);
        }
    }
}
