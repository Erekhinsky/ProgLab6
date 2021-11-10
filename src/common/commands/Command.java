package common.commands;

import common.elementsOfCollection.Vehicle;
import common.exception.IncorrectValueException;
import common.ui.UserInterface;
import server.interaction.StorageInteraction;

import java.io.IOException;
import java.io.Serializable;

/**
 * Класс, от которого наследуются команды.
 */
public abstract class Command implements Serializable {

    /**
     * Строка вызова команды.
     */
    protected String cmdLine;

    /**
     * Полное описание команды.
     */
    protected String description;
    protected boolean needsObject;
    protected int argumentAmount;
    protected String argument;
    protected Vehicle object;
    protected String options;


    /**
     * Методы исполнения команды.
     *
     * @param ui       Объект для взаимодействия с пользователем.
     * @param argument Аргументы для исполнения команды.
     * @param vehicle  Объект коллекции.
     */
    public String execute(UserInterface ui, StorageInteraction storageInteraction, String argument, Vehicle vehicle) {
        return null;
    }

    public String execute(UserInterface ui, StorageInteraction storageInteraction) throws IOException {
        return null;
    }

    public String execute(UserInterface ui, String argument, StorageInteraction storageInteraction) throws IOException {
        return null;
    }

    public String execute(UserInterface ui, StorageInteraction storageInteraction, Vehicle vehicle) throws IncorrectValueException {
        return null;
    }

    public String execute(StorageInteraction storageInteraction) throws IOException {
        return null;
    }

    /**
     * Пустой конструктор Command.
     */
    public Command() {
    }

    /**
     * Возвращает строку вызова команды.
     *
     * @return Строка вызова команды.
     */
    public String getCmdLine() {
        return cmdLine;
    }

    /**
     * Возвращает описание команды.
     *
     * @return Описание команды.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Возвращает необходимые аргументы.
     *
     * @return Необходимые аргументы для команды.
     */
    public String getOptions() {
        return options;
    }


    /**
     * Возвращает необходимость в дополнительных для исполнения команды.
     *
     * @return True - нужно, false - не нужно.
     */
    public boolean getNeedsObject() {
        return needsObject;
    }

    public int getArgumentAmount() {
        return argumentAmount;
    }

    /**
     * Добавляет объект для исполнения.
     *
     * @param object Объект коллекции.
     */
    public void setObject(Vehicle object) {
        this.object = object;
    }

    /**
     * Возвращает объект для исполнения.
     *
     * @return Объект коллекции.
     */
    public Vehicle getObject() {
        return this.object;
    }

    /**
     * Добавляет аргументы для исполнения.
     *
     * @param arg Аргументы.
     */
    public void setArgument(String arg) {
        this.argument = arg;
    }

    /**
     * Возвращает аргументы для исполнения.
     *
     * @return Аргументы для исполнения.
     */
    public String getArgument() {
        return this.argument;
    }

}
