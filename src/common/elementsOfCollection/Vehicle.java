package common.elementsOfCollection;

import server.collection.VehicleStorage;
import common.exception.IncorrectValueException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс хранимых объектов.
 */
public class Vehicle implements Serializable {

    /**
     * ID транспорта. Значение поля должно быть больше 0, Значение этого поля должно быть уникальным.
     */
    private long id;

    /**
     * Название транспорта. Поле не может быть null, Строка не может быть пустой.
     */
    private String name;

    /**
     * Координаты транспорта. Поле не может быть null.
     */
    private Coordinates coordinates;

    /**
     * Дата создания объекта. Поле не может быть null.
     */
    private java.time.LocalDate creationDate;

    /**
     * Мощность двигателя. Значение поля должно быть больше 0. Значение не может быть null.
     */
    private long enginePower;

    /**
     * Число колёс. Значение поля должно быть больше 0.
     */
    private long numberOfWheels;

    /**
     * Пройденное расстояние. Значение поля должно быть больше 0.
     */
    private float distanceTravelled;

    /**
     * Тип топлива транспорта. Поле может быть null.
     */
    private FuelType fuelType;


    /**
     * Полный конструктор.
     *
     * @param id                ID транспорта.
     * @param name              Название транспорта.
     * @param coordinates       Координаты транспорта.
     * @param creationDate      Время создания объекта.
     * @param enginePower       Мощность двигателя.
     * @param numberOfWheels    Число колес транспорта.
     * @param distanceTravelled Пройденное расстояние.
     * @param fuelType          Тип топлива транспорта.
     */
    public Vehicle(long id, String name, Coordinates coordinates, LocalDate creationDate, long enginePower,
                   long numberOfWheels, float distanceTravelled, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.distanceTravelled = distanceTravelled;
        this.fuelType = fuelType;
    }

    /**
     * Конструктор без авто генерируемых полей.
     *
     * @param name              Название транспорта.
     * @param coordinates       Координаты транспорта.
     * @param enginePower       Мощность двигателя.
     * @param numberOfWheels    Число колес транспорта.
     * @param distanceTravelled Пройденное расстояние.
     * @param fuelType          Тип топлива транспорта.
     */
    public Vehicle(String name, Coordinates coordinates, long enginePower, long numberOfWheels,
                   float distanceTravelled, FuelType fuelType) {
        this.name = name;
        this.coordinates = coordinates;
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.distanceTravelled = distanceTravelled;
        this.fuelType = fuelType;
    }

    /**
     * Пустой конструктор.
     */
    public Vehicle() {

    }

    /**
     * Метод вывода данных о транспорте.
     */
    public void showVehicle() {
        if (getCoordinates() != null && getName() != null)
            System.out.println("Название транспорта: " + getName() + "\n" +
                    "ID транспорта: " + getId() + "\n" +
                    "Координата Х транспорта: " + getX() + "\n" +
                    "Координата Y транспорта: " + getY() + "\n" +
                    "Дата создания объекта: " + getCreationDate() + "\n" +
                    "Мощность двигателя: " + getEnginePower() + "\n" +
                    "Число колёс транспорта: " + getNumberOfWheels() + "\n" +
                    "Пройденное расстояние: " + getDistanceTravelled() + "\n" +
                    "Тип топлива: " + getFuelType() + "\n"
            );
    }

    /**
     * Метод вывода данных о транспорте.
     */
    public String returnVehicleDescribe(Vehicle vehicle) {
        return "Транспорт: " + "\n" +
                "Название транспорта: " + vehicle.getName() + "\n" +
                "ID транспорта: " + vehicle.getId() + "\n" +
                "Координата Х транспорта: " + vehicle.getX() + "\n" +
                "Координата Y транспорта: " + vehicle.getY() + "\n" +
                "Дата создания объекта: " + vehicle.getCreationDate() + "\n" +
                "Мощность двигателя: " + vehicle.getEnginePower() + "\n" +
                "Число колёс транспорта: " + vehicle.getNumberOfWheels() + "\n" +
                "Пройденное расстояние: " + vehicle.getDistanceTravelled() + "\n" +
                "Тип топлива: " + vehicle.getFuelType() + "\n" + "\n";
    }

    /**
     * Метод, возвращающий ID транспорта.
     *
     * @return ID продукта.
     */
    public long getId() {
        return id;
    }

    /**
     * Метод, добавляющий ID продукту.
     *
     * @param id ID продукта.
     * @throws IncorrectValueException В случае ошибки ввода/вывода.
     */
    public void setId(long id) throws IncorrectValueException {
        if (VehicleStorage.checkID(id, VehicleStorage.vehicles) && id > 0) {
            this.id = id;
        }
    }

    /**
     * Метод, возвращающий название транспорта.
     *
     * @return Название транспорта.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, добавляющий имя транспорта.
     *
     * @param name Название транспорта.
     * @throws IncorrectValueException В случае ошибки ввода/вывода.
     */
    public void setName(String name) throws IncorrectValueException {
        if (!name.equals("")) this.name = name;
        else throw new IncorrectValueException("Название не может быть пустым");
    }

    /**
     * Метод, возвращающий местоположение транспорта.
     *
     * @return Местоположение транспорта.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Метод, добавляющий местоположение продукта.
     *
     * @param coordinates Местоположение продукта.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Метод, возвращающий координату X транспорта.
     *
     * @return Координата X транспорта.
     */
    public double getX() {
        return getCoordinates().getX();
    }

    /**
     * Метод, возвращающий координату Y транспорта.
     *
     * @return Координата Y транспорта.
     */
    public double getY() {
        return getCoordinates().getY();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Метод, возвращающий мощность двигателя.
     *
     * @return Мощность двигателя.
     */
    public long getEnginePower() {
        return enginePower;
    }

    /**
     * Метод, добавляющий мощность двигателя.
     *
     * @param enginePower Мощность двигателя.
     */
    public void setEnginePower(long enginePower) throws IncorrectValueException {
        if (enginePower >= 0) this.enginePower = enginePower;
        else {
            throw new IncorrectValueException("Мощность не может быть меньше 0");
        }
    }


    /**
     * Метод, возвращающий число колес транспорта.
     *
     * @return Число колес транспорта.
     */
    public long getNumberOfWheels() {
        return numberOfWheels;
    }

    /**
     * Метод, устанавливающий число колес транспорта.
     *
     * @param numberOfWheels Число колес транспорта.
     */
    public void setNumberOfWheels(long numberOfWheels) throws IncorrectValueException {
        if (numberOfWheels >= 0) this.numberOfWheels = numberOfWheels;
        else {
            throw new IncorrectValueException("Число колес не может быть меньше 0");
        }
    }

    /**
     * Метод, возвращающий пройденное расстояние.
     *
     * @return Пройденное расстояние.
     */
    public float getDistanceTravelled() {
        return distanceTravelled;
    }

    /**
     * Метод, устанавливающий пройденное расстояние.
     *
     * @param distanceTravelled Пройденное расстояние
     */
    public void setDistanceTravelled(float distanceTravelled) throws IncorrectValueException {
        if (distanceTravelled >= 0) this.distanceTravelled = distanceTravelled;
        else {
            throw new IncorrectValueException("Пройденное расстояние не может быть меньше 0");
        }
    }

    /**
     * Метод, возвращающий тип топлива транспорта.
     *
     * @return Тип топлива транспорта.
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * Метод, устанавливающий тип топлива транспорта.
     *
     * @param fuelType Тип топлива транспорта.
     */
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Метод, генерирующий и возвращающий уникальный ID.
     *
     * @return ID продукта.
     */
    public Long generateID() {
        Date date = new Date();
        AtomicLong id = new AtomicLong((date.getTime() / 1000));
        for (Vehicle vehicle : VehicleStorage.vehicles) {
            if (vehicle.getId() == id.get()) {
                do {
                    id.set((int) (Math.random() * Long.MAX_VALUE));
                } while (vehicle.getId() == id.get());
            }
        }
        return Long.parseLong(id.toString());
    }

    /**
     * Метод сравнения объектов класса.
     *
     * @param o Второй объект сравнения.
     * @return Результат сравнения.
     */
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof Vehicle)) {
            throw new ClassCastException();
        }
        Vehicle vehicle = (Vehicle) o;
        return (int) (this.getId() - vehicle.getId());
    }

}
