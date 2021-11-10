package common.exception;

/**
 * Класс ошибки некорректного значения на вводе.
 */
public class InvalidInputException extends RuntimeException {

    /**
     * Конструктор для вывода сообщения ошибки.
     *
     * @param message Сообщение ошибки.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
