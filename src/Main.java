import java.util.Scanner;

void main() {
    Scanner scanner = new Scanner(System.in);
    DeviceManager manager = new DeviceManager();

    // Предзаполнение списка для удобства тестирования
    var phone1 = new Smartphone("Samsung", "Galaxy S24");
    phone1.addContact("Иван");
    manager.addDevice(phone1);
    manager.addDevice(new Radio("Sony", "CFD-S70"));
    manager.addDevice(new SatellitePhone("Iridium", "Extreme 9575"));

    while (true) {
        System.out.println("\n=== МЕНЮ: КОММУНИКАЦИОННЫЕ УСТРОЙСТВА ===");
        System.out.println("1. Добавить устройство");
        System.out.println("2. Удалить устройство");
        System.out.println("3. Изменить свойства устройства");
        System.out.println("4. Функциональная работа");
        System.out.println("5. Показать все устройства");
        System.out.println("6. Рандомизировать свойства");
        System.out.println("7. [Бонус] Широковещательная рассылка (Broadcast)");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> {
                System.out.print("Тип (1-Смартфон, 2-Радио, 3-Спутниковый): ");
                String type = scanner.nextLine();
                System.out.print("Производитель: ");
                String man = scanner.nextLine();
                System.out.print("Модель: ");
                String mod = scanner.nextLine();
                switch (type) {
                    case "1" -> manager.addDevice(new Smartphone(man, mod));
                    case "2" -> manager.addDevice(new Radio(man, mod));
                    case "3" -> manager.addDevice(new SatellitePhone(man, mod));
                    default -> System.out.println("Неверный тип.");
                }
            }
            case "2" -> {
                manager.showAllDevices();
                System.out.print("ID для удаления: ");
                if (manager.removeDevice(scanner.nextLine())) System.out.println("Удалено.");
                else System.out.println("Не найдено.");
            }
            case "3" -> {
                manager.showAllDevices();
                System.out.print("Введите ID устройства для изменения: ");
                var dev = manager.findDevice(scanner.nextLine());

                if (dev == null) {
                    System.out.println("Устройство не найдено.");
                    break;
                }

                // Общие свойства (из абстрактного класса)
                System.out.print("Новый производитель: ");
                dev.setManufacturer(scanner.nextLine());
                System.out.print("Новая модель: ");
                dev.setModel(scanner.nextLine());

                // Изменение уникальных свойств в зависимости от типа (полиморфизм и instanceof)
                if (dev instanceof Smartphone sm) {
                    System.out.print("Новый заряд (0-100): ");
                    try { sm.setBatteryLevel(Integer.parseInt(scanner.nextLine())); } catch (Exception e) {}
                    System.out.print("Новая ОС: ");
                    sm.setOsVersion(scanner.nextLine());
                } else if (dev instanceof Radio r) {
                    System.out.print("Новая частота: ");
                    try { r.setFrequency(Double.parseDouble(scanner.nextLine())); } catch (Exception e) {}
                    System.out.print("Есть антенна? (true/false): ");
                    try { r.setHasAntenna(Boolean.parseBoolean(scanner.nextLine())); } catch (Exception e) {}
                } else if (dev instanceof SatellitePhone sp) {
                    System.out.print("Новая спутниковая сеть: ");
                    sp.setSatelliteNetwork(scanner.nextLine());
                    System.out.print("Новая сила сигнала (0-100): ");
                    try { sp.setSignalStrength(Integer.parseInt(scanner.nextLine())); } catch (Exception e) {}
                }

                System.out.println("Свойства обновлены.");
            }
            case "4" -> {
                manager.showAllDevices();
                System.out.print("ID устройства: ");
                var dev = manager.findDevice(scanner.nextLine());
                if (dev == null) { System.out.println("Не найдено."); break; }

                System.out.print("1-Вкл, 2-Выкл, 3-В сеть, 4-Сообщение: ");
                String sub = scanner.nextLine();
                switch (sub) {
                    case "1" -> dev.turnOn();
                    case "2" -> dev.turnOff();
                    case "3" -> {
                        System.out.print("Имя сети: ");
                        dev.connectToNetwork(scanner.nextLine());
                    }
                    case "4" -> {
                        System.out.print("Текст: ");
                        dev.sendMessage(scanner.nextLine());
                    }
                    default -> System.out.println("Неверный выбор.");
                }
            }
            case "5" -> manager.showAllDevices();
            case "6" -> {
                manager.getDevices().forEach(CommunicationDevice::randomizeProperties);
                System.out.println("Свойства рандомизированы!");
            }
            case "7" -> {
                manager.showAllDevices();
                System.out.print("ID отправителя: ");
                String senderId = scanner.nextLine();
                System.out.print("Текст рассылки: ");
                String msg = scanner.nextLine();
                manager.broadcastMessage(senderId, msg);
            }
            case "0" -> {
                System.out.println("Выход...");
                return;
            }
            default -> System.out.println("Неверный ввод.");
        }
    }
}