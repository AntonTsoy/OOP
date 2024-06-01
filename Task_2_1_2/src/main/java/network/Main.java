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
        Thread serverThread = new Thread(new ServerTask("Some Ip address", 8080, "230.0.0.0", 12345));
        serverThread.start();
        serverThread.join();
        client1.join();
        client2.join();
        client2.join();
        System.out.println("Finish");
    }

    private static class ServerTask implements Runnable {

        private final Server server;

        public ServerTask(String ipAddress, int tcpPort, String multicastAddress, int udpPort) throws SocketException {
            this.server = new Server(ipAddress, tcpPort, multicastAddress, udpPort);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println("Answer from client: " + server.ping());
            } catch (InterruptedException e) {
                System.out.println("Sys_message: Server was interrupted;\n");
                e.printStackTrace();
            }
        }
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
