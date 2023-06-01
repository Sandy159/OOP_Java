package carFactory.factory.providers;

import carFactory.Config;
import carFactory.factory.stores.CarsStore;

public class Controller implements Runnable {
    private final CarsStore carsStore;
    private final TaskQueue taskQueue;
    private final int storeSize = Config.StoreCarsSize;

    public Controller(CarsStore carsStore) {
        this.carsStore = carsStore;
        taskQueue = new TaskQueue();
    }

    public synchronized TaskQueue getTaskQueue() {
        return taskQueue;
    }

    @Override
    public void run() {
        int taskId = 0;
        while (true) {
            if (carsStore.getCurrentAmount() + taskQueue.getSize() < storeSize) {
                for (int i = 0; i < storeSize; i++) {
                    taskQueue.addTask(taskId);
                    taskId++;
                }
            }
            synchronized (this){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
