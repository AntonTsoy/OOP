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
        System.out.println(addition + ": Client receive broadcast datagram packet;");
        String received = new String(packet.getData(), 0, packet.getLength());
        socket.leaveGroup(group);
        socket.close();
        return received;
    }


    


    public void waitPing() {
        try {
            String multicastMessage = multicastRead();
            System.out.println(addition + ": Client receives TCP port number from UDP broadcast message");
            int tcpPort = Integer.parseInt(multicastMessage);

            Socket clientSocket = new Socket(serverAddress, tcpPort);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String serverMessage = reader.readLine();

            System.out.println("[" + clientSocket + "] client receives: " + serverMessage);
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            if (Objects.equals(serverMessage, "ping")) {
                writer.println("pong" + addition);
            }
            
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
