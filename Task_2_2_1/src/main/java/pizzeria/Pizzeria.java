package pizzeria;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * Класс, представляющий работу пиццерии.
 */
public class Pizzeria {

    private Gson gson;    
    private int storehouseCapacity;
    private int workMins;

    private List<Worker> workers;
    private ArrayList<Thread> workerThreads;

    private BlockingDesk orderQueue;
    private BlockingDesk storeQueue;


    /**
     * Конструктор класса. Принимает на вход конфигурацию пиццерии и исходные данные по заказам и складу.
     * 
     * @param readConfig   Поток чтения с конфигурацией пиццерии.
     * @param readOrders   Поток чтения с исходными данными по заказам.
     * @param readStore    Поток чтения с исходными данными по складу.
     * @throws JsonSyntaxException    В случае ошибки парсинга JSON.
     * @throws JsonIOException       В случае ошибки ввода-вывода при работе с JSON.
     * @throws InterruptedException  В случае прерывания потока.
     * @throws IOException            В случае ошибки ввода-вывода.
     */
    public Pizzeria(Reader readConfig, Reader readOrders, Reader readStore) 
        throws JsonSyntaxException, JsonIOException, InterruptedException, IOException
    {
        this.gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();
        PizzeriaConfig config = gson.fromJson(readConfig, PizzeriaConfig.class);
        this.orderQueue = gson.fromJson(readOrders, BlockingDesk.class);
        this.storeQueue = gson.fromJson(readStore, BlockingDesk.class);
        System.out.println(this.orderQueue);
        System.out.println(this.storeQueue);
        WorkerFactoryPizzeria workerFactory = new WorkerFactoryPizzeria(orderQueue, storeQueue);

        this.storehouseCapacity = config.getStorehouseCapacity();
        this.workMins = config.getWorkMins();
        this.workers = config.getAllWorkers();

        this.workerThreads = new ArrayList<Thread>();
        for (Worker worker : this.workers) {
            workerThreads.add(workerFactory.hireWorker(worker));
        }
    }

    /**
     * Запускает рабочий день пиццерии.
     * 
     * @param writeOrders Поток записи данных о заказах.
     * @param writeStore  Поток записи данных о складе.
     * @throws IOException        В случае ошибки ввода-вывода.
     * @throws InterruptedException  В случае прерывания потока.
     */
    public void workingDay(Writer writeOrders, Writer writeStore) throws IOException, InterruptedException {
        for (Thread worker : this.workerThreads) {
            worker.start();
        }

        try {
            Thread.sleep(getWorkMins());
        } catch (InterruptedException e) {
            System.out.println("Налоговая не законно закрыла пиццерию!!!");
            throw new RuntimeException();
        }

        for (Thread worker : this.workerThreads) {
            worker.interrupt();
        }
        for (Thread worker : this.workerThreads) {
            worker.join();
        }

        serializeQueues(writeOrders, writeStore);
    }

    /**
     * Сериализует данные о заказах и складе.
     * 
     * @param writeOrders Поток записи данных о заказах.
     * @param writeStore  Поток записи данных о складе.
     * @throws IOException  В случае ошибки ввода-вывода.
     */
    public void serializeQueues(Writer writeOrders, Writer writeStore) throws IOException {
        writeOrders.write(this.gson.toJson(orderQueue));
        writeStore.write(this.gson.toJson(storeQueue));
        writeOrders.flush();
        writeStore.flush();
    }

    /**
     * Возвращает вместимость склада.
     * 
     * @return Вместимость склада.
     */
    public int getStorehouseCapacity() {
        return this.storehouseCapacity;
    }

    /**
     * Возвращает продолжительность рабочего дня.
     * 
     * @return Продолжительность рабочего дня.
     */
    public int getWorkMins() {
        return this.workMins;
    }

    /**
     * Возвращает список работников пиццерии.
     * 
     * @return Список работников пиццерии.
     */
    public List<Worker> getWorkers() {
        return this.workers;
    }
}
