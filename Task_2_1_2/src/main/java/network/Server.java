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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ping() {
        try {
            multicastPublish(Integer.toString(this.tcpPort));
            ServerSocket serverSocket;
            serverSocket = new ServerSocket(this.tcpPort);
            Socket clientSocket;
            clientSocket = serverSocket.accept();
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            writer.println("ping");

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            String clientMessage = reader.readLine();
            serverSocket.close();
            return clientMessage;
        } catch (IOException e) {
            e.printStackTrace();
            return "NOTHING";
        }
    }
}
