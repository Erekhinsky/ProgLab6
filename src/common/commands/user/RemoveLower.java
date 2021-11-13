package common.commands.user;

import common.commands.abstracts.Command;
import common.ui.UserInterface;
import common.elementsOfCollection.Vehicle;
import server.interaction.StorageInteraction;

/**
 * Класс команды remove_lower.
 */
public class RemoveLower extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public RemoveLower() {
        cmdLine = "remove_lower";
        description = "удалить из коллекции все элементы, меньшие, чем заданный";
        options = "Параметры: Сравниваемый объект";
        needsObject = true;
        argumentAmount = 1;
        serverCommandLabel = false;
    }

    /**
     * Метод исполнения
     *
     * @param ui объект, через который ведется взаимодействие с пользователем.
     */
    @Override
    public String execute(UserInterface ui, StorageInteraction storageInteraction, Vehicle vehicle) {
        int size1 = storageInteraction.getSize();
        storageInteraction.removeLower(vehicle);
        int size2 = storageInteraction.getSize();
        if (size2 < size1)
            return ("Операция успешно выполнена");
        else return "Что-то пошло не так";
    }
}
