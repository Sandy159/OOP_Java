package carFactory.factory.providers;

import carFactory.Config;
import carFactory.Logger;
import carFactory.factory.factory.Car;
import carFactory.factory.stores.CarsStore;

import java.io.IOException;

public class Dealer extends Thread implements Runnable{
    private final CarsStore carsStore;
    private final carFactory.Logger logger;
    private final int id;
    private static int time;

    public Dealer(CarsStore carsStore, Logger logger, int number) {
        this.carsStore = carsStore;
        this.logger = logger;
        this.id = number;

        time = Config.DealerTime;
    }

    public static void changeTime(int newTime){
        time = newTime;
    }

    @Override
    public void run() {
        Car car;
        while(true){
            try {
                sleep(time);
                car = carsStore.getCar();
                logger.makeLog(car, id);
            } catch (InterruptedException e) {
                System.err.println("Interrupted: " + Thread.currentThread().getName());
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
