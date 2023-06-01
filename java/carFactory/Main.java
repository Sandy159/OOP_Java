package carFactory;

import carFactory.factory.providers.Controller;
import carFactory.factory.stores.AccessoriesStore;
import carFactory.factory.stores.BodiesStore;
import carFactory.factory.stores.CarsStore;
import carFactory.factory.stores.EnginesStore;
import carFactory.threadsPoll.ThreadsPool;
import carFactory.view.View;
import carFactory.view.ViewController;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] argc) throws InterruptedException {
        AccessoriesStore accessoriesStore = new AccessoriesStore();
        BodiesStore bodiesStore = new BodiesStore();
        EnginesStore enginesStore = new EnginesStore();
        CarsStore carsStore = new CarsStore();

        Logger logger = new Logger();
        ThreadsPool threadsPool = new ThreadsPool(accessoriesStore, bodiesStore, enginesStore, carsStore, logger);
        ViewController viewController = new ViewController(accessoriesStore, bodiesStore, enginesStore, carsStore);
        View view = new View(viewController, threadsPool);
        javax.swing.SwingUtilities.invokeLater(view);
    }
}
