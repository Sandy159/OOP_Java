package carFactory.view;

import carFactory.factory.stores.*;

public class ViewController {
    private final AccessoriesStore accessoriesStore;
    private final BodiesStore bodiesStore;
    private final EnginesStore enginesStore;
    private final CarsStore carsStore;

    public ViewController(AccessoriesStore accessoriesStore, BodiesStore bodiesStore, EnginesStore enginesStore, CarsStore carsStore) {
        this.accessoriesStore =accessoriesStore;
        this.bodiesStore = bodiesStore;
        this.enginesStore = enginesStore;
        this.carsStore = carsStore;
    }

    public int getAccessoryStorageSize() { return accessoriesStore.getCurrentAmount(); }

    public int getCarsStorageSize(){
        return carsStore.getCurrentAmount();
    }

    public int getBodyStorageSize() { return bodiesStore.getCurrentAmount(); }

    public int getEngineStorageSize(){
        return enginesStore.getCurrentAmount();
    }

    public int getCountCars() { return carsStore.getCountSoldCar(); }

    public AccessoriesStore getAccessoryStorage(){
        return accessoriesStore;
    }

    public CarsStore getAutoStorage(){
        return carsStore;
    }

    public BodiesStore getBodyStorage(){
        return bodiesStore;
    }

    public EnginesStore getMotorStorage(){
        return enginesStore;
    }
}
