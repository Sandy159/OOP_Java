package carFactory.threadsPoll;

import carFactory.Config;
import carFactory.factory.providers.*;
import carFactory.factory.stores.*;

import java.util.ArrayList;

public class ThreadsPool {
    private Controller controller;
    private final AccessoriesStore accessoriesStore;
    private final BodiesStore bodiesStore;
    private final EnginesStore enginesStore;
    private final CarsStore carsStore;
    private final carFactory.Logger logger;
    private final ArrayList<Thread> commonThreadsPool;
    private final ArrayList<Thread> workersThreadPool;

    public ThreadsPool(AccessoriesStore accessoriesStore, BodiesStore bodiesStore, EnginesStore enginesStore, CarsStore carsStore, carFactory.Logger logger){
        this.accessoriesStore = accessoriesStore;
        this.bodiesStore = bodiesStore;
        this.enginesStore = enginesStore;
        this.carsStore = carsStore;
        this.logger = logger;

        commonThreadsPool = new ArrayList<>();
        workersThreadPool = new ArrayList<>();

        initController();
        initBodiesSupplier();
        initAccessoriesSuppliers();
        initEnginesSupplier();
        initWorkers();
        initDealers();
    }



    public void interruptThreads(){
        for (Thread thread: commonThreadsPool){
            thread.interrupt();
        }
    }

    private void initAccessoriesSuppliers(){
        int amount = Config.AccessoriesSuppliers;

        for (int i=0; i<amount; i++){
            AccessoriesSupplier accessoriesSupplier = new AccessoriesSupplier(accessoriesStore);
            Thread thread = new Thread(accessoriesSupplier, "accessory" + i);
            thread.start();
            commonThreadsPool.add(thread);
        }
    }

    private void initBodiesSupplier(){
        BodiesSupplier bodiesSupplier = new BodiesSupplier(bodiesStore);
        Thread thread = new Thread(bodiesSupplier, "body");
        thread.start();
        commonThreadsPool.add(thread);
    }

    private void initEnginesSupplier(){
        EnginesSupplier enginesSupplier = new EnginesSupplier(enginesStore);
        Thread thread = new Thread(enginesSupplier, "engine");
        thread.start();
        commonThreadsPool.add(thread);
    }

    private void initWorkers(){
        int amount = Config.Workers;

        for (int i=0; i<amount; i++){
            Worker worker = new Worker(bodiesStore, accessoriesStore, enginesStore, carsStore, controller);
            Thread thread = new Thread(worker, "worker" + i);
            thread.start();
            workersThreadPool.add(thread);
        }
    }

    private void initDealers(){
        int amount = Config.Dealers;

        for (int i=0; i<amount; i++){
            Dealer dealer = new Dealer(carsStore, logger, i);
            Thread thread = new Thread(dealer, "dealer" + i);
            thread.start();
            commonThreadsPool.add(thread);
        }
    }

    private void initController() {
        controller = new Controller(carsStore);
        Thread thread = new Thread(controller, "controller");
        thread.start();
        commonThreadsPool.add(thread);
        carsStore.setController(controller);
    }

    private ArrayList<Thread> getWorkersThreadPool(){
        return workersThreadPool;
    }
}