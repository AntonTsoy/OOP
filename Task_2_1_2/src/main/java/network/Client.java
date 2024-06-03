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
        try {
            MulticastSocket socket = new MulticastSocket(this.udpPort);
            InetAddress group = InetAddress.getByName(this.multicastAddress);
            socket.joinGroup(group);
            byte[] buf = new byte[maxHostAddressLength];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            System.out.println(Thread.currentThread().getId() + " client wants receiving multicast;"); 
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            socket.leaveGroup(group);
            socket.close();
            return received;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private Socket setTcpConnection() throws Exception {
        String multicastMessage;
        multicastMessage = multicastRead();
        int tcpPort = Integer.parseInt(multicastMessage);

        Socket clientSocket;
        clientSocket = new Socket(serverAddress, tcpPort);

        return clientSocket;
    }


    public Thread completeTask() {
        Thread task = new Thread(() -> {
            Socket clientSocket;
            try {
                clientSocket = setTcpConnection();
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getId() + " client couldn't connect - finishing.");
                return;
            }

            BufferedReader reader;
            PrintWriter writer;
            try {
                reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println(Thread.currentThread().getId() + " client couldn't open IO streams finishing.");
                return;
            }

            while (true) {
                String serverTask;
                try {
                    if (Thread.interrupted()) {
                        System.out.println(Thread.currentThread().getId() + " client finishing.");
                        return;
                    }
                    serverTask = reader.readLine();
                    if (serverTask == null) {
                        System.out.println(Thread.currentThread().getId() + " client finishing.");
                        return;
                    }
                } catch (IOException e) {
                    System.out.println("Client-thread: " + Thread.currentThread().getId() + " lost the connection.");
                    return;
                }

                System.out.println(Thread.currentThread().getId() + " client sending task-answer;");
                writer.println(PrimeNumberDetector.isNotPrimeNumbers(
                    Parser.parseStrToIntegerList(serverTask)));
            }
        });
        task.start();
        return task;
    }
}
