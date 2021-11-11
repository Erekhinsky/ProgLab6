package server.collection;

import common.elementsOfCollection.Vehicle;

import java.util.Date;
import java.util.Vector;

public class VehicleStorage {

    /**
     * Хранимая коллекция.
     */
    public static Vector<Vehicle> vehicles = new Vector<>();

    /**
     * Метод очистки хранимой коллекции.
     */
    public void clear() {
        vehicles.clear();
    }

    /**
     * Метод, возвращающий дату создания коллекции.
     *
     * @return Дата создания.
     */
    public Date getInitializationDate() {
        return new Date();
    }

    /**
     * Метод, возвращающий хранимую коллекцию.
     *
     * @return Хранимая коллекция.
     */
    public Vector<Vehicle> getCollection() {
        return vehicles;
    }

    /**
     * Метод проверки ID на совпадение с другими в коллекции.
     *
     * @param id       Ключ-ID объекта.
     * @param vehicles Хранимая коллекция.
     * @return Результат проверки, где false- проходит проверку.
     */
    public static Boolean checkID(long id, Vector<Vehicle> vehicles) {
        final Boolean[] checker = {true};
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == id) {
                checker[0] = false;
                break;
            }
        }
        return checker[0];
    }
}
