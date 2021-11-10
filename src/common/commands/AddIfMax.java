package common.commands;

import common.ui.UserInterface;
import common.elementsOfCollection.Vehicle;
import server.interaction.StorageInteraction;

/**
 * Класс команды add_if_max.
 */
public class AddIfMax extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public AddIfMax() {
        cmdLine = "add_if_max";
        description = "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
        options = "Параметры: Добавляемый объект";
        needsObject = true;
        argumentAmount = 1;
    }

    /**
     * Метод исполнения
     *
     * @param ui объект, через который ведется взаимодействие с пользователем.
     */
    @Override
    public String execute(UserInterface ui, StorageInteraction storageInteraction, Vehicle vehicle) {
        int size1 = storageInteraction.getSize();
        storageInteraction.addIfMax(vehicle);
        int size2 = storageInteraction.getSize();
        if (size2 > size1) {
            return ("Операция успешно выполнена");
        } else return ("Похоже, добавляемый объект меньше максимального или уже существует.");
    }
}