package pizzeria;

/**
 * Фабрика работников для пиццерии. Она нанимает работников и настраивает их очереди работы.
 */
public class WorkerFactoryPizzeria {

    private BlockingDesk source;
    private BlockingDesk destination;

    /**
     * Создает новую фабрику работников с указанными очередями.
     *
     * @param source Исходная очередь.
     * @param destination Целевая очередь.
     */
    public WorkerFactoryPizzeria(BlockingDesk source, BlockingDesk destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Нанимает работника и настраивает его очереди в соответствии с его типом.
     *
     * @param worker Работник, которого нужно нанять.
     * @return Новая нить, на которой будет запущен работник.
     */
    public Thread hireWorker(Worker worker) {
        if (worker instanceof Chef chef) {
            chef.setQueues(source, destination);
        } else if (worker instanceof Courier courier) {
            courier.setStore(destination);
        } else {
            return null;
        }
        return new Thread(worker);
    }
}
