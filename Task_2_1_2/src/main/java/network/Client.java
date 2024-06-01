package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Objects;


public class Client {
    private static final int maxHostAddressLength = 22;

    private final String multicastAddress;
    private final int udpPort;
    private final String serverAddress;
    private final String addition;

    public Client(String multicastAddress, int udpPort, String serverAddress, String addition) {
        this.multicastAddress = multicastAddress;
        this.udpPort = udpPort;
        this.serverAddress = serverAddress;
        this.addition = addition;
    }

    @SuppressWarnings("deprecation")
    private String multicastRead() throws IOException {
        MulticastSocket socket = new MulticastSocket(this.udpPort);
        InetAddress group = InetAddress.getByName(this.multicastAddress);
        socket.joinGroup(group);
        byte[] buf = new byte[maxHostAddressLength];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        socket.leaveGroup(group);
        socket.close();
        return received;
    }

    public void waitPing() {
        String multicastMessage;
        try {
            multicastMessage = multicastRead();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int tcpPort = Integer.parseInt(multicastMessage);
        System.out.println("Client will connect with port: " + tcpPort);

        Socket clientSocket;
        try {
            clientSocket = new Socket(serverAddress, tcpPort);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        BufferedReader reader;
        String serverMessage;
        try {
            reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            serverMessage = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Message from server: " + serverMessage);
        if (Objects.equals(serverMessage, "ping")) {
            PrintWriter writer;
            try {
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            writer.println("pong" + addition);
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
