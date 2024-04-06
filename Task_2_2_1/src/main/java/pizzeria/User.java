package pizzeria;

import java.io.Writer;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class User {

    private String name;

    public User(String name) {
        setName(name);
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return name;
    }

    public void abobus() throws IOException {
        String filePath = "gson_user.json";
        User user = new User("Clark");

        Writer writer = new FileWriter(filePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(user, writer);
    }
}
