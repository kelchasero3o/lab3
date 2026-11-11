import java.util.Random;

public class SatellitePhone extends CommunicationDevice {
    private String satelliteNetwork;
    private int signalStrength;
    private boolean isRoaming;

    public SatellitePhone(String manufacturer, String model) {
        super(manufacturer, model);
        this.satelliteNetwork = "Iridium";
        this.signalStrength = 75;
        this.isRoaming = true;
    }

    public void updateSatellitePosition() {
        if (isPowerOn) {
            System.out.println("Поиск спутников сети " + satelliteNetwork + "... Спутник найден.");
            setSignalStrength(signalStrength + 10); // Вызов сеттера с проверкой
        }
    }

    public void makeEmergencyCall() {
        if (isPowerOn) {
            System.out.println("ЭКСТРЕННЫЙ ВЫЗОВ через спутник " + satelliteNetwork + "! Связь установлена.");
        } else {
            System.out.println("Устройство выключено.");
        }
    }

    // Сеттеры с ВАЛИДАЦИЕЙ
    public void setSatelliteNetwork(String satelliteNetwork) {
        if (satelliteNetwork != null && !satelliteNetwork.isBlank()) {
            this.satelliteNetwork = satelliteNetwork;
        } else {
            System.out.println("Ошибка: название спутниковой сети не может быть пустым! Значение не изменено.");
        }
    }

    public void setSignalStrength(int signalStrength) {
        if (signalStrength >= 0 && signalStrength <= 100) {
            this.signalStrength = signalStrength;
        } else {
            System.out.println("Ошибка: сила сигнала должна быть от 0 до 100! Значение не изменено.");
        }
    }

    public void setIsRoaming(boolean isRoaming) {
        this.isRoaming = isRoaming;
    }

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
            setSatelliteNetwork(networkName);
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

    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        setSignalStrength(rand.nextInt(0, 101));
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