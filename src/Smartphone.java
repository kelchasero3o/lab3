import java.util.Random;

public class Smartphone extends CommunicationDevice {
    private int batteryLevel;
    private double screenSize;
    private String osVersion;

    public Smartphone(String manufacturer, String model) {
        super(manufacturer, model);
        this.batteryLevel = 100;
        this.screenSize = 6.5;
        this.osVersion = "Android 14";
    }

    public void installApp(String appName) {
        if (isPowerOn) System.out.println("Приложение " + appName + " установлено на " + model);
        else System.out.println("Сначала включите устройство!");
    }

    public void takePhoto() {
        if (isPowerOn && batteryLevel > 5) {
            setBatteryLevel(batteryLevel - 5); // Используем сеттер для списания заряда
            System.out.println("Смартфон " + model + " сделал фото. Заряд: " + batteryLevel + "%");
        } else {
            System.out.println("Недостаточно заряда или устройство выключено.");
        }
    }

    // Сеттеры с ВАЛИДАЦИЕЙ
    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel >= 0 && batteryLevel <= 100) {
            this.batteryLevel = batteryLevel;
        } else {
            System.out.println("Ошибка: заряд должен быть от 0 до 100! Значение не изменено.");
        }
    }

    public void setScreenSize(double screenSize) {
        if (screenSize > 0 && screenSize <= 15.0) {
            this.screenSize = screenSize;
        } else {
            System.out.println("Ошибка: размер экрана должен быть от 1 до 15 дюймов! Значение не изменено.");
        }
    }

    public void setOsVersion(String osVersion) {
        if (osVersion != null && !osVersion.isBlank()) {
            this.osVersion = osVersion;
        } else {
            System.out.println("Ошибка: версия ОС не может быть пустой! Значение не изменено.");
        }
    }

    @Override
    public void turnOn() {
        this.isPowerOn = true;
        System.out.println("Смартфон " + model + " включен. Загрузка ОС " + osVersion);
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
                " | Сеть: " + (isConnected ? "Подключено" : "Нет") +
                " | Заряд: " + batteryLevel + "%";
    }

    @Override
    public void connectToNetwork(String networkName) {
        if (isPowerOn) {
            this.isConnected = true;
            System.out.println("Смартфон подключен к сети: " + networkName);
        } else {
            System.out.println("Ошибка: Смартфон выключен.");
        }
    }

    @Override
    public void sendMessage(String message) {
        if (isPowerOn && isConnected) {
            if (contacts.isEmpty()) {
                System.out.println("Нет контактов для отправки.");
            } else {
                System.out.println("Отправка SMS: \"" + message + "\" контактам: " + contacts);
            }
        } else {
            System.out.println("Ошибка: Нет сети или устройство выключено.");
        }
    }

    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        setBatteryLevel(rand.nextInt(0, 101));
        setScreenSize(5.0 + (8.0 - 5.0) * rand.nextDouble());
    }

    @Override
    public String getDetailedInfo() {
        return String.format("Смартфон [ID: %s, %s %s, Экран: %.1f\", ОС: %s]",
                id.substring(0, 8), manufacturer, model, screenSize, osVersion);
    }
}