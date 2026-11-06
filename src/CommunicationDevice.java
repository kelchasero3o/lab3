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
    protected boolean isPowerOn; // Добавлено

    public CommunicationDevice(String manufacturer, String model) {
        this.id = UUID.randomUUID().toString();
        this.manufacturer = manufacturer;
        this.model = model;
        this.contacts = new ArrayList<>();
        this.isConnected = false;
        this.isPowerOn = false;
    }

    // Методы из задания (п. 22.1)
    public abstract void turnOn();
    public abstract void turnOff();
    public abstract String getStatus();
    public abstract void connectToNetwork(String networkName);
    public abstract void sendMessage(String message);

    // Дополнительный функционал (п. 4)
    public abstract String getDetailedInfo();
    public abstract void randomizeProperties();

    // Геттеры и сеттеры
    public String getId() { return id; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public List<String> getContacts() { return contacts; }
    public boolean isConnected() { return isConnected; }
    public boolean isPowerOn() { return isPowerOn; }

    public void addContact(String contact) {
        if (!contacts.contains(contact)) {
            contacts.add(contact);
        }
    }
}