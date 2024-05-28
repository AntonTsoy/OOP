package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;


public class Client {
    private static final int maxHostAddressLength = 22;

    private final String multicastAddress;
    private final int udpPort;
    private final String serverAddress;

    public Client(String multicastAddress, int udpPort, String serverAddress) {
        this.multicastAddress = multicastAddress;
        this.udpPort = udpPort;
        this.serverAddress = serverAddress;
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

    public void waitPing() throws IOException {
        String multicastMessage = multicastRead();
        int tcpPort = Integer.parseInt(multicastMessage);
        while (true) {
            Socket clientSocket = new Socket("localhost", tcpPort);
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            String serverMessage = reader.readLine();
            
            if (serverMessage == "ping") {
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println("pong");
                writer.close();
            }

            reader.close();
            clientSocket.close();
        }
    }
}
