package network;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        int[] arr = new int[]{997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997,
            997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 997, 8, 10, 20, 30, 40};
        List<String> tasks = Parser.makeTaskListFromIntegerList(Arrays.stream(arr).boxed().toList());

        Server server = new Server(8080, "230.0.0.0", 12345);
        Client client1 = new Client("230.0.0.0", 12345, "localhost");
        Client client2 = new Client("230.0.0.0", 12345, "localhost");
        Client client3 = new Client("230.0.0.0", 12345, "localhost");
        Client client4 = new Client("230.0.0.0", 12345, "localhost");
        List<Thread> clients = new ArrayList<Thread>();
        clients.add(client1.completeTask());
        clients.add(client2.completeTask());
        clients.add(client3.completeTask());
        clients.add(client4.completeTask());
        System.out.println(Thread.currentThread().getId() + ":[main-thread] result = " + 
            server.hasUnsimpleNumber(tasks));
    }
}
