package common.ui;

import common.elementsOfCollection.*;
import common.exception.IncorrectValueException;
import server.Server;
import server.utils.ValidationClass;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Класс, реализующий взаимодействие с пользователем.
 */
public class UserInterface {

    private static final Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * Сканер.
     */
    private final Scanner scanner;

    /**
     * Куда идет запись.
     */
    private final Writer writer;

    /**
     * Режим взаимодействия.
     */
    private final boolean interaction;

    /**
     * Стандартный конструктор.
     *
     * @param reader      откуда считывать.
     * @param interaction режим взаимодействия (true - интерактивный).
     * @param writer      куда записывать.
     */
    public UserInterface(Reader reader, boolean interaction, Writer writer) {
        this.scanner = new Scanner(reader);
        this.writer = writer;
        this.interaction = interaction;
    }

    /**
     * Метод, считывающий введенную пользователем строку.
     *
     * @return введенная строка.
     */
    public String read() {
        return scanner.nextLine();
    }

    /**
     * Метод, пишущий сообщение на вывод.
     *
     * @param message сообщение.
     * @throws IOException в случае ошибки ввода/вывода.
     */
    public void write(String message) throws IOException {
        writer.write(message);
        writer.flush();
    }

    /**
     * Метод, демонстрирующий сообщение пользователю.
     *
     * @param message сообщение.
     * @throws IOException в случае ошибки ввода/вывода.
     */
    public void showMessage(String message) throws IOException {
        write(message + "\n");
    }

    /**
     * Метод, проверяющий наличие несчитанных строк.
     *
     * @return true, если они есть, иначе false.
     */
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    /**
     * Метод, принимающий от пользователя необходимый для ввода аргумент.
     *
     * @param message  сообщение для пользователя.
     * @param nullable может быть null или нет.
     * @return введенный аргумент.
     * @throws IOException в случае ошибки ввода/вывода.
     */
    public String readArgument(String message, boolean nullable) throws IOException {
        String line = null;
        if (!nullable) {
            if (interaction) {
                while (line == null) {
                    showMessage("Введите: ");
                    showMessage(message);
                    line = scanner.nextLine();
                    line = line.isEmpty() ? null : line;
                }
            } else {
                line = scanner.nextLine();
                line = line.isEmpty() ? null : line;
            }
            if (!interaction && line == null)
                throw new InvalidParameterException("Неверный параметр на вводе данных");
        } else {
            if (interaction) {
                showMessage(message);
            }
            line = scanner.nextLine();
            line = line.isEmpty() ? null : line;
        }
        return line;
    }

    /**
     * Метод, принимающий от пользователя необходимый для ввода численный и ограниченный условиями аргумент.
     *
     * @param message  сообщение для пользователя.
     * @param min      минимальное значение.
     * @param max      максимальное значение.
     * @param nullable может быть null или нет.
     * @return введенный аргумент.
     * @throws IOException в случае ошибки ввода/вывода.
     */
    public String readConditionArgument(String message, double min, double max, boolean nullable) throws IOException {
        String line = null;
        if (!nullable) {
            if (interaction) {
                while (line == null || Long.parseLong(line) < min || Long.parseLong(line) > max) {
                    showMessage("Ввод в данное поле не должно быть пустым и должен быть в диапазоне: [" + min + ":" + max + "]");
                    showMessage(message);
                    line = scanner.nextLine();
                    line = line.isEmpty() ? null : line;
                }
            } else {
                line = scanner.nextLine();
                line = line.isEmpty() ? null : line;
                if (line == null || Long.parseLong(line) < min || Long.parseLong(line) > max)
                    throw new InvalidParameterException("Неверный параметр на вводе данных");
            }
        } else {
            if (interaction) {
                do {
                    showMessage(message);
                    line = scanner.nextLine();
                    if (line.isEmpty())
                        break;
                } while (Long.parseLong(line) < min || Long.parseLong(line) > max);
                line = line.isEmpty() ? null : line;
            } else {
                line = scanner.nextLine();
                if (Long.parseLong(line) < min || Long.parseLong(line) > max)
                    throw new InvalidParameterException("Неверный параметр на вводе данных");
            }
        }
        return line;
    }

    /**
     * Метод, считывающий транспорт (объект коллекции) из строки.
     *
     * @param ui объект взаимодействия с пользователем.
     * @return объект коллекции.
     * @throws IOException в случае ошибки ввода/вывода.
     */
    public Vehicle readVehicle(UserInterface ui) throws IOException, IncorrectValueException {

        String name;
        do {
            name = readArgument("Название транспорта: ", false);
        }
        while (!ValidationClass.validateName(name, interaction, ui));

        LocalDate creationDate = LocalDate.now();

        String xLine;
        String yLine;
        do {
            xLine = readArgument("x координату:", false);
        } while (!ValidationClass.validateDouble(xLine, interaction, ui, false));
        do {
            yLine = readArgument("y координату:", false);
        } while (!ValidationClass.validateDouble(yLine, interaction, ui, false));
        double x = Double.parseDouble(xLine);
        double y = Double.parseDouble(yLine);
        Coordinates coordinates = new Coordinates(x, y);

        String enginePowerStr;
        do {
            enginePowerStr = readConditionArgument("Мощность двигателя:", 0, Double.MAX_VALUE, false);
        } while (!ValidationClass.validateLong(enginePowerStr, interaction, ui, false));
        long enginePower = Long.parseLong(enginePowerStr);

        String numberOfWheelsStr;
        do {
            numberOfWheelsStr = readConditionArgument("Количество колес:", 0, Double.MAX_VALUE, true);
        } while (!ValidationClass.validateLong(numberOfWheelsStr, interaction, ui, false));
        long numberOfWheels = Long.parseLong(numberOfWheelsStr);

        String distanceTravelledStr;
        do {
            distanceTravelledStr = readConditionArgument("Пройденную дистанцию: ", 0, Float.MAX_VALUE, false);
        } while (!ValidationClass.validateFloat(distanceTravelledStr, interaction, ui, true));
        float distanceTravelled = Float.parseFloat(distanceTravelledStr);

        FuelType fuelType;
        String fuelTypeLine;
        do {
            fuelTypeLine = readArgument("Тип топлива, выберите из этих:" + "\n" + FuelType.getPossibleValues(), false);
        } while (!ValidationClass.validateFuelType(fuelTypeLine, interaction, ui));
        fuelType = FuelType.valueOf(fuelTypeLine.toUpperCase());

        return new Vehicle(new Vehicle().generateID(), name, coordinates, creationDate, enginePower, numberOfWheels, distanceTravelled, fuelType);
    }

    public boolean isInteractionMode() {
        return interaction;
    }

    /**
     * Метод, считывающий ID из строки.
     *
     * @param ui объект взаимодействия с пользователем.
     * @return ID продукта.
     * @throws IOException в случае ошибки ввода/вывода.
     */
    public long readId(UserInterface ui) throws IOException {
        String idStr;
        do {
            idStr = readArgument("Введите id продукта: ", false);
        } while (!ValidationClass.validateLong(idStr, interaction, ui, false));
        return Integer.parseInt(idStr);
    }
}

