package carFactory.view;

import carFactory.Config;
import carFactory.factory.providers.*;
import carFactory.threadsPoll.ThreadsPool;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class View extends JFrame implements Runnable{
    private final ViewController controller;
    private final ThreadsPool threadsPool;
    private final JPanel[] storagePanel;
    private final JPanel[] soldPanel;
    private final JPanel[] sliders;
    private final JPanel[] titles;


    public View(ViewController controller, ThreadsPool threadsPool){
        this.controller = controller;
        this.threadsPool = threadsPool;

        storagePanel = new JPanel[4];
        soldPanel = new JPanel[4];
        sliders = new JPanel[4];
        titles = new JPanel[4];

        addListener();
    }

    private void addListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                threadsPool.interruptThreads();
                super.windowClosing(e);
            }
        });
    }

    private void init(){
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("CarFactory");
        GridLayout layout = new GridLayout(8, 2);
        setLayout(layout);
        setVisible(true);
    }

    private void initPanels(){
        for (int i = 0; i < 4; i++) {
            storagePanel[i] = new JPanel();
            soldPanel[i] = new JPanel();
        }

        String accessorySize = Integer.toString(controller.getAccessoryStorageSize());
        JLabel accessoryLabel = new JLabel(accessorySize);
        controller.getAccessoryStorage().setLabel(accessoryLabel);
        storagePanel[0].add(accessoryLabel);

        String bodySize = Integer.toString(controller.getBodyStorageSize());
        JLabel bodyLabel = new JLabel(bodySize);
        controller.getBodyStorage().setLabel(bodyLabel);
        storagePanel[1].add(bodyLabel);

        String motorSize = Integer.toString(controller.getEngineStorageSize());
        JLabel motorLabel = new JLabel(motorSize);
        controller.getMotorStorage().setLabel(motorLabel);
        storagePanel[2].add(motorLabel);

        String autoSize = Integer.toString(controller.getCarsStorageSize());
        JLabel autoLabel = new JLabel(autoSize);
        controller.getAutoStorage().setStoreLabel(autoLabel);
        storagePanel[3].add(autoLabel);

        for (int i=0; i<3; i++){
            soldPanel[i].add(new Label(""));
        }
        String countCar = Integer.toString(controller.getCountCars());
        JLabel countCarLabel = new JLabel(countCar);
        controller.getAutoStorage().setSoldLabel(countCarLabel);
        soldPanel[3].add(countCarLabel);
    }

    private void addAll(){
        for (int i = 0; i < 2; i++){
            add(titles[2*i]);
            add(titles[2*i + 1]);
            add(storagePanel[2*i]);
            add(storagePanel[2*i + 1]);
            add(soldPanel[2*i]);
            add(soldPanel[2*i + 1]);
            add(sliders[2*i]);
            add(sliders[2*i + 1]);
        }
    }

    private JSlider createSlider(){
        JSlider slider = new JSlider(0, 2* Config.Time, Config.Time);
        slider.setMinorTickSpacing(250);
        slider.setMajorTickSpacing(500);

        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    private void initSliders(){
        for (int i = 0; i < 4; i++)
            sliders[i] = new JPanel();

        JSlider accessoriesProvidersSlider = createSlider();
        //accessoriesProvidersSlider.setOrientation(SwingConstants.VERTICAL);
        accessoriesProvidersSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()){
                    int time = source.getValue();
                    AccessoriesSupplier.changeTime(time);
                }
            }
        });
        sliders[0].add(accessoriesProvidersSlider);

        JSlider bodyProviderSlider = createSlider();
        //bodyProviderSlider.setOrientation(SwingConstants.VERTICAL);
        bodyProviderSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()){
                    int time = source.getValue();
                    BodiesSupplier.changeTime(time);
                }
            }
        });
        sliders[1].add(bodyProviderSlider);

        JSlider motorProviderSlider = createSlider();
        //motorProviderSlider.setOrientation(SwingConstants.VERTICAL);
        motorProviderSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()){
                    int time = source.getValue();
                    EnginesSupplier.changeTime(time);
                }
            }
        });
        sliders[2].add(motorProviderSlider);

        JSlider dealerSlider = createSlider();
        //dealerSlider.setOrientation(SwingConstants.VERTICAL);
        dealerSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()){
                    int time = source.getValue();
                    Dealer.changeTime(time);
                }
            }
        });
        sliders[3].add(dealerSlider);
    }

    @Override
    public void run() {
        init();
        initPanels();
        initSliders();
        initTitles();
        addAll();
    }

    private void initTitles(){
        for (int i = 0; i < 4; i++)
            titles[i] = new JPanel();
        titles[0].add(new JLabel("Accessory"));
        titles[1].add(new JLabel("Body"));
        titles[2].add(new JLabel("Engine"));
        titles[3].add(new JLabel("Car"));
    }
}
