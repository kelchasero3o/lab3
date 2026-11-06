import java.util.Random;

public class Smartphone extends CommunicationDevice {
    // Уникальные свойства (п. 5)
    private int batteryLevel;
    private double screenSize;
    private String osVersion;

    public Smartphone(String manufacturer, String model) {
        super(manufacturer, model);
        this.batteryLevel = 100;
        this.screenSize = 6.5;
        this.osVersion = "Android 14";
    }

    // Уникальные методы (п. 5)
    public void installApp(String appName) {
        if (isPowerOn) System.out.println("Приложение " + appName + " установлено на " + model);
        else System.out.println("Сначала включите устройство!");
    }

    public void takePhoto() {
        if (isPowerOn && batteryLevel > 5) {
            batteryLevel -= 5;
            System.out.println("Смартфон " + model + " сделал фото. Заряд: " + batteryLevel + "%");
        } else {
            System.out.println("Недостаточно заряда или устройство выключено.");
        }
    }

    // Сеттеры для изменения уникальных свойств через меню
    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = Math.clamp(batteryLevel, 0, 100);
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    // Реализация базовых абстрактных методов
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

    // Реализация дополнительных методов (п. 4)
    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.batteryLevel = rand.nextInt(1, 101);
        this.screenSize = 5.0 + (8.0 - 5.0) * rand.nextDouble();
    }

    @Override
    public String getDetailedInfo() {
        return String.format("Смартфон [ID: %s, %s %s, Экран: %.1f\", ОС: %s]",
                id.substring(0, 8), manufacturer, model, screenSize, osVersion);
    }
}