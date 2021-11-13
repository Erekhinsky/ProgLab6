package common.commands.user;

import common.commands.abstracts.Command;
import common.ui.UserInterface;
import server.Server;
import server.interaction.StorageInteraction;

import java.io.IOException;

/**
 * Класс команды exit.
 */
public class Exit extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Exit() {
        cmdLine = "exit";
        description = "завершить программу";
        options = "Нет параметров.";
        needsObject = false;
        argumentAmount = 0;
        serverCommandLabel = false;
    }

    /**
     * Метод исполнения
     *
     * @param ui объект, через который ведется взаимодействие с пользователем.
     * @return Результат команды.
     */
    @Override
    public String execute(UserInterface ui, StorageInteraction storageInteraction) throws IOException {
        System.exit(0);
        return ("Завершение работы");
    }
}
