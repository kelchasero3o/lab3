import java.util.Random;

public class Radio extends CommunicationDevice {
    // Уникальные свойства (п. 5)
    private double frequency;
    private int volumeLevel;
    private boolean hasAntenna;

    public Radio(String manufacturer, String model) {
        super(manufacturer, model);
        this.frequency = 101.5;
        this.volumeLevel = 50;
        this.hasAntenna = true;
    }

    // Уникальные методы (п. 5)
    public void tuneFrequency(double newFrequency) {
        if (isPowerOn) {
            this.frequency = newFrequency;
            System.out.println("Радио настроено на волну: " + newFrequency + " МГц");
        }
    }

    public void adjustVolume(int delta) {
        if (isPowerOn) {
            this.volumeLevel = Math.clamp(volumeLevel + delta, 0, 100);
            System.out.println("Громкость: " + volumeLevel + "%");
        }
    }

    @Override
    public void turnOn() {
        this.isPowerOn = true;
        System.out.println("Радио " + manufacturer + " " + model + " включено. Шипение эфира...");
    }

    @Override
    public void turnOff() {
        this.isPowerOn = false;
        this.isConnected = false;
        System.out.println("Радио выключено.");
    }

    @Override
    public String getStatus() {
        return "Питание: " + (isPowerOn ? "Вкл" : "Выкл") +
                " | Частота: " + frequency + " МГц | Громкость: " + volumeLevel + "%";
    }

    @Override
    public void connectToNetwork(String networkName) {
        if (isPowerOn && hasAntenna) {
            this.isConnected = true;
            System.out.println("Радио поймало сигнал вышки: " + networkName);
        } else if (!hasAntenna) {
            System.out.println("Нет антенны! Сигнал не ловится.");
        }
    }

    @Override
    public void sendMessage(String message) {
        if (isPowerOn && isConnected) {
            System.out.println("Передача в эфир на частоте " + frequency + " МГц: \"" + message + "\"");
        }
    }

    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.frequency = 87.5 + (108.0 - 87.5) * rand.nextDouble();
        this.volumeLevel = rand.nextInt(0, 101);
        this.hasAntenna = rand.nextBoolean();
    }

    @Override
    public String getDetailedInfo() {
        return String.format("Радио [ID: %s, %s %s, Антенна: %s]",
                id.substring(0, 8), manufacturer, model, hasAntenna ? "Есть" : "Нет");
    }
}