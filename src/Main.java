import java.util.Scanner;

void main() {
    Scanner scanner = new Scanner(System.in);
    DeviceManager manager = new DeviceManager();

    // [ЛАБА 4] Инициализация ридера с дефолтным именем файла
    DeviceFileReader fileReader = new DeviceFileReader("devices.txt");

    var phone1 = new Smartphone("Samsung", "Galaxy S24");
    phone1.addContact("Иван");
    manager.addDevice(phone1);
    manager.addDevice(new Radio("Sony", "CFD-S70"));
    manager.addDevice(new SatellitePhone("Iridium", "Extreme 9575"));

    while (true) {
        System.out.println("\nМЕНЮ: КОММУНИКАЦИОННЫЕ УСТРОЙСТВА");
        System.out.println("1. Добавить устройство");
        System.out.println("2. Удалить устройство");
        System.out.println("3. Изменить свойства устройства");
        System.out.println("4. Функциональная работа");
        System.out.println("5. Показать все устройства");
        System.out.println("6. Рандомизировать свойства");
        System.out.println("7. [Бонус] Широковещательная рассылка (Broadcast)");
        // [ЛАБА 4] Новые пункты меню
        System.out.println("8. Прочитать устройства из файла (" + fileReader.getFilePath() + ")");
        System.out.println("9. Изменить имя файла для чтения");

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
                    case "1" -> {
                        var sm = new Smartphone(man, mod);
                        sm.setBatteryLevel(readInt(scanner, "Заряд батареи (0-100): "));
                        sm.setScreenSize(readDouble(scanner, "Размер экрана (дюймы): "));
                        System.out.print("Версия ОС: ");
                        sm.setOsVersion(scanner.nextLine());
                        manager.addDevice(sm);
                    }
                    case "2" -> {
                        var r = new Radio(man, mod);
                        r.setFrequency(readDouble(scanner, "Частота (87.5-108.0 МГц): "));
                        r.setVolumeLevel(readInt(scanner, "Громкость (0-100): "));
                        r.setHasAntenna(readBoolean(scanner, "Наличие антенны (true/false): "));
                        manager.addDevice(r);
                    }
                    case "3" -> {
                        var sp = new SatellitePhone(man, mod);
                        System.out.print("Спутниковая сеть: ");
                        sp.setSatelliteNetwork(scanner.nextLine());
                        sp.setSignalStrength(readInt(scanner, "Сила сигнала (0-100): "));
                        sp.setIsRoaming(readBoolean(scanner, "Роуминг (true/false): "));
                        manager.addDevice(sp);
                    }
                    default -> System.out.println("Неверный тип.");
                }
                System.out.println("Устройство добавлено.");
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

                System.out.print("Новый производитель: ");
                dev.setManufacturer(scanner.nextLine());
                System.out.print("Новая модель: ");
                dev.setModel(scanner.nextLine());

                switch (dev) {
                    case Smartphone sm -> {
                        sm.setBatteryLevel(readInt(scanner, "Новый заряд (0-100): "));
                        sm.setScreenSize(readDouble(scanner, "Новый размер экрана: "));
                        System.out.print("Новая ОС: ");
                        sm.setOsVersion(scanner.nextLine());
                    }
                    case Radio r -> {
                        r.setFrequency(readDouble(scanner, "Новая частота (87.5-108.0): "));
                        r.setVolumeLevel(readInt(scanner, "Новая громкость (0-100): "));
                        r.setHasAntenna(readBoolean(scanner, "Есть антенна? (true/false): "));
                    }
                    case SatellitePhone sp -> {
                        System.out.print("Новая спутниковая сеть: ");
                        sp.setSatelliteNetwork(scanner.nextLine());
                        sp.setSignalStrength(readInt(scanner, "Новая сила сигнала (0-100): "));
                        sp.setIsRoaming(readBoolean(scanner, "Роуминг? (true/false): "));
                    }
                    default -> {}
                }
                System.out.println("Свойства обновлены.");
            }
            case "4" -> {
                manager.showAllDevices();
                System.out.print("ID устройства: ");
                var dev = manager.findDevice(scanner.nextLine());
                if (dev == null) { System.out.println("Не найдено."); break; }

                System.out.print("1-Вкл, 2-Выкл, 3-В сеть, 4-Сообщение, 5-Специфичные действия: ");
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
                    case "5" -> {
                        switch (dev) {
                            case Smartphone sm -> {
                                System.out.print("Введите название приложения: ");
                                sm.installApp(scanner.nextLine());
                                sm.takePhoto();
                            }
                            case Radio r -> {
                                r.tuneFrequency(readDouble(scanner, "Введите новую частоту для настройки: "));
                                r.adjustVolume(readInt(scanner, "Введите изменение громкости (например, 10 или -10): "));
                            }
                            case SatellitePhone sp -> {
                                sp.updateSatellitePosition();
                                sp.makeEmergencyCall();
                            }
                            default -> {}
                        }
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
            // [ЛАБА 4] Чтение из файла
            case "8" -> {
                System.out.println("Чтение из файла: " + fileReader.getFilePath());
                var loadedDevices = fileReader.readDevices();
                if (!loadedDevices.isEmpty()) {
                    // Добавляем прочитанные объекты в основной менеджер (п. 4)
                    loadedDevices.forEach(manager::addDevice);
                    System.out.println("Устройства добавлены в общий список.");
                } else {
                    System.out.println("Не удалось прочитать устройства (файл пуст или не найден).");
                }
            }
            // [ЛАБА 4] Изменение имени файла
            case "9" -> {
                System.out.print("Введите новое имя файла (например, new_devices.txt): ");
                fileReader.setFilePath(scanner.nextLine());
                System.out.println("Имя файла изменено на: " + fileReader.getFilePath());
            }
            case "0" -> {
                System.out.println("Выход...");
                return;
            }
            default -> System.out.println("Неверный ввод.");
        }
    }
}

// Вспомогательные методы для ввода
int readInt(Scanner scanner, String prompt) {
    while (true) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите целое число!");
        }
    }
}

double readDouble(Scanner scanner, String prompt) {
    while (true) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim().replace(',', '.');
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите число (например, 101.5)!");
        }
    }
}

boolean readBoolean(Scanner scanner, String prompt) {
    while (true) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("true") || input.equals("да") || input.equals("1")) {
            return true;
        } else if (input.equals("false") || input.equals("нет") || input.equals("0")) {
            return false;
        } else {
            System.out.println("Ошибка: введите true/false (или да/нет).");
        }
    }
}