package common.exception;

/**
 * Класс ошибки некорректного значения.
 */
public class IncorrectValueException extends Exception {

    /**
     * Конструктор для вывода сообщения ошибки.
     *
     * @param message Сообщение ошибки.
     */
    public IncorrectValueException(String message) {
        super(message);
    }

}