package common.commands.user;

import common.commands.abstracts.Command;
import common.exception.IncorrectValueException;
import common.ui.UserInterface;
import server.interaction.StorageInteraction;
import server.utils.ValidationClass;

import java.io.IOException;

/**
 * Класс команды remove_by_id.
 */
public class RemoveById extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public RemoveById() {
        cmdLine = "remove_by_id";
        description = "удалить элемент из коллекции по его id";
        options = "Параметры: ID объекта.";
        needsObject = false;
        argumentAmount = 1;
        serverCommandLabel = false;
    }

    /**
     * Метод исполнения
     *
     * @param ui        объект, через который ведется взаимодействие с пользователем.
     * @param arguments необходимые для исполнения аргументы.
     */
    public String execute(UserInterface ui, String arguments, StorageInteraction storageInteraction) throws IOException {
        long id = 0;
        try {
            if (ValidationClass.validateLong(arguments, true, ui, false))
                id = Long.parseLong(arguments);
            else throw new IncorrectValueException("Неверное введенное значение");
        } catch (IOException | IncorrectValueException e) {
            e.printStackTrace();
        }
        if (storageInteraction.findById(id)) {
            storageInteraction.removeById(id);
            return ("Транспорт удален");
        } else return ("Транспорт с таким id не найден");
    }
}
