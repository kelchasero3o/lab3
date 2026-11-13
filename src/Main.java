import java.util.Scanner;

void main() {
    Scanner scanner = new Scanner(System.in);
    DeviceManager manager = new DeviceManager();

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
                        r.setHasAntenna(readBoolean(scanner, "Наличие антенны (true/false): ")); // Исправлено
                        manager.addDevice(r);
                    }
                    case "3" -> {
                        var sp = new SatellitePhone(man, mod);
                        System.out.print("Спутниковая сеть: ");
                        sp.setSatelliteNetwork(scanner.nextLine());
                        sp.setSignalStrength(readInt(scanner, "Сила сигнала (0-100): "));
                        sp.setIsRoaming(readBoolean(scanner, "Роуминг (true/false): ")); // Исправлено
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

                // Общие свойства с валидацией
                System.out.print("Новый производитель: ");
                dev.setManufacturer(scanner.nextLine());
                System.out.print("Новая модель: ");
                dev.setModel(scanner.nextLine());

                // ИСПОЛЬЗУЕМ СОВРЕМЕННЫЙ SWITCH (Java 21+) ВМЕСТО IF-ELSE
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

                System.out.println("Свойства обновлены (если не было ошибок валидации).");
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
                        // ИСПОЛЬЗУЕМ СОВРЕМЕННЫЙ SWITCH ВМЕСТО IF-ELSE
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
            case "0" -> {
                System.out.println("Выход...");
                return;
            }
            default -> System.out.println("Неверный ввод.");
        }
    }
}

// Вспомогательный метод для безопасного ввода целого числа (защита от букв)
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

// Вспомогательный метод для безопасного ввода дробного числа
double readDouble(Scanner scanner, String prompt) {
    while (true) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim().replace(',', '.'); // Меняем запятую на точку для удобства
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите число (например, 101.5)!");
        }
    }
}

// НОВЫЙ МЕТОД: Безопасный ввод логического значения (убирает пустые catch блоки)
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