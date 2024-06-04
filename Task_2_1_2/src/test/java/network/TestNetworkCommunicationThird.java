package network;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Класс для тестирования сетевого взаимодействия.
 */
public class TestNetworkCommunicationThird {

    @Test
    public void testClientServerInteraction() {
        Server server = new Server(8080, "230.0.0.0", 12345);
        Client client = new Client("230.0.0.0", 12345, "localhost");

        // Start client in a separate thread
        client.completeTask();
        // Start server in a separate thread
        try {
            List<String> tasks = Arrays.asList("2 3 5 7", "11 13 17", "19 23 29");
            boolean result = server.hasUnsimpleNumber(tasks);
            Assertions.assertFalse(result, "The server should determine all tasks are prime.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Assertions.fail("Server failed to complete tasks.");
        }
    }
}
