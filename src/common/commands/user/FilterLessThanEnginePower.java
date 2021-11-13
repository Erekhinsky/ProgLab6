package common.commands.user;

import common.commands.abstracts.Command;
import common.exception.IncorrectValueException;
import common.ui.UserInterface;
import server.interaction.StorageInteraction;
import server.utils.ValidationClass;

import java.io.IOException;

/**
 * Класс команды filter_less_than_engine_power.
 */
public class FilterLessThanEnginePower extends Command {

    /**
     * Стандартный конструктор, добавляющий строку вызова и описание команды.
     */
    public FilterLessThanEnginePower() {
        cmdLine = "filter_less_than_engine_power";
        description = "вывести элементы, значение поля enginePower которых меньше заданного";
        options = "Параметры: Мощность двигателя";
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
    @Override
    public String execute(UserInterface ui, String arguments, StorageInteraction storageInteraction) throws IOException {
        long enginePower = 0;
        try {
            if (ValidationClass.validateLong(arguments, true, ui, false))
                enginePower = Long.parseLong(arguments);
            else throw new IncorrectValueException("Неверное введенное значение");
        } catch (IOException | IncorrectValueException e) {
            e.printStackTrace();
        }
        String resultOfCommand = storageInteraction.filterLessThanEnginePower(enginePower);
        if (resultOfCommand.length() > 1)
            return (resultOfCommand);
        else return ("Что-то пошло не так");
    }
}
