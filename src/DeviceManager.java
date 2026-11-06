import java.util.ArrayList;
import java.util.List;

public class DeviceManager {
    // Единый список для всех устройств (полиморфизм)
    private final List<CommunicationDevice> devices = new ArrayList<>();

    public void addDevice(CommunicationDevice device) {
        devices.add(device);
    }

    public boolean removeDevice(String id) {
        return devices.removeIf(d -> d.getId().equals(id) || d.getId().startsWith(id));
    }

    public CommunicationDevice findDevice(String id) {
        return devices.stream()
                .filter(d -> d.getId().equals(id) || d.getId().startsWith(id))
                .findFirst()
                .orElse(null);
    }

    public void showAllDevices() {
        if (devices.isEmpty()) {
            System.out.println("Список устройств пуст.");
            return;
        }
        for (var device : devices) {
            System.out.println(device.getDetailedInfo() + " | " + device.getStatus());
        }
    }

    public List<CommunicationDevice> getDevices() {
        return devices;
    }

    // Интересный функционал (п. 7): Широковещательная рассылка
    public void broadcastMessage(String senderId, String message) {
        var sender = findDevice(senderId);
        if (sender == null) {
            System.out.println("Отправитель не найден.");
            return;
        }

        if (!sender.isPowerOn() || !sender.isConnected()) {
            System.out.println("Отправитель должен быть включен и подключен к сети!");
            return;
        }

        System.out.println("\nНАЧАЛО ШИРОКОВЕЩАТЕЛЬНОЙ РАССЫЛКИ");
        System.out.println("Отправитель: " + sender.getDetailedInfo());

        if (sender.getContacts().isEmpty()) {
            System.out.println("У отправителя нет сохраненных контактов.");
            return;
        }

        int successCount = 0;
        for (var device : devices) {
            if (device.getId().equals(sender.getId())) continue;

            if (device.isPowerOn() && device.isConnected()) {
                System.out.println("-> Доставлено на " + device.getManufacturer() + " " + device.getModel() + ": \"" + message + "\"");
                successCount++;
            } else {
                System.out.println("-> Устройство " + device.getManufacturer() + " " + device.getModel() + " недоступно.");
            }
        }
        System.out.println("РАССЫЛКА ЗАВЕРШЕНА. Доставлено: " + successCount + " ---\n");
    }
}