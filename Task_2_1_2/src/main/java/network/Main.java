package network;

import java.net.SocketException;


public class Main {

    public static void main(String[] args) throws SocketException, InterruptedException {
        System.out.println("Begin");
        Thread client1 = new Thread(new ClientTask("230.0.0.0", 12345, "localhost", "1"));
        Thread client2 = new Thread(new ClientTask("230.0.0.0", 12345, "localhost", "2"));
        Thread client3 = new Thread(new ClientTask("230.0.0.0", 12345, "localhost", "3"));
        client1.start();
        client2.start();
        client3.start();
        Thread.sleep(1000);
        Server server = new Server("Some Ip address", 8080, "230.0.0.0", 12345);
        server.broadcastPing(3);
        client1.join();
        client2.join();
        client2.join();
        System.out.println("Finish");
    }


    private static class ClientTask implements Runnable {

        private final Client client;

        public ClientTask(String multicastAddress, int udpPort, String serverAddress, String addition) {
            this.client = new Client(multicastAddress, udpPort, serverAddress, addition);
        }

        @Override
        public void run() {
            client.waitPing();
        }
    }
}
