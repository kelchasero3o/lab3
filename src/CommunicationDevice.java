import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class CommunicationDevice {
    protected final String id;

    // Свойства из задания (п. 22.2)
    protected String manufacturer;
    protected String model;
    protected List<String> contacts;
    protected boolean isConnected;

    public CommunicationDevice(String manufacturer, String model) {
        this.id = UUID.randomUUID().toString();
        this.manufacturer = manufacturer;
        this.model = model;
        this.contacts = new ArrayList<>();
        this.isConnected = false;
    }

    // Методы из задания (п. 22.1)
    public abstract void turnOn();
    public abstract void turnOff();
    public abstract String getStatus();
    public abstract void connectToNetwork(String networkName);
    public abstract void sendMessage(String message);

    // Геттеры
    public String getId() { return id; }
    public String getManufacturer() { return manufacturer; }
    public String getModel() { return model; }
    public List<String> getContacts() { return contacts; }
    public boolean isConnected() { return isConnected; }
}