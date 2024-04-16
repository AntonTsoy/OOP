package pizzeria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;


/**
 * 
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
     * 
     * @return
     */
    public Chef[] getChefs() {
        return this.chefs;
    }


    /**
     * 
     * @return
     */
    public Courier[] getCouriers() {
        return this.couriers;
    }


    /**
     * 
     * @return
     */
    public List<Worker> getAllWorkers() {
        ArrayList<Worker> workers = new ArrayList<Worker>(Arrays.asList(getChefs()));
        workers.addAll(Arrays.asList(getCouriers()));
        return workers;
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
}
