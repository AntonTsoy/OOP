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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


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


    public boolean hasUnsimpleNumber(List<String> numberChunks) 
        throws IOException, InterruptedException {

        multicastPublish(Integer.toString(this.tcpPort));
        ServerSocket serverSocket = new ServerSocket(this.tcpPort);
        ArrayBlockingQueue<String> clientTasks = new ArrayBlockingQueue<>(numberChunks.size(),
            false, numberChunks);
        List<Boolean> clientAnswers = new ArrayList<Boolean>();
        List<Thread> clientHandlingThreads = new ArrayList<Thread>();
        Thread workerSearcher = takingNewConnections(serverSocket, clientTasks, clientAnswers, clientHandlingThreads);

        int expectedTaskAnswersNumber = clientTasks.size();
        while (expectedTaskAnswersNumber != 0) {
            synchronized (clientAnswers) {
                clientAnswers.wait();
                if (clientAnswers.stream().anyMatch(x -> x == true)) {
                    break;
                } else {
                    expectedTaskAnswersNumber -= clientAnswers.size();
                    clientAnswers.clear();
                }
            }
        }
        workerSearcher.interrupt();
        serverSocket.close();

        stopAllThreads(clientHandlingThreads);
        return clientAnswers.stream().anyMatch(x -> x == true);
    }


    private Thread takingNewConnections(
        ServerSocket serverSocket, 
        BlockingQueue<String> clientTasks,
        List<Boolean> clientAnswers,
        List<Thread> clientHandlingThreads) {

        Thread task = new Thread(() -> {
            while (true) {
                if (clientTasks.isEmpty()) {
                    return;
                    // Не хочу делать холостой цикл. Я хотел придумать, что-то умное.
                    // Чтобы клиенты выполняли задачи и параллельно работал поиск новых клиентов.
                    // Если все задачи уже разобраны, то поиск приостанавливается.
                    // Если задачи есть, то поиск либо продолжается, либо возобнавляется.
                }
                try {
                    Socket clientSocket = serverSocket.accept();
                    clientHandlingThreads.add(clientHandling(clientSocket, clientTasks, 
                        clientAnswers));
                } catch (IOException e) {
                    System.err.println("Searcher of new clients was interrupted.");
                    return;
                }

                if (Thread.interrupted()) {
                    return;
                }
            }
        });
        task.start();
        return task;
    }


    private Thread clientHandling(
        Socket clientSocket, 
        BlockingQueue<String> clientTasks,
        List<Boolean> clientAnswers) {

        Thread task = new Thread(() -> {
            PrintWriter writer;
            BufferedReader reader;
            try {
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
                reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                return;
            }

            while (true) {
                if (Thread.interrupted()) {
                    return;
                }

                String currentTask;
                try {
                    currentTask = clientTasks.take();
                } catch (InterruptedException e) {
                    return;
                }

                writer.println(currentTask);
                String clientResult;
                try {
                    clientResult = reader.readLine();
                } catch (IOException e) {  // Я надеюсь что эта хрень возникает при разрыве соединение и болье возникать нигде не будет.
                    System.err.println("Thread: " + Thread.currentThread().getId() + " lost the connection.");
                    try {
                        clientTasks.put(currentTask);
                        return;
                    } catch (InterruptedException err) {
                        err.printStackTrace();
                        return;
                    }
                }

                boolean resultAnswer = Objects.equals(clientResult, "true");
                synchronized (clientAnswers) {
                    clientAnswers.add(resultAnswer);
                    clientAnswers.notify();
                }
            }
        });
        task.start();
        return task;
    }


    private void stopAllThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        }
    }
}
