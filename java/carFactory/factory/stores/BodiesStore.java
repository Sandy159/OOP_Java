package carFactory.factory.stores;

import carFactory.Config;
import carFactory.factory.factory.Body;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class BodiesStore {
    private final ArrayList<Body> bodies;
    private JLabel label;
    //Object monitor = new Object();
    public BodiesStore(){
        label = null;
        bodies = new ArrayList<>();
    }

    public void setLabel(JLabel jLabel){
        label = jLabel;
    }

    private void updateLabel(){
        if (label != null)
            label.setText(Integer.toString(bodies.size()));
    }

    public synchronized void giveBody(Body newBody) throws InterruptedException {
        while(isFull()){
            wait();
           /* monitor.wait();
            monitor.notify();*/
        }
        bodies.add(newBody);
        updateLabel();
        notify();
    }

    public synchronized Body getBody() throws InterruptedException {
        while (bodies.isEmpty()){
            wait();
        }
        Body detail = bodies.remove(0);
        updateLabel();
        notify();
        return detail;
    }

    public synchronized int getCurrentAmount(){
        return bodies.size();
    }

    private synchronized boolean isFull(){
        return (bodies.size() == Config.StoreBodySize);
    }
}
