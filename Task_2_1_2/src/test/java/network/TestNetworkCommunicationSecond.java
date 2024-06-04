package network;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Класс для тестирования сетевого взаимодействия.
 */
public class TestNetworkCommunicationSecond {    

    @Test
    public void testWorkPresentation() throws IOException, InterruptedException {
        Client client1 = new Client("230.0.0.0", 12345, "localhost");
        client1.completeTask();
        Client client2 = new Client("230.0.0.0", 12345, "localhost");
        client2.completeTask();
        Client client3 = new Client("230.0.0.0", 12345, "localhost");
        client3.completeTask();
        Client client4 = new Client("230.0.0.0", 12345, "localhost");
        client4.completeTask();

        Integer[] arr = new Integer[]{997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997,
            997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 8, 10, 20, 30, 40};
        List<String> tasks = Parser.makeTaskListFromIntegerList(Arrays.stream(arr).toList());
        Server server = new Server(8080, "230.0.0.0", 12345);
        boolean result = server.hasUnsimpleNumber(tasks);
        Assertions.assertTrue(result);
    }
}
