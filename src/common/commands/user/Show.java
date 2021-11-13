package common.commands.user;

import common.commands.abstracts.Command;
import common.ui.UserInterface;
import server.interaction.StorageInteraction;

import java.io.IOException;

/**
 * Класс команды show.
 */
public class Show extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Show() {
        cmdLine = "show";
        description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
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
    public String execute(UserInterface ui, StorageInteraction storageInteraction) throws IOException {
        if (storageInteraction.getSize() == 0)
            return ("Коллекция пуста");
        else {
            return ("Коллекция: " + storageInteraction.show());
        }
    }
}
