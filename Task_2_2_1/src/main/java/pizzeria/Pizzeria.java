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
 * 
 */
public class Pizzeria {

    private Gson gson;    
    private int storehouseCapacity;
    private int workMins;

    private List<Worker> workers;
    private ArrayList<Thread> workerThreads;

    private BlockingDesk orderQueue; // Эти поля можно не заводить. Просто передавать класс
    private BlockingDesk storeQueue; // конфиг в фабрику и она уже будет нитям давать необходимое

    
    /**
     * Тут мы создаем пиццерию, т.е. просто запихиваем в объект конфиг данные о складе и времени
     * работы пиццерии, задаём курьеров и поваров
     *
     * @throws JsonIOException 
     * @throws JsonSyntaxException 
     * @throws InterruptedException 
     * @throws IOException 
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
        WorkerFactoryPizzeria workerFactory = new WorkerFactoryPizzeria(storeQueue, orderQueue);

        this.storehouseCapacity = config.getStorehouseCapacity();
        this.workMins = config.getWorkMins();
        this.workers = config.getAllWorkers();

        this.workerThreads = new ArrayList<Thread>();
        for (Worker worker : workers) {
            workerThreads.add(workerFactory.hireWorker(worker));
        }
    }


    /**
     * 
     * @param writeOrders
     * @param writeStore
     * @throws IOException
     * @throws InterruptedException
     */
    public void workingDay(Writer writeOrders, Writer writeStore) throws IOException, InterruptedException {
        for (Thread worker : this.workerThreads) {
            worker.start();
            System.err.println("Рабочий запущен в работу");
        }

        try {
            Thread.sleep(getWorkMins());
        } catch (InterruptedException e) {
            System.err.println("Налоговая не законно закрыла пиццерию!!!");
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
     * 
     * @param writeOrders
     * @param writeStore
     * @throws IOException
     */
    public void serializeQueues(Writer writeOrders, Writer writeStore) throws IOException {
        writeOrders.write(this.gson.toJson(orderQueue));
        writeStore.write(this.gson.toJson(storeQueue));
        writeOrders.flush();
        writeStore.flush();
    }


    /**
     * 
     * @return
     */
    public int getStorehouseCapacity() {
        return this.storehouseCapacity;
    }


    /**
     * 
     * @return
     */
    public int getWorkMins() {
        return this.workMins;
    }


    /**
     * 
     * @return
     */
    public List<Worker> getWorkers() {
        return this.workers;
    }
}
