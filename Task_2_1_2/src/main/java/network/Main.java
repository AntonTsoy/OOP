package network;


import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        List<Boolean> clientAnswers = new ArrayList<Boolean>();
        System.out.println(clientAnswers.stream().anyMatch(x -> x == true));
    }

    public static void fMain(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Thread " + Thread.currentThread().getId() + " is running");
                        Thread.sleep(1000); // Симуляция работы потока
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("All threads started");
    }
}
