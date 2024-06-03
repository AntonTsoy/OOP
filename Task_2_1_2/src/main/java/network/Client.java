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
    private static final int maxHostAddressLength = 1024;

    private final String multicastAddress;
    private final int udpPort;
    private final String serverAddress;

    public Client(String multicastAddress, int udpPort, String serverAddress) {
        this.multicastAddress = multicastAddress;
        this.udpPort = udpPort;
        this.serverAddress = serverAddress;
    }


    @SuppressWarnings("deprecation")
    private String multicastRead() {
        try (MulticastSocket socket = new MulticastSocket(this.udpPort)) {
            InetAddress group = InetAddress.getByName(this.multicastAddress);
            socket.joinGroup(group);
            byte[] buffer = new byte[maxHostAddressLength];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println(Thread.currentThread().getId() + " client wants receiving multicast;");
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            socket.leaveGroup(group);
            return received;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private Socket setTcpConnection() throws IOException {
        String multicastMessage = multicastRead();
        int tcpPort = Integer.parseInt(multicastMessage);
        return new Socket(serverAddress, tcpPort);
    }


    public Thread completeTask() {
        Thread task = new Thread(() -> {
            try (Socket clientSocket = setTcpConnection();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
                 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                while (!Thread.currentThread().isInterrupted()) {
                    String serverTask = reader.readLine();
                    if (serverTask == null) {
                        System.out.println(Thread.currentThread().getId() + " client finishing.");
                        break;
                    }
                    System.out.println(Thread.currentThread().getId() + 
                        " client sending task-answer;");
                    writer.println(PrimeNumberDetector.isNotPrimeNumbers(
                        Parser.parseStrToIntegerList(serverTask)));
                }
            } catch (IOException e) {
                System.out.println(Thread.currentThread().getId() + 
                    " client couldn't connect or lost connection - finishing.");
            }
        });
        task.start();
        return task;
    }
}
