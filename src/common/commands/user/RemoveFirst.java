package common.commands.user;

import common.commands.abstracts.Command;
import common.ui.UserInterface;
import server.interaction.StorageInteraction;

import java.io.IOException;

/**
 * Класс команды remove_first.
 */
public class RemoveFirst extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public RemoveFirst() {
        cmdLine = "remove_first";
        description = "удалить первый элемент из коллекции";
        options = "Нет параметров.";
        needsObject = false;
        argumentAmount = 0;
        serverCommandLabel = false;
    }

    /**
     * Метод исполнения
     *
     * @param ui объект, через который ведется взаимодействие с пользователем.
     */
    @Override
    public String execute(UserInterface ui, StorageInteraction storageInteraction) throws IOException {
        int size1 = storageInteraction.getSize();
        try {
            storageInteraction.removeFirst();
            int size2 = storageInteraction.getSize();
            if (size2 < size1) return ("Операция успешно выполнена");
            else return ("Упс, что-то пошло не так.");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } return "Коллекция не полна.";
    }
}
