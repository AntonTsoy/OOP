package network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Класс для тестирования сетевого взаимодействия.
 */
public class TestNetworkCommunicationFirst {
    @Test 
    public void testServerStableness() throws IOException, InterruptedException {
        List<Thread> clients = new ArrayList<Thread>();
        Client client1 = new Client("230.0.0.0", 12345, "localhost");
        clients.add(client1.completeTask());
        Client client2 = new Client("230.0.0.0", 12345, "localhost");
        clients.add(client2.completeTask());
        Client client3 = new Client("230.0.0.0", 12345, "localhost");
        clients.add(client3.completeTask());
        
        Thread interrupter = new Thread(() -> {
            for (Thread client : clients) {
                client.interrupt();
            }

            Client goodClient = new Client("230.0.0.0", 12345, "localhost");
            goodClient.completeTask();
        });
        interrupter.start();

        Server server = new Server(8080, "230.0.0.0", 12345);
        boolean result = server.hasUnsimpleNumber(Arrays.asList(
            "911 919 997", 
            "953 617 379", 
            "883 479 149", 
            "911 919 929", 
            "3 5 7 11", 
            "43 19", 
            "1"));
        Assertions.assertTrue(result);
    }
}
