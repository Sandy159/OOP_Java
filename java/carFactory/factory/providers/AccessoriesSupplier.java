package carFactory.factory.providers;

import carFactory.Config;
import carFactory.factory.factory.Accessory;
import carFactory.factory.stores.AccessoriesStore;

import java.util.concurrent.atomic.AtomicInteger;

public class AccessoriesSupplier extends Thread implements Runnable{
    private final AccessoriesStore accessoriesStore;
    private static final AtomicInteger id = new AtomicInteger(0);
    private static int time;

    public AccessoriesSupplier(AccessoriesStore accessoriesStore){
        this.accessoriesStore = accessoriesStore;
        time = Config.Time;
    }

    public static void changeTime(int newTime){
        time = newTime;
    }

    @Override
    public synchronized void run() {
        while(true){
            try {
                sleep(time);
                accessoriesStore.giveAccessory(new Accessory(id.getAndIncrement()));
            } catch (InterruptedException e) {
                System.err.println("Interrupted: " + Thread.currentThread().getName());
                return;
            }
        }
    }

    public int getAmountOfReadyAccessories(){
        return id.get();
    }
}
