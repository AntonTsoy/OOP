package pizzeria;

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



    public Chef[] getChefs() {
        return this.chefs;
    }

    public Courier[] getCouriers() {
        return this.couriers;
    }

    public int getStorehouseCapacity() {
        return this.storehouseCapacity;
    }

    public int getWorkMins() {
        return this.workMins;
    }
}
