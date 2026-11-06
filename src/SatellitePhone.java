import java.util.Random;

public class SatellitePhone extends CommunicationDevice {
    // Уникальные свойства (п. 5)
    private String satelliteNetwork;
    private int signalStrength;
    private boolean isRoaming;

    public SatellitePhone(String manufacturer, String model) {
        super(manufacturer, model);
        this.satelliteNetwork = "Iridium";
        this.signalStrength = 75;
        this.isRoaming = true;
    }

    // Уникальные методы (п. 5)
    public void updateSatellitePosition() {
        if (isPowerOn) {
            System.out.println("Поиск спутников сети " + satelliteNetwork + "... Спутник найден.");
            this.signalStrength = Math.min(100, signalStrength + 10);
        }
    }

    public void makeEmergencyCall() {
        if (isPowerOn) {
            System.out.println("ЭКСТРЕННЫЙ ВЫЗОВ через спутник " + satelliteNetwork + "! Связь установлена.");
        } else {
            System.out.println("Устройство выключено.");
        }
    }

    // Сеттеры для изменения уникальных свойств через меню
    public void setSatelliteNetwork(String satelliteNetwork) {
        this.satelliteNetwork = satelliteNetwork;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = Math.clamp(signalStrength, 0, 100);
    }

    public void setIsRoaming(boolean isRoaming) {
        this.isRoaming = isRoaming;
    }

    // Реализация базовых абстрактных методов
    @Override
    public void turnOn() {
        this.isPowerOn = true;
        System.out.println("Спутниковый телефон " + model + " включен. Холодный старт...");
    }

    @Override
    public void turnOff() {
        this.isPowerOn = false;
        this.isConnected = false;
        System.out.println("Спутниковый телефон выключен.");
    }

    @Override
    public String getStatus() {
        return "Питание: " + (isPowerOn ? "Вкл" : "Выкл") +
                " | Спутник: " + satelliteNetwork + " | Сигнал: " + signalStrength + "%";
    }

    @Override
    public void connectToNetwork(String networkName) {
        if (isPowerOn && signalStrength > 20) {
            this.isConnected = true;
            this.satelliteNetwork = networkName;
            System.out.println("Подключение к спутниковой сети: " + networkName + " установлено.");
        } else {
            System.out.println("Нет сигнала спутника (слабый уровень).");
        }
    }

    @Override
    public void sendMessage(String message) {
        if (isPowerOn && isConnected) {
            System.out.println("Отправка спутникового сообщения (SMS): \"" + message + "\"");
        } else {
            System.out.println("Нет соединения со спутником для отправки.");
        }
    }

    // Реализация дополнительных методов (п. 4)
    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.signalStrength = rand.nextInt(0, 101);
        this.isRoaming = rand.nextBoolean();
        String[] networks = {"Iridium", "Globalstar", "Inmarsat", "Thuraya"};
        this.satelliteNetwork = networks[rand.nextInt(networks.length)];
    }

    @Override
    public String getDetailedInfo() {
        return String.format("Спутниковый телефон [ID: %s, %s %s, Сеть: %s, Роуминг: %s]",
                id.substring(0, 8), manufacturer, model, satelliteNetwork, isRoaming ? "Да" : "Нет");
    }
}