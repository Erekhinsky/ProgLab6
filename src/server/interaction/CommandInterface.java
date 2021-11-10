package server.interaction;

import common.elementsOfCollection.*;
import common.exception.IncorrectValueException;

import java.io.IOException;
import java.util.List;

/**
 * Командный интерфейс.
 */
public interface CommandInterface {

    /**
     * Команда help.
     *
     * @return справка по доступным командам.
     */
    String help();

    /**
     * Команда show.
     *
     * @return элементы коллекции в строковом представлении.
     */
    String show();

    /**
     * Команда info.
     */
    String info();

    /**
     * Команда add.
     *
     * @param vehicle Транспорт.
     * @throws IncorrectValueException В случае ошибки.
     */
    void add(Vehicle vehicle) throws IncorrectValueException;

    /**
     * Команда update.
     *
     * @param id      ID транспорта.
     * @param vehicle Транспорт.
     * @throws IncorrectValueException В случае ошибки.
     */
    void update(long id, Vehicle vehicle) throws IncorrectValueException;

    /**
     * Команда remove_by_id.
     *
     * @param id ID транспорта, по которому будет происходить удаление.
     */
    void removeById(long id);

    /**
     * Команда clear.
     */
    void clear();

    /**
     * Команда save.
     *
     * @throws IOException В случае ошибки.
     */
    void save() throws IOException;

    /**
     * Команда exit.
     */
    void exit();

    /**
     * Команда remove_first.
     */
    void removeFirst();

    /**
     * Команда add_if_max.
     *
     * @param vehicle Транспорт, добавляемый в коллекцию, если его значение превышает значение наибольшего элемента.
     */
    void addIfMax(Vehicle vehicle);

    /**
     * Команда remove_lower.
     *
     * @param vehicle Транспорт, относительно которого будет происходить удаление.
     */
    void removeLower(Vehicle vehicle);

    /**
     * Команда count_less_than_fuel_type.
     *
     * @param fuelType Тип топлива транспорта, относительно которого будет вестись подсчет.
     * @return Количество элементов, значение поля fuelType которых меньше заданного.
     */
    int countLessThanFuelType(FuelType fuelType);

    /**
     * Команда filter_less_than_engine_power.
     *
     * @param enginePower Мощность двигателя, относительно которой будет фильтроваться коллекция.
     * @return Отфильтрованная коллекция.
     */
    String filterLessThanEnginePower(long enginePower);

    /**
     * Команда print_field_descending_distance_travelled.
     */
    List<Float> printFieldDescendingDistanceTravelled();

    /**
     * Метод, возвращающий размер коллекции.
     *
     * @return размер коллекции.
     */
    int getSize();

    /**
     * Метод поиска продукта по ключу.
     *
     * @param id ID продукта.
     * @return true/false - был ли найден продукт по ключу.
     */
    boolean findById(long id);

}
