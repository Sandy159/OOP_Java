package carFactory.factory.stores;

import carFactory.Config;
import carFactory.factory.factory.Car;
import carFactory.factory.providers.Controller;

import javax.swing.*;
import java.util.ArrayList;

public class CarsStore {
    private final ArrayList<Car> cars;
    private int amountOfSoldCars = 0;
    private JLabel storeLabel;
    private JLabel soldLabel;
    private Controller controller;
    //Object monitor = new Object();

    public CarsStore() {
        storeLabel = null;
        soldLabel = null;
        cars = new ArrayList<>();
    }

    public void setStoreLabel(JLabel jLabel) {
        storeLabel = jLabel;
    }

    public void setSoldLabel(JLabel jLabel) {
        soldLabel = jLabel;
    }

    private void updateLabel(JLabel label, String text){
        if (label != null)
            label.setText(text);
    }

    public synchronized void giveCar(Car newCar) throws InterruptedException {
        while (isFull()) {
            wait();
           /* monitor.wait();
            monitor.notify();*/
        }
        cars.add(newCar);
        updateLabel(storeLabel, Integer.toString(cars.size()));
        notify();
        synchronized (controller){
            controller.notify();
        }
    }

    public synchronized Car getCar() throws InterruptedException {
        while (cars.isEmpty()) {
            wait();
        }
        Car detail = cars.remove(0);
        amountOfSoldCars++;
        updateLabel(storeLabel, Integer.toString(cars.size()));
        updateLabel(soldLabel, Integer.toString(amountOfSoldCars));
        notify();
        synchronized (controller){
            controller.notify();
        }
        return detail;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public synchronized int getCurrentAmount() {
        return cars.size();
    }

    public synchronized int getCountSoldCar() {
        return amountOfSoldCars;
    }

    private synchronized boolean isFull() {
        return (cars.size() == Config.StoreCarsSize);
    }
}
