import java.util.Random;

public class Smartphone extends CommunicationDevice {

    public Smartphone(String manufacturer, String model) {
        super(manufacturer, model);
    }

    @Override
    public void turnOn() {
        this.isPowerOn = true;
        System.out.println("Смартфон " + model + " включен.");
    }

    @Override
    public void turnOff() {
        this.isPowerOn = false;
        this.isConnected = false;
        System.out.println("Смартфон " + model + " выключен.");
    }

    @Override
    public String getStatus() {
        return "Питание: " + (isPowerOn ? "Вкл" : "Выкл") +
                " | Сеть: " + (isConnected ? "Подключено" : "Нет");
    }

    @Override
    public void connectToNetwork(String networkName) {
        if (isPowerOn) {
            this.isConnected = true;
            System.out.println("Смартфон подключен к сети: " + networkName);
        }
    }

    @Override
    public void sendMessage(String message) {
        if (isPowerOn && isConnected) {
            System.out.println("Отправка SMS: \"" + message + "\"");
        }
    }

    @Override
    public void randomizeProperties() {
        // Заглушка до добавления уникальных свойств
        System.out.println("Свойства смартфона рандомизированы.");
    }

    @Override
    public String getDetailedInfo() {
        return String.format("Смартфон [ID: %s, %s %s]", id.substring(0, 8), manufacturer, model);
    }
}