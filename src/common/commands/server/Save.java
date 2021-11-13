package common.commands.server;

import common.commands.abstracts.Command;
import common.ui.UserInterface;
import server.interaction.StorageInteraction;

import java.io.IOException;

/**
 * Класс команды save.
 */
public class Save extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Save() {
        cmdLine = "save";
        description = "сохранить коллекцию в файл";
        options = "Нет параметров.";
        needsObject = false;
        argumentAmount = 0;
        serverCommandLabel = true;
    }

    /**
     * Метод исполнения
     *
     * @param ui объект, через который ведется взаимодействие с пользователем.
     */
    @Override
    public String execute(UserInterface ui, StorageInteraction storageInteraction) throws IOException {
        storageInteraction.save();
        return ("Коллекция сохранена в файл");
    }

    public String execute(StorageInteraction storageInteraction) throws IOException {
        storageInteraction.save();
        return ("Коллекция сохранена в файл");
    }
}