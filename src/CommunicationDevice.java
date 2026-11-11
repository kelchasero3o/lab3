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
    protected boolean isPowerOn;

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

    // Геттеры и сеттеры с ВАЛИДАЦИЕЙ
    public String getId() { return id; }
    public String getManufacturer() { return manufacturer; }

    public void setManufacturer(String manufacturer) {
        if (manufacturer != null && !manufacturer.isBlank()) {
            this.manufacturer = manufacturer;
        } else {
            System.out.println("Ошибка: производитель не может быть пустым! Значение не изменено.");
        }
    }

    public String getModel() { return model; }

    public void setModel(String model) {
        if (model != null && !model.isBlank()) {
            this.model = model;
        } else {
            System.out.println("Ошибка: модель не может быть пустой! Значение не изменено.");
        }
    }

    public List<String> getContacts() { return contacts; }
    public boolean isConnected() { return isConnected; }
    public boolean isPowerOn() { return isPowerOn; }

    public void addContact(String contact) {
        if (contact != null && !contact.isBlank() && !contacts.contains(contact)) {
            contacts.add(contact);
        }
    }
}