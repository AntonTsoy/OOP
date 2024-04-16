package pizzeria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Класс, представляющий конфигурацию пиццерии.
 */
public class PizzeriaConfig {
    @Expose
    private Chef[] chefs;
    @Expose
    private Courier[] couriers;
    @Expose
    private int storehouseCapacity;
    @Expose
    private int workMins;


    /**
     * Возвращает массив поваров.
     *
     * @return Массив поваров.
     */
    public Chef[] getChefs() {
        return this.chefs;
    }


    /**
     * Возвращает массив курьеров.
     *
     * @return Массив курьеров.
     */
    public Courier[] getCouriers() {
        return this.couriers;
    }


    /**
     * Возвращает список всех работников пиццерии (поваров и курьеров).
     *
     * @return Список всех работников пиццерии.
     */
    public List<Worker> getAllWorkers() {
        ArrayList<Worker> workers = new ArrayList<Worker>(Arrays.asList(getChefs()));
        workers.addAll(Arrays.asList(getCouriers()));
        return workers;
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
}
