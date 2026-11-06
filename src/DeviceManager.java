import java.util.ArrayList;
import java.util.List;

public class DeviceManager {
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
}