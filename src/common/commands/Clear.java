package common.commands;

import common.ui.UserInterface;
import server.interaction.StorageInteraction;

import java.io.IOException;

/**
 * Класс команды clear.
 */
public class Clear extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Clear() {
        cmdLine = "clear";
        description = "очистить коллекцию";
        options = "Нет параметров.";
        needsObject = false;
        argumentAmount = 0;
    }

    /**
     * Метод исполнения
     *
     * @param ui объект, через который ведется взаимодействие с пользователем.
     * @return Результат команды.
     */
    public String execute(UserInterface ui, StorageInteraction storageInteraction) throws IOException {
        storageInteraction.clear();
        if (storageInteraction.getSize() > 0)
            return ("Что-то пошло не так, попробуйте еще раз");
        else return ("Коллекция очищена");
    }
}

