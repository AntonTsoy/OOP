package network;

import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<String> tasksForClients = new ArrayList<>();
        tasksForClients.add("13 19 7 5");
        //tasksForClients.add("2 3 5 11");
        //tasksForClients.add("31 1");

        Server server = new Server("Some Ip address", 8080, "230.0.0.0", 12345);
        Client client = new Client("230.0.0.0", 12345, "localhost");
        System.out.println(Thread.currentThread().getId() + " Launch server and client");
        client.completeTask();
        System.out.println(server.hasUnsimpleNumber(tasksForClients));
    }   
}
