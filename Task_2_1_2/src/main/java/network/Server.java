package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Server {
    private final String host;
    private final int tcpPort;
    private final String multicastAddress;
    private final int udpPort;

    public Server(String ipAddress, int tcpPort, String multicastAddress, int udpPort) {
        this.host = ipAddress;
        this.tcpPort = tcpPort;
        this.multicastAddress = multicastAddress;
        this.udpPort = udpPort;
    }

    public boolean hasUnsimpleNumber(List<Integer> checkingList) {
        return false;
    }

    private void multicastPublish(String message) {
        try {
            DatagramSocket socket;
            socket = new DatagramSocket();
            InetAddress group;
            group = InetAddress.getByName(this.multicastAddress);
            byte[] buf = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, this.udpPort);
            socket.send(packet);
            socket.close();
            System.out.println("Server made BROADCAST package sending;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Runnable pingNewClient(ServerSocket serverSocket) {
        Runnable pingTask = () -> {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server accepting new connection;");
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println("ping");
                System.out.println("Server sent new ping-connection message;");

                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                String clientMessage = reader.readLine();
                System.out.println("[" + clientSocket + "]: " + clientMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        return pingTask;
    }

    public void broadcastPing(int connectionsNumber) throws InterruptedException {
        try {
            multicastPublish(Integer.toString(this.tcpPort));
            ServerSocket serverSocket = new ServerSocket(this.tcpPort);
            Thread[] connections = new Thread[connectionsNumber];
            for (int connectId = 0; connectId < connectionsNumber; connectId++) {
                connections[connectId] = new Thread(pingNewClient(serverSocket));
                connections[connectId].start();
            }
            for (int connectId = 0; connectId < connectionsNumber; connectId++) {
                connections[connectId].join();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}