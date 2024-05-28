package network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;


public class Server {
    private final String host;
    private final int tcpPort;
    private final String multicastAddress;
    private final int udpPort;

    public Server(String ipAddress, int tcpPort, String multicastAddress, int udpPort) throws SocketException {
        this.host = ipAddress;
        this.tcpPort = tcpPort;
        this.multicastAddress = multicastAddress;
        this.udpPort = udpPort;
    }

    public boolean hasUnsimpleNumber(List<Integer> checkingList) {
        return false;
    }

    public String ping() throws IOException {
        multicastPublish(Integer.toString(this.tcpPort));
        ServerSocket serverSocket = new ServerSocket(this.tcpPort);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            writer.println("ping");
            break;
        }
        return "pong";
    }

    private void multicastPublish(String message) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName(this.multicastAddress);
        byte[] buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, this.udpPort);
        socket.send(packet);
        socket.close();
    }
}
