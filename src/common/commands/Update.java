package common.commands;

import common.ui.UserInterface;
import common.elementsOfCollection.Vehicle;
import common.exception.IncorrectValueException;
import server.interaction.StorageInteraction;

import java.io.IOException;

/**
 * Класс команды update.
 */
public class Update extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Update() {
        cmdLine = "update";
        description = "обновить значение элемента коллекции, id которого равен заданному";
        options = "Параметры: ID заменяемого объекта, Добавляемый объект";
        needsObject = true;
        argumentAmount = 2;
    }

    /**
     * Метод исполнения
     *
     * @param argument           необходимые для исполнения аргументы.
     * @param storageInteraction объект для взаимодействия с коллекцией.
     */
    public String execute(UserInterface userInterface, StorageInteraction storageInteraction, String argument, Vehicle vehicle) {
        long id = Long.parseLong(argument);
        if (storageInteraction.findById(id)) {
            storageInteraction.update(id, vehicle);
            return ("Транспорт обновлен");
        } else return ("Что-то пошло не так");
    }
}