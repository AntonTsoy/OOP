package network;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestNetworkCommunication {
    
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
        Server server = new Server(8080, "230.0.0.0", 12345);
        Assertions.assertTrue(server.hasUnsimpleNumber(tasks));
    }
}
