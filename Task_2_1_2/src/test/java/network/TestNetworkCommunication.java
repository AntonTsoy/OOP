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
public class TestNetworkCommunication {

    @Test
    public void testWorkPresentation() throws IOException, InterruptedException {
        int[] arr = new int[]{997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997,
            997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 8, 10, 20, 30, 40};
        List<String> tasks = Parser.makeTaskListFromIntegerList(
            Arrays.stream(arr).boxed().toList());

        Client client1 = new Client("230.0.0.0", 12345, "localhost");
        Client client2 = new Client("230.0.0.0", 12345, "localhost");
        Client client3 = new Client("230.0.0.0", 12345, "localhost");
        Client client4 = new Client("230.0.0.0", 12345, "localhost");
        client1.completeTask();
        client2.completeTask();
        client3.completeTask();
        client4.completeTask();
        Server server = new Server(8080, "230.0.0.0", 12345);
        Assertions.assertTrue(server.hasUnsimpleNumber(tasks));
    }


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


    @Test 
    public void testServerStableness() throws IOException, InterruptedException {
        Client client1 = new Client("230.0.0.0", 12345, "localhost");
        Client client2 = new Client("230.0.0.0", 12345, "localhost");
        Client client3 = new Client("230.0.0.0", 12345, "localhost");
        List<Thread> clients = new ArrayList<Thread>();
        clients.add(client1.completeTask());
        clients.add(client2.completeTask());
        clients.add(client3.completeTask());

        Thread interrupter = new Thread(() -> {
            for (Thread client : clients) {
                client.interrupt();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
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
