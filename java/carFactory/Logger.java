package carFactory;

import carFactory.factory.factory.Car;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private final File logFile;

    public Logger(){
        logFile = new File("C:\\Users\\alexa\\IdeaProjects\\lab4\\src\\main\\java\\carFactory\\history.log");
    }

    public void makeLog(Car car, int id) throws IOException {
        if(!Config.Log){
            return;
        }

        FileWriter logger = new FileWriter(logFile, true);
        logger.write("Dealer " + id + ": Auto " + car.getId() +
                "(Body:" + car.getBodyId() + " Engine:" + car.getEngineId() + " Accessory:" +
                car.getAccessoryId()+")\n");
        logger.close();
    }
}
