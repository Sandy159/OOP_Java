package carFactory.factory.factory;

public class Car {
    private final int id;
    private final Engine engine;
    private final Accessory accessory;
    private final Body body;

    public Car(int id, Accessory accessory, Body body, Engine engine){
        this.id = id;
        this.accessory = accessory;
        this.body = body;
        this.engine = engine;
    }

    public int getId(){
        return id;
    }

    public int getAccessoryId(){
        return accessory.getId();
    }

    public int getBodyId(){
        return body.getId();
    }

    public int getEngineId(){
        return engine.getId();
    }
}
