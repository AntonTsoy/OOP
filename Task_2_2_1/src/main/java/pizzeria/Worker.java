package pizzeria;


/**
 * 
 */
public interface Worker extends Runnable {

    /**
     * 
     * @return
     */
    int getId();


    /**
     * 
     * @return
     */
    int getSpeed();


    /**
     * 
     * @return
     */
    default String getInfo() {
        return "Рабочий #" + getId();
    }
} 
