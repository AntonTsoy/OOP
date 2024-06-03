package network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            arr.add(i);
        }
        List<String> tasks = Parser.makeTaskListFromIntegerList(arr);

        Server server = new Server("Some Ip address", 8080, "230.0.0.0", 12345);
        Client client1 = new Client("230.0.0.0", 12345, "localhost");
        Client client2 = new Client("230.0.0.0", 12345, "localhost");
        Client client3 = new Client("230.0.0.0", 12345, "localhost");
        Client client4 = new Client("230.0.0.0", 12345, "localhost");
        Client client5 = new Client("230.0.0.0", 12345, "localhost");
        client1.completeTask();
        client2.completeTask();
        client3.completeTask();
        client4.completeTask();
        client5.completeTask();
        System.out.println(Thread.currentThread().getId() + ":[main-thread] result = " + 
            server.hasUnsimpleNumber(tasks));
    }   
}
