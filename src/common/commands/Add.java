package common.commands;

import common.exception.IncorrectValueException;
import common.ui.UserInterface;
import common.elementsOfCollection.Vehicle;
import server.interaction.StorageInteraction;

/**
 * Класс команды add.
 */
public class Add extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Add() {
        cmdLine = "add";
        description = "добавить новый элемент с заданным ключом";
        options = "Параметры: Добавляемый объект";
        needsObject = true;
        argumentAmount = 1;
    }

    /**
     * Метод исполнения.
     *
     * @param ui                 Объект взаимодействия с пользователем.
     * @param storageInteraction Объект исполнения команды.
     * @param vehicle            Хранимый в коллекции объект.
     * @return Результат выполнения команды.
     * @throws IncorrectValueException В случае ошибки ввода/вывода.
     */
    @Override
    public String execute(UserInterface ui, StorageInteraction storageInteraction, Vehicle vehicle) throws IncorrectValueException {
        vehicle.showVehicle();
        int initSize = storageInteraction.getSize();
        storageInteraction.add(vehicle);
        if (storageInteraction.getSize() > initSize)
            return ("Транспорт успешно добавлен");
        else return ("Такой транспорт уже есть");
    }
}
