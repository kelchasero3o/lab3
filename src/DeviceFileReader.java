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

    // [COMMIT 2] Главный метод чтения и парсинга файла
    public List<CommunicationDevice> readDevices() {
        List<CommunicationDevice> loadedDevices = new ArrayList<>();

        // Сбрасываем статистику перед новым чтением
        objectsFound = 0;
        fieldsRead = 0;
        fieldsMissing = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Используем Map для сбора свойств одного объекта перед его созданием
            Map<String, String> currentProps = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();

                // Пропускаем пустые строки (они служат разделителями между устройствами)
                if (line.isEmpty()) {
                    if (!currentProps.isEmpty()) {
                        // TODO: Вызвать метод создания объекта
                        currentProps.clear();
                    }
                    continue;
                }

                // Разделяем строку по двоеточию (формат FieldName: FieldValue,)
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    // Убираем запятую в конце значения, если она есть
                    String value = parts[1].trim().replaceAll(",$", "");
                    currentProps.put(key, value);
                }
            }

            // Обрабатываем последний объект, если файл не заканчивается пустой строкой
            if (!currentProps.isEmpty()) {
                // TODO: Вызвать метод создания объекта
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return loadedDevices;
    }
}