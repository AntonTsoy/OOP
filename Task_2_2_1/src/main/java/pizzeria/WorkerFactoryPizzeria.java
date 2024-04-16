package pizzeria;

public class WorkerFactoryPizzeria {

    private BlockingDesk source;
    private BlockingDesk destination;


    public WorkerFactoryPizzeria(BlockingDesk source, BlockingDesk destination) {
        this.source = source;
        this.destination = destination;
    }


    public Thread hireWorker(Worker worker) {
        switch (worker.getClass().getName()) {
            case "pizzeria.Chef":
                ((Chef)worker).setQueues(source, destination);
                break;
            case "pizzeria.Courier":
                ((Courier)worker).setStore(destination);
                break;
        }
        return new Thread(worker);
    }
}
