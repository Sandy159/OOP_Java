package carFactory.factory.providers;

import carFactory.Config;
import carFactory.factory.factory.Engine;
import carFactory.factory.stores.EnginesStore;

import static java.lang.Thread.sleep;

public class EnginesSupplier implements Runnable{
    private final EnginesStore enginesStore;
    private int id = 0;
    private static int time;

    public EnginesSupplier(EnginesStore enginesStore){
        this.enginesStore = enginesStore;
        time = Config.Time;
    }

    public static void changeTime(int newTime){
        time = newTime;
    }

    @Override
    public void run() {
        while(true){
            try {
                sleep(time);
                enginesStore.giveEngine(new Engine(id));
                id++;
            } catch (InterruptedException e) {
                System.err.println("Interrupted: " + Thread.currentThread().getName());
                return;
            }
        }
    }

    public int getAmountOfReadyEngines(){
        return id;
    }
}
