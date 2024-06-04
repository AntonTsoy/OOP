package network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    public static void amain(String[] args) {
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
        boolean result;
        try {
            result = server.hasUnsimpleNumber(Arrays.asList(
                "911 919 997", 
                "953 617 379", 
                "883 479 149", 
                "911 919 929", 
                "3 5 7 11", 
                "43 19", 
                "1"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
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
        try {
            System.out.println(server.hasUnsimpleNumber(tasks));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
