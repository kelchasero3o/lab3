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
                parseAndCreateDevice(currentProps, loadedDevices);
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        System.out.println("\n--- СТАТИСТИКА ЧТЕНИЯ ---");
        System.out.println("Успешно создано объектов: " + objectsFound);
        System.out.println("Успешно прочитано свойств: " + fieldsRead);
        System.out.println("Потеряно/не найдено свойств: " + fieldsMissing);

        return loadedDevices;
    }

    private void parseAndCreateDevice(Map<String, String> props, List<CommunicationDevice> targetList) {
        String type = props.get("Type");
        if (type == null) {
            System.out.println("Предупреждение: пропущен блок без указания Type.");
            return;
        }

        String man = props.getOrDefault("Manufacturer", "Unknown");
        String mod = props.getOrDefault("Model", "Unknown");

        CommunicationDevice device = null;

        if (type.equalsIgnoreCase("Smartphone")) {
            Smartphone sm = new Smartphone(man, mod);
            fieldsRead += 2;

            if (props.containsKey("BatteryLevel")) { sm.setBatteryLevel(Integer.parseInt(props.get("BatteryLevel"))); fieldsRead++; } else fieldsMissing++;
            if (props.containsKey("ScreenSize")) { sm.setScreenSize(Double.parseDouble(props.get("ScreenSize"))); fieldsRead++; } else fieldsMissing++;
            if (props.containsKey("OsVersion")) { sm.setOsVersion(props.get("OsVersion")); fieldsRead++; } else fieldsMissing++;

            device = sm;
        }
        else if (type.equalsIgnoreCase("Radio")) {
            Radio r = new Radio(man, mod);
            fieldsRead += 2;

            if (props.containsKey("Frequency")) { r.setFrequency(Double.parseDouble(props.get("Frequency"))); fieldsRead++; } else fieldsMissing++;
            if (props.containsKey("VolumeLevel")) { r.setVolumeLevel(Integer.parseInt(props.get("VolumeLevel"))); fieldsRead++; } else fieldsMissing++;
            if (props.containsKey("HasAntenna")) { r.setHasAntenna(Boolean.parseBoolean(props.get("HasAntenna"))); fieldsRead++; } else fieldsMissing++;

            device = r;
        }
        else if (type.equalsIgnoreCase("SatellitePhone")) {
            SatellitePhone sp = new SatellitePhone(man, mod);
            fieldsRead += 2;

            if (props.containsKey("SatelliteNetwork")) { sp.setSatelliteNetwork(props.get("SatelliteNetwork")); fieldsRead++; } else fieldsMissing++;
            if (props.containsKey("SignalStrength")) { sp.setSignalStrength(Integer.parseInt(props.get("SignalStrength"))); fieldsRead++; } else fieldsMissing++;
            if (props.containsKey("IsRoaming")) { sp.setIsRoaming(Boolean.parseBoolean(props.get("IsRoaming"))); fieldsRead++; } else fieldsMissing++;

            device = sp;
        }

        if (device != null) {
            targetList.add(device);
            objectsFound++;
        }
    }
}