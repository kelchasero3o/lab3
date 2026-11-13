public class DeviceFileReader {
    // Название файла, которое можно менять (п. 7)
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
}