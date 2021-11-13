package common.commands.user;

import common.commands.abstracts.Command;
import common.ui.CommandCenter;
import common.ui.UserInterface;
import server.Server;
import server.interaction.StorageInteraction;

import java.io.IOException;

/**
 * Класс команды help.
 */
public class Help extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public Help() {
        cmdLine = "help";
        description = "Вывести справку по доступным командам";
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
        StringBuilder display = new StringBuilder();
        for (Command cmd : CommandCenter.getInstance().receiveAllCommands()) {
            if (!cmd.getServerCommandLabel())
                display.append(cmd.getCmdLine()).append(" || ").append(cmd.getDescription()).append(" || ").append(cmd.getOptions()).append("\n");
        }
        return (display.toString());
    }
}
