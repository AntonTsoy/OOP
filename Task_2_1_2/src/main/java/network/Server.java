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


/**
 * Класс сервера.
 */
public class Server {
    private final int tcpPort;
    private final String multicastAddress;
    private final int udpPort;


    /**
     * Конструктор сервера.
     *
     * @param tcpPort соединение с клиентом
     * @param multicastAddress рассылка
     * @param udpPort броадкаст
     */
    public Server(int tcpPort, String multicastAddress, int udpPort) {
        this.tcpPort = tcpPort;
        this.multicastAddress = multicastAddress;
        this.udpPort = udpPort;
    }

    private void multicastPublish(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress group = InetAddress.getByName(this.multicastAddress);
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, this.udpPort);
            Thread.sleep(800);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Функция исполняется в основном потоке. Создает побочные потоки: 1) поиска новых подключений
     * до тех пор, пока не кончатся задачи; 2) потоки для взаимодействия с клиентами.
     *
     * @param numberChunks это задачи, которые должны решить потоки-клиенты (рабочие)
     * @return результат. Булевое значение есть ли в коллекции епростое число.
     * @throws IOException непредвиденные исключения
     * @throws InterruptedException непредвиденные исключения
     */
    public boolean hasUnsimpleNumber(List<String> numberChunks)
            throws IOException, InterruptedException {

        multicastPublish(Integer.toString(this.tcpPort));
        try (ServerSocket serverSocket = new ServerSocket(this.tcpPort)) {
            BlockingQueue<String> clientTasks = new ArrayBlockingQueue<>(numberChunks.size(),
                false, numberChunks);
            BlockingQueue<Boolean> clientAnswers = new ArrayBlockingQueue<>(numberChunks.size(),
                false);
            List<Thread> clientHandlingThreads = new ArrayList<>();
            Thread workerSearcher = takingNewConnections(serverSocket, clientTasks, clientAnswers,
                clientHandlingThreads);

            boolean resultAnswer = false;
            int expectedTaskAnswersNumber = clientTasks.size();
            for (int answerId = 0; answerId < expectedTaskAnswersNumber; answerId++) {
                if (clientAnswers.take()) {
                    resultAnswer = true;
                    break;
                }
            }
            workerSearcher.interrupt();
            stopAllThreads(clientHandlingThreads);
            return resultAnswer;
        }
    }

    private Thread takingNewConnections(
            ServerSocket serverSocket,
            BlockingQueue<String> clientTasks,
            BlockingQueue<Boolean> clientAnswers,
            List<Thread> clientHandlingThreads) {

        Thread task = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // multicastPublish(Integer.toString(this.tcpPort));
                    if (clientTasks.isEmpty()) {
                        break;
                    }
                    Socket clientSocket = serverSocket.accept();
                    clientHandlingThreads.add(clientHandling(
                        clientSocket, clientTasks, clientAnswers));
                }
            } catch (IOException e) {
                System.out.println("Server-Searcher was interrupted.");
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
            try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()))) {
                
                taskPostingLoop(clientSocket, clientTasks, clientAnswers, reader, writer);
            } catch (IOException | InterruptedException e) {
                try {
                    clientSocket.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }

        });
        task.start();
        return task;
    }

    private void taskPostingLoop(
            Socket clientSocket,
            BlockingQueue<String> clientTasks,
            BlockingQueue<Boolean> clientAnswers,
            BufferedReader reader,
            PrintWriter writer) throws InterruptedException {

        while (!Thread.currentThread().isInterrupted()) {
            String currentTask = clientTasks.take();
            writer.println(currentTask);

            String clientResult;
            try {
                clientResult = reader.readLine();
                if (clientResult == null) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                clientTasks.put(currentTask);
                System.out.println(Thread.currentThread().getId() + " server-handler back task.");
                break;
            }
            clientAnswers.put(Objects.equals(clientResult, "true"));
        }
    }

    private void stopAllThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        }
    }
}
