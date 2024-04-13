package pizzeria;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    private final List<Chef> chefs;
    private final List<Courier> couriers;
    private List<Thread> workers;

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

        this.storehouseCapacity = config.getStorehouseCapacity();
        this.workMins = config.getWorkMins();

        this.orderQueue = gson.fromJson(readOrders, BlockingDesk.class);
        this.storeQueue = gson.fromJson(readStore, BlockingDesk.class);

        this.chefs = new ArrayList<Chef>(Arrays.asList(config.getChefs()));
        this.couriers = new ArrayList<Courier>(Arrays.asList(config.getCouriers()));
        this.workers = new ArrayList<Thread>();
        for (Chef chef : chefs) {
            chef.setQueues(orderQueue, storeQueue);
            this.workers.add(new Thread(chef));
        }
        System.err.println(couriers);
        for (Courier courier : couriers) {
            courier.setStore(storeQueue);
            this.workers.add(new Thread(courier));
            System.err.println(courier + " был добавлен в штат");
        }

    }

    public void workingDay(Writer writeOrders, Writer writeStore) throws IOException, InterruptedException {
        for (Thread worker : this.workers) {
            worker.start();
            System.err.println("Рабочий запущен в работу");
        }

        try {
            Thread.sleep(getWorkMins());
        } catch (InterruptedException e) {
            System.err.println("Налоговая не законно закрыла пиццерию!!!");
            throw new RuntimeException();
        }

        for (Thread worker : this.workers) {
            worker.interrupt();
        }
        for (Thread worker: this.workers) {
            worker.join();
        }

        serializeQueues(writeOrders, writeStore);
    }

    public void serializeQueues(Writer writeOrders, Writer writeStore) throws IOException {
        writeOrders.write(this.gson.toJson(orderQueue));
        writeOrders.flush();

        writeStore.write(this.gson.toJson(storeQueue));
        writeStore.flush();
    }

   
    public int getStorehouseCapacity() {
        return storehouseCapacity;
    }

    public int getWorkMins() {
        return workMins;
    }

    public List<Chef> getChefs() {
        return chefs;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public BlockingDesk getOrderQueue() {
        return orderQueue;
    }

    public BlockingDesk getStoreQueue() {
        return storeQueue;
    }
}
