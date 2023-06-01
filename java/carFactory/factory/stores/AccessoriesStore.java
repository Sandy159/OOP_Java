package carFactory.factory.stores;

import carFactory.Config;
import carFactory.factory.factory.Accessory;

import javax.swing.*;
import java.util.ArrayList;

public class AccessoriesStore {
    private final ArrayList<Accessory> accessories;
    private JLabel label;
    //Object monitor = new Object();
    public AccessoriesStore(){
        label = null;
        accessories = new ArrayList<>();
    }

    public void setLabel(JLabel jLabel){
        label = jLabel;
    }

    private void updateLabel(){
        if (label != null)
            label.setText(Integer.toString(accessories.size()));
    }

    public synchronized void giveAccessory(Accessory newAccessory) throws InterruptedException {
        while(isFull()){
            wait();
           /* monitor.wait();
            monitor.notify();*/
        }
        accessories.add(newAccessory);
        updateLabel();
        notify();
    }

    public synchronized Accessory getAccessory() throws InterruptedException {
        while (accessories.isEmpty()){
            wait();
        }
        Accessory detail = accessories.remove(0);
        updateLabel();
        notify();
        return detail;
    }

    public synchronized int getCurrentAmount(){
        return accessories.size();
    }

    private synchronized boolean isFull(){
        return (accessories.size() == Config.StoreAccessoriesSize);
    }
}
