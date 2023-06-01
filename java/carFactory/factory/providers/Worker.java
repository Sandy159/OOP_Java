package carFactory.factory.providers;

import carFactory.factory.factory.*;
import carFactory.factory.stores.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Worker extends Thread implements Runnable{
    private final BodiesStore bodiesStore;
    private final AccessoriesStore accessoriesStore;
    private final EnginesStore enginesStore;
    private final CarsStore carsStore;
    private final TaskQueue taskQueue;
    private static final AtomicInteger curId = new AtomicInteger(0);

    public Worker(BodiesStore bodiesStore, AccessoriesStore accessoriesStore, EnginesStore enginesStore, CarsStore carsStore, Controller controller) {
        this.bodiesStore = bodiesStore;
        this.accessoriesStore = accessoriesStore;
        this.enginesStore =enginesStore;
        this.carsStore = carsStore;

        taskQueue = controller.getTaskQueue();
    }

    @Override
    public synchronized void run() {
        while(true){
            Accessory accessory;
            Body body;
            Engine engine;
            try {
                accessory = accessoriesStore.getAccessory();
                body = bodiesStore.getBody();
                engine = enginesStore.getEngine();

                if (taskQueue.getSize() > 0){
                    carsStore.giveCar(new Car(curId.getAndIncrement(), accessory, body, engine));
                    taskQueue.getTask();
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted: " + Thread.currentThread().getName());
                return;
            }
        }
    }

    public int getAmountOfReadyCars(){
        return curId.get();
    }
}