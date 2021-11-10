package common.commands;

import common.elementsOfCollection.Vehicle;
import common.exception.IncorrectValueException;
import common.ui.CommandCenter;
import common.ui.ScriptReader;
import common.ui.UserInterface;
import server.interaction.StorageInteraction;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Класс команды execute_script.
 */
public class ExecuteScript extends Command {

    private static final HashSet<String> paths = new HashSet<>();

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public ExecuteScript() {
        cmdLine = "execute_script";
        description = "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
        options = "Параметры: Путь к исполняемому файлу";
        needsObject = false;
        argumentAmount = 1;
    }

    /**
     * Метод исполнения
     *
     * @param ui        объект, через который ведется взаимодействие с пользователем.
     * @param arguments необходимые для исполнения аргументы.
     */
    public String execute(UserInterface ui, String arguments, StorageInteraction storageInteraction) throws IOException {

        UserInterface scriptInteraction = new UserInterface(new FileReader(arguments), false, new OutputStreamWriter(System.out));

//        ScriptReader.scriptRead(arguments);

        Path p = Paths.get(arguments);
        boolean exists = Files.exists(p);
        boolean isDirectory = Files.isDirectory(p);
        boolean isFile = Files.isRegularFile(p);
        try {
            if (exists && !isDirectory && isFile) {
                paths.add(arguments);
                String line;
                while (scriptInteraction.hasNextLine()) {
                    line = scriptInteraction.read();
                    String cmdLine = line.split(" ")[0];
                    String cmdArgument;
                    try {
                        cmdArgument = line.split(" ")[1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        cmdArgument = null;
                    }
                    Command cmd = CommandCenter.getInstance().getCmdCommand(cmdLine);
                    if (cmd.getArgumentAmount() == 0) {
                        CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, storageInteraction);
                    } else {
                        if (cmd.getArgumentAmount() == 1 && cmd.getNeedsObject()) {
                            Vehicle vehicle = scriptInteraction.readVehicle(scriptInteraction);
                            CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, storageInteraction, vehicle);
                        }
                        if (cmd.getArgumentAmount() == 1 && !cmd.getNeedsObject()) {
                            if (cmd.getCmdLine().equals("execute_script")) {
                                if (!paths.contains(arguments)) {
                                    paths.add(arguments);
                                    CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, cmdArgument, storageInteraction);
                                } else {
                                    paths.clear();
                                    throw new InvalidAlgorithmParameterException("Выполнение скрипта остановлено, т.к. возможна рекурсия");
                                }
                            }
                            CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, cmdArgument, storageInteraction);
                        }
                        if (cmd.getArgumentAmount() == 2 && cmd.getNeedsObject()) {
                            Vehicle vehicle = scriptInteraction.readVehicle(scriptInteraction);
                            CommandCenter.getInstance().executeCommand(scriptInteraction, cmd, cmdArgument, storageInteraction, vehicle);
                        }
                    }
                }
                paths.clear();
                return ("Скрипт выполнен");
            } else return "Скрипт не выполнен, что-то не так с файлом.";
        } catch (InvalidParameterException e) {
            paths.clear();
            return ("Неверный скрипт");
        } catch (FileNotFoundException e) {
            paths.clear();
            return ("В качестве аргумента указан путь к несуществующему файлу");
        } catch (NoSuchElementException e) {
            paths.clear();
            return ("Скрипт некорректен, проверьте верность введенных команд");
        } catch (InvalidAlgorithmParameterException e) {
            return ("Выполнение скрипта остановлено, т.к. возможна рекурсия");
        } catch (IncorrectValueException e) {
            e.printStackTrace();
        }
        return null;
    }
}
