import java.util.Random;

public class Radio extends CommunicationDevice {
    private double frequency;
    private int volumeLevel;
    private boolean hasAntenna;

    public Radio(String manufacturer, String model) {
        super(manufacturer, model);
        this.frequency = 101.5;
        this.volumeLevel = 50;
        this.hasAntenna = true;
    }

    public void tuneFrequency(double newFrequency) {
        if (isPowerOn) {
            setFrequency(newFrequency); // Вызов сеттера с проверкой
            System.out.println("Радио настроено на волну: " + frequency + " МГц");
        } else {
            System.out.println("Радио выключено.");
        }
    }

    public void adjustVolume(int delta) {
        if (isPowerOn) {
            setVolumeLevel(volumeLevel + delta); // Вызов сеттера с проверкой
            System.out.println("Громкость: " + volumeLevel + "%");
        }
    }

    // Сеттеры с ВАЛИДАЦИЕЙ
    public void setFrequency(double frequency) {
        if (frequency >= 87.5 && frequency <= 108.0) {
            this.frequency = frequency;
        } else {
            System.out.println("Ошибка: частота должна быть в диапазоне 87.5 - 108.0 МГц! Значение не изменено.");
        }
    }

    public void setVolumeLevel(int volumeLevel) {
        if (volumeLevel >= 0 && volumeLevel <= 100) {
            this.volumeLevel = volumeLevel;
        } else {
            System.out.println("Ошибка: громкость должна быть от 0 до 100! Значение не изменено.");
        }
    }

    public void setHasAntenna(boolean hasAntenna) {
        this.hasAntenna = hasAntenna;
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
        } else {
            System.out.println("Радио выключено.");
        }
    }

    @Override
    public void sendMessage(String message) {
        if (isPowerOn && isConnected) {
            System.out.println("Передача в эфир на частоте " + frequency + " МГц: \"" + message + "\"");
        } else {
            System.out.println("Невозможно передать сообщение. Проверьте питание и антенну.");
        }
    }

    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        setFrequency(87.5 + (108.0 - 87.5) * rand.nextDouble());
        setVolumeLevel(rand.nextInt(0, 101));
        this.hasAntenna = rand.nextBoolean();
    }

    @Override
    public String getDetailedInfo() {
        return String.format("Радио [ID: %s, %s %s, Антенна: %s]",
                id.substring(0, 8), manufacturer, model, hasAntenna ? "Есть" : "Нет");
    }
}