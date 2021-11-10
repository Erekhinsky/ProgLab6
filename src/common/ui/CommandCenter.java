package common.ui;

import common.commands.Command;
import common.commands.*;
import common.elementsOfCollection.Vehicle;
import common.exception.IncorrectValueException;
import server.Server;
import server.interaction.StorageInteraction;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Класс управления командами.
 */
public class CommandCenter {

    /**
     * Логер.
     */
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * Список команд.
     */
    public final HashMap<String, Command> commands = new HashMap<>();
    /**
     * Объект управления командами.
     */
    public static CommandCenter commandCenter = new CommandCenter();

    /**
     * Конструктор управления командами, где добавляются все команды.
     */
    public CommandCenter() {
        addCmd(new Help());
        addCmd(new Info());
        addCmd(new Show());
        addCmd(new Update());
        addCmd(new Clear());
        addCmd(new AddIfMax());
        addCmd(new ExecuteScript());
        addCmd(new Exit());
        addCmd(new CountLessThanFuelType());
        addCmd(new RemoveLower());
        addCmd(new Add());
        addCmd(new FilterLessThanEnginePower());
        addCmd(new RemoveFirst());
        addCmd(new RemoveById());
        addCmd(new PrintFieldDescendingDistanceTravelled());
        addCmd(new Save());
    }

    /**
     * Метод добавления команды в список команд.
     *
     * @param command Команда.
     */
    public void addCmd(Command command) {
        commands.put(command.getCmdLine(), command);
    }

    /**
     * Метод, распознающий команду в строке, введенной пользователем.
     *
     * @param cmdLine Строка, содержащая команду.
     * @return Объект класса соответствующей команды.
     */
    public Command getCmdCommand(String cmdLine) {
        return commands.getOrDefault(cmdLine, null);
    }

    /**
     * Метод, возвращающий единственный объект класса. Реализация шаблона "Синглтон".
     *
     * @return Объект центра управления командами.
     */
    public static CommandCenter getInstance() {
        if (commandCenter == null)
            return new CommandCenter();
        return commandCenter;
    }

    /**
     * Метод, возвращающий список команд.
     *
     * @return Список команд.
     */
    public List<Command> receiveAllCommands() {
        return commands.keySet().stream().map(commands::get).collect(Collectors.toList());
    }


    /**
     * Методы исполнения команды.
     *
     * @param ui       Объект взаимодействия с пользователем.
     * @param cmd      Строка пользовательского ввода, содержащая команду.
     * @param argument Аргументы для исполнения команды.
     * @param vehicle  Объект коллекции.
     * @return Вывод результата выполнения команды.
     */
    public String executeCommand(UserInterface ui, Command cmd, String argument, StorageInteraction storageInteraction, Vehicle vehicle) {
        logger.log(Level.INFO, "Executing user command with two arguments");
        return cmd.execute(ui, storageInteraction, argument, vehicle);
    }

    public String executeCommand(UserInterface ui, String line, StorageInteraction storageInteraction) throws IOException {
        logger.log(Level.INFO, "Executing server command initiated by user's actions" + "\n");
        Command cmd = getCmdCommand(line);
        return cmd.execute(ui, storageInteraction);
    }

    public String executeCommand(UserInterface ui, Command cmd, StorageInteraction storageInteraction) throws IOException {
        logger.log(Level.INFO, "Executing user command with no arguments" + "\n");
        return cmd.execute(ui, storageInteraction);
    }

    public String executeCommand(UserInterface ui, Command cmd, String argument, StorageInteraction storageInteraction) throws IOException {
        logger.log(Level.INFO, "Executing user command with a string argument" + "\n");
        return cmd.execute(ui, argument, storageInteraction);
    }

    public String executeCommand(UserInterface ui, Command cmd, StorageInteraction storageInteraction, Vehicle vehicle) throws IncorrectValueException {
        logger.log(Level.INFO, "Executing user command with an object argument" + "\n");
        return cmd.execute(ui, storageInteraction, vehicle);
    }

    public void executeServerCommand(Command cmd, StorageInteraction storageInteraction) throws IOException {
        logger.log(Level.INFO, "Executing server command" + "\n");
        cmd.execute(storageInteraction);
    }
}
