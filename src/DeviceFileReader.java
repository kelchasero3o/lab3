import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceFileReader {
    private String filePath;

    // Статистика поиска (п. 5)
    private int objectsFound = 0;
    private int fieldsRead = 0;
    private int fieldsMissing = 0;

    public DeviceFileReader(String filePath) {
        this.filePath = filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public List<CommunicationDevice> readDevices() {
        List<CommunicationDevice> loadedDevices = new ArrayList<>();

        objectsFound = 0;
        fieldsRead = 0;
        fieldsMissing = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            Map<String, String> currentProps = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    if (!currentProps.isEmpty()) {
                        // [COMMIT 3] Вызываем метод создания объекта
                        parseAndCreateDevice(currentProps, loadedDevices);
                        currentProps.clear();
                    }
                    continue;
                }

                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim().replaceAll(",$", "");
                    currentProps.put(key, value);
                }
            }

            if (!currentProps.isEmpty()) {
                // [COMMIT 3] Вызываем метод создания объекта
                parseAndCreateDevice(currentProps, loadedDevices);
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return loadedDevices;
    }

    // [COMMIT 3] Вспомогательный метод для создания объекта на основе собранных свойств
    private void parseAndCreateDevice(Map<String, String> props, List<CommunicationDevice> targetList) {
        String type = props.get("Type");
        if (type == null) {
            System.out.println("Предупреждение: пропущен блок без указания Type.");
            return;
        }

        String man = props.getOrDefault("Manufacturer", "Unknown");
        String mod = props.getOrDefault("Model", "Unknown");

        CommunicationDevice device = null;

        // Обрабатываем пока только Смартфоны
        if (type.equalsIgnoreCase("Smartphone")) {
            Smartphone sm = new Smartphone(man, mod);
            fieldsRead += 2; // Manufacturer и Model

            if (props.containsKey("BatteryLevel")) { sm.setBatteryLevel(Integer.parseInt(props.get("BatteryLevel"))); fieldsRead++; } else fieldsMissing++;
            if (props.containsKey("ScreenSize")) { sm.setScreenSize(Double.parseDouble(props.get("ScreenSize"))); fieldsRead++; } else fieldsMissing++;
            if (props.containsKey("OsVersion")) { sm.setOsVersion(props.get("OsVersion")); fieldsRead++; } else fieldsMissing++;

            device = sm;
        }

        if (device != null) {
            targetList.add(device);
            objectsFound++;
        }
    }
}