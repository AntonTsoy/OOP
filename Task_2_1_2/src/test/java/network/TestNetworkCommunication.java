package network;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestNetworkCommunication {

    private static final String MULTICAST_ADDRESS = "230.0.0.0";
    private static final int UDP_PORT = 12345;
    private static final int TCP_PORT = 8080;
    private Server server;
    private Client client;

    @BeforeEach
    public void setUp() {
        server = new Server(TCP_PORT, MULTICAST_ADDRESS, UDP_PORT);
        client = new Client(MULTICAST_ADDRESS, UDP_PORT, "localhost");
    }
    

    @Test
    public void testWorkPresentation() throws IOException, InterruptedException {
        int[] arr = new int[]{997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997,
            997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 8, 10, 20, 30, 40};
        List<String> tasks = Parser.makeTaskListFromIntegerList(Arrays.stream(arr).boxed().toList());

        Client client1 = new Client("230.0.0.0", 12345, "localhost");
        Client client2 = new Client("230.0.0.0", 12345, "localhost");
        Client client3 = new Client("230.0.0.0", 12345, "localhost");
        Client client4 = new Client("230.0.0.0", 12345, "localhost");
        client1.completeTask();
        client2.completeTask();
        client3.completeTask();
        client4.completeTask();
        Assertions.assertTrue(server.hasUnsimpleNumber(tasks));
    }


    @Test
    public void testClientServerInteraction() {
        // Start client in a separate thread
        client.completeTask();
        // Start server in a separate thread
        try {
            List<String> tasks = Arrays.asList("2 3 5 7", "11 13 17", "19 23 29");
            boolean result = server.hasUnsimpleNumber(tasks);
            assertFalse(result, "The server should determine that all tasks are prime-free.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail("Server failed to complete tasks.");
        }
    }


    
}
