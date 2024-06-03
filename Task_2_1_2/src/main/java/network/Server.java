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
    private final int tcpPort;
    private final String multicastAddress;
    private final int udpPort;


    public Server(int tcpPort, String multicastAddress, int udpPort) {
        this.tcpPort = tcpPort;
        this.multicastAddress = multicastAddress;
        this.udpPort = udpPort;
    }


    private void multicastPublish(String message) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName(this.multicastAddress);
            byte[] buf = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, this.udpPort);
            Thread.sleep(1000);
            socket.send(packet);
            socket.close();
            System.out.println(Thread.currentThread().getId() + " server close datagram socket;");
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
        ArrayBlockingQueue<Boolean> clientAnswers = new ArrayBlockingQueue<>(numberChunks.size(),
            false);
        List<Thread> clientHandlingThreads = new ArrayList<Thread>();
        Thread workerSearcher = takingNewConnections(serverSocket, clientTasks, clientAnswers, clientHandlingThreads);

        Boolean resultAnswer = false;
        int expectedTaskAnswersNumber = clientTasks.size();
        for (int answerId = 0; answerId < expectedTaskAnswersNumber; answerId++) {
            if (clientAnswers.take()) {
                resultAnswer = true;
                break;
            }
        }
        workerSearcher.interrupt();
        serverSocket.close();
        stopAllThreads(clientHandlingThreads);
        return resultAnswer;
    }


    private Thread takingNewConnections(
        ServerSocket serverSocket, 
        BlockingQueue<String> clientTasks,
        BlockingQueue<Boolean> clientAnswers,
        List<Thread> clientHandlingThreads) {

        Thread task = new Thread(() -> {
            while (true) {
                if (clientTasks.isEmpty()) {
                    System.out.println(Thread.currentThread().getId() + " server-searcher finishing.");
                    return;
                    // Не хочу делать холостой цикл. Я хотел придумать, что-то умное.
                    // Чтобы клиенты выполняли задачи и параллельно работал поиск новых клиентов.
                    // Если все задачи уже разобраны, то поиск приостанавливается.
                    // Если задачи есть, то поиск либо продолжается, либо возобнавляется.
                }
                try {
                    if (Thread.interrupted()) {
                        System.out.println(Thread.currentThread().getId() + " server-searcher finishing.");
                        return;
                    }
                    Thread.sleep(5);  // Это нужно, чтобы только что подключившийся клиент мог успеть взять задачу до того, как будет подключен следующий воркер.
                    Socket clientSocket = serverSocket.accept();
                    System.out.println(Thread.currentThread().getId() + " server accepted new connect;");
                    clientHandlingThreads.add(clientHandling(clientSocket, clientTasks, 
                        clientAnswers));
                } catch (InterruptedException | IOException e) {
                    System.out.println(Thread.currentThread().getId() + " searcher of new clients was interrupted.");
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
        BlockingQueue<Boolean> clientAnswers) {

        Thread task = new Thread(() -> {
            PrintWriter writer;
            BufferedReader reader;
            try {
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
                reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                System.out.println(Thread.currentThread().getId() + " server-handler finishing.");
                try {
                    clientSocket.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
                return;
            }

            while (true) {
                String currentTask;
                try {
                    if (Thread.interrupted()) {
                        System.out.println(Thread.currentThread().getId() + " server-handler finishing.");
                        try {
                            clientSocket.close();
                        } catch (IOException err) {
                            err.printStackTrace();
                        }
                        return;
                    }
                    currentTask = clientTasks.take();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getId() + " server-handler finishing.");
                    try {
                        clientSocket.close();
                    } catch (IOException err) {
                        err.printStackTrace();
                    }
                    return;
                }

                writer.println(currentTask);
                String clientResult;
                try {
                    clientResult = reader.readLine();
                } catch (IOException e) {  // Я надеюсь что эта ерунда возникает при разрыве соединение и больше возникать нигде не будет.
                    System.out.println("Server-thread: " + Thread.currentThread().getId() + " lost the connection.");
                    try {
                        clientTasks.put(currentTask);
                        System.out.println(Thread.currentThread().getId() + " server-handler finishing.");
                        try {
                            clientSocket.close();
                        } catch (IOException err) {
                            err.printStackTrace();
                        }
                        return;
                    } catch (InterruptedException err) {  // Эта ситуация может произойти, если пришел ответ о нахождении непростого числа от какого-то другого клиента, пока этот поток пытался вернуть задачу от умершего клиента.
                        err.printStackTrace();
                        System.out.println(Thread.currentThread().getId() + " server-handler finishing.");
                        try {
                            clientSocket.close();
                        } catch (IOException error) {
                            error.printStackTrace();
                        }
                        return;
                    }
                }

                System.out.println(Thread.currentThread().getId() + " server received a client answer;");
                try {
                    clientAnswers.put(Objects.equals(clientResult, "true"));
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getId() + " server-handler finishing.");
                    try {
                        clientSocket.close();
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                    return;
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
