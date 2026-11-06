import java.util.Random;

public class SatellitePhone extends CommunicationDevice {

    public SatellitePhone(String manufacturer, String model) {
        super(manufacturer, model);
    }

    @Override
    public void turnOn() {
        this.isPowerOn = true;
        System.out.println("Спутниковый телефон " + model + " включен.");
    }

    @Override
    public void turnOff() {
        this.isPowerOn = false;
        this.isConnected = false;
        System.out.println("Спутниковый телефон выключен.");
    }

    @Override
    public String getStatus() {
        return "Питание: " + (isPowerOn ? "Вкл" : "Выкл");
    }

    @Override
    public void connectToNetwork(String networkName) {
        if (isPowerOn) {
            this.isConnected = true;
            System.out.println("Подключение к спутниковой сети: " + networkName + " установлено.");
        }
    }

    @Override
    public void sendMessage(String message) {
        if (isPowerOn && isConnected) {
            System.out.println("Отправка спутникового сообщения (SMS): \"" + message + "\"");
        }
    }

    @Override
    public void randomizeProperties() {
        System.out.println("Свойства спутникового телефона рандомизированы.");
    }

    @Override
    public String getDetailedInfo() {
        return String.format("Спутниковый телефон [ID: %s, %s %s]", id.substring(0, 8), manufacturer, model);
    }
}