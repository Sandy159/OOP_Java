package carFactory.factory.stores;

import carFactory.Config;
import carFactory.factory.factory.Engine;

import javax.swing.*;
import java.util.ArrayList;

public class EnginesStore {
    private final ArrayList<Engine> engines;
    private JLabel label;
    //Object monitor = new Object();
    public EnginesStore(){
        label = null;
        engines = new ArrayList<>();
    }

    public void setLabel(JLabel jLabel){
        label = jLabel;
    }

    private void updateLabel(){
        if (label != null)
            label.setText(Integer.toString(engines.size()));
    }

    public synchronized void giveEngine(Engine newEngine) throws InterruptedException {
        while(isFull()){
            wait();
           /* monitor.wait();
            monitor.notify();*/
        }
        engines.add(newEngine);
        updateLabel();
        notify();
    }

    public synchronized Engine getEngine() throws InterruptedException {
        while (engines.isEmpty()){
            wait();
        }
        Engine detail = engines.remove(0);
        updateLabel();
        notify();
        return detail;
    }

    public synchronized int getCurrentAmount(){
        return engines.size();
    }

    private synchronized boolean isFull(){
        return (engines.size() == Config.StoreEnginesSize);
    }
}