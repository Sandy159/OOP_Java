package carFactory.factory.providers;

import static java.lang.Thread.sleep;

import carFactory.Config;
import carFactory.factory.factory.Body;
import carFactory.factory.stores.*;

public class BodiesSupplier extends Thread implements Runnable{
    private final BodiesStore bodiesStore;
    private int id = 0;
    private static int time;

    public BodiesSupplier(BodiesStore bodiesStore){
        this.bodiesStore = bodiesStore;
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
                bodiesStore.giveBody(new Body(id));
                id++;
            } catch (InterruptedException e) {
                System.err.println("Interrupted: " + Thread.currentThread().getName());
                return;
            }
        }
    }

    public int getAmountOfReadyBodies(){
        return id;
    }
}
