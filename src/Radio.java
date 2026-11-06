import java.util.Random;

public class Radio extends CommunicationDevice {

    public Radio(String manufacturer, String model) {
        super(manufacturer, model);
    }

    @Override
    public void turnOn() {
        this.isPowerOn = true;
        System.out.println("Радио " + manufacturer + " " + model + " включено.");
    }

    @Override
    public void turnOff() {
        this.isPowerOn = false;
        this.isConnected = false;
        System.out.println("Радио выключено.");
    }

    @Override
    public String getStatus() {
        return "Питание: " + (isPowerOn ? "Вкл" : "Выкл");
    }

    @Override
    public void connectToNetwork(String networkName) {
        if (isPowerOn) {
            this.isConnected = true;
            System.out.println("Радио поймало сигнал вышки: " + networkName);
        }
    }

    @Override
    public void sendMessage(String message) {
        if (isPowerOn && isConnected) {
            System.out.println("Передача в эфир: \"" + message + "\"");
        }
    }

    @Override
    public void randomizeProperties() {
        System.out.println("Свойства радио рандомизированы.");
    }

    @Override
    public String getDetailedInfo() {
        return String.format("Радио [ID: %s, %s %s]", id.substring(0, 8), manufacturer, model);
    }
}