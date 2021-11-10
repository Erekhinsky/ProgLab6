package server.interaction;

import server.collection.VehicleStorage;
import common.elementsOfCollection.*;
import common.exception.IncorrectValueException;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс взаимодействия с коллекцией.
 */
public final class StorageInteraction implements CommandInterface {

    /**
     * Статическое поле-хранилище коллекции.
     */
    private static VehicleStorage storage;

    /**
     * Статическое поле, содержащее путь к файлу с хранимой коллекцией.
     */
    private static String originPath;

    /**
     * Стандартный конструктор, задает хранилище, с которым будет работа.
     *
     * @param storage    хранилище.
     * @param originPath путь к файлу.
     */
    public StorageInteraction(VehicleStorage storage, String originPath) {
        StorageInteraction.storage = storage;
        StorageInteraction.originPath = originPath;
    }

    /**
     * Метод, реализующий команду info.
     */
    public String info() {
        return "Дата доступа к коллекции: " + storage.getInitializationDate() + "\n"
                + "Тип коллекции: " + storage.getCollection().getClass() + "\n"
                + "Размер коллекции: " + storage.getCollection().size();
    }

    /**
     * Метод, реализующий команду help.
     *
     * @return Список команд и их описание.
     */
    @Override
    public String help() {
        return null;
    }

    /**
     * Метод, реализующий команду show.
     *
     * @return Строковое представление объектов коллекции.
     */
    public String show() {
        ArrayList<Vehicle> sortedDisplay = new ArrayList<>(storage.getCollection());
        sortedDisplay.sort(Comparator.comparing(Vehicle::getName));
        StringBuilder display = new StringBuilder();
        sortedDisplay.forEach((vehicle -> display.append(vehicle.returnVehicleDescribe(vehicle))));
        return display.toString();
    }

    /**
     * Метод, реализующий команду add.
     *
     * @param vehicle Добавляемый объект.
     */
    public void add(Vehicle vehicle) {
        if (VehicleStorage.checkID(vehicle.getId(), VehicleStorage.vehicles)) {
            VehicleStorage.vehicles.add(vehicle);
        } else try {
            throw new IncorrectValueException("Продукт с таким ID уже существует.");
        } catch (IncorrectValueException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, реализующий команду update.
     *
     * @param vehicle Новый объект.
     * @param id      ID заменяемого объекта.
     */
    @Override
    public void update(long id, Vehicle vehicle) {
        removeById(id);
        try {
            vehicle.setId(id);
        } catch (IncorrectValueException e) {
            e.printStackTrace();
        }
        storage.getCollection().add(vehicle);
    }

    /**
     * Метод, реализующий команду remove_by_id.
     *
     * @param id Ключ удаляемого объекта.
     */
    @Override
    public void removeById(long id) {
        Vehicle vehicle = returnById(id);
        if (vehicle != null) {
            storage.getCollection().remove(vehicle);
        }
    }

    /**
     * Метод, реализующий команду clear.
     */
    public void clear() {
        storage.clear();
    }

    /**
     * Метод, реализующий команду save.
     *
     * @throws IOException В случае ошибки ввода/вывода.
     */
    public void save() throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(StorageInteraction.originPath));
        bufferedWriter.write("{" + "\n");
        bufferedWriter.write("\t" + "\"vehicle\" : [" + "\n");
        int counter = 0;
        for (Vehicle v : storage.getCollection()) {
            bufferedWriter.write("\t" + "\t" + "{" + "\n");
            bufferedWriter.write("\t" + "\t" + "\t" + "\"id\": " + "\"" + v.getId() + "\"," + "\n");
            bufferedWriter.write("\t" + "\t" + "\t" + "\"name\": " + "\"" + v.getName() + "\"," + "\n");
            bufferedWriter.write("\t" + "\t" + "\t" + "\"coordinates\": { " + "\n");
            bufferedWriter.write("\t" + "\t" + "\t" + "\t" + "\"x\": " + "\"" + v.getX() + "\"," + "\n");
            bufferedWriter.write("\t" + "\t" + "\t" + "\t" + "\"y\": " + "\"" + v.getY() + "\"" + "\n");
            bufferedWriter.write("\t" + "\t" + "\t" + "}," + "\n");
            bufferedWriter.write("\t" + "\t" + "\t" + "\"creationDate\": " + "\"" + v.getCreationDate().format(formatter) + "\"," + "\n");
            bufferedWriter.write("\t" + "\t" + "\t" + "\"enginePower\": " + "\"" + v.getEnginePower() + "\"," + "\n");
            bufferedWriter.write("\t" + "\t" + "\t" + "\"numberOfWheels\": " + "\"" + v.getNumberOfWheels() + "\"," + "\n");
            bufferedWriter.write("\t" + "\t" + "\t" + "\"distanceTravelled\": " + "\"" + v.getDistanceTravelled() + "\"," + "\n");
            bufferedWriter.write("\t" + "\t" + "\t" + "\"fuelType\": " + "\"" + v.getFuelType() + "\"" + "\n");
            if (counter == storage.getCollection().size() - 1) {
                bufferedWriter.write("\t" + "\t" + "}" + "\n");
            } else bufferedWriter.write("\t" + "\t" + "}," + "\n");
            counter++;
        }
        bufferedWriter.write("\t" + "]" + "\n");
        bufferedWriter.write("}" + "\n");
        bufferedWriter.flush();
    }

    /**
     * Метод, реализующий команду exit.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Метод, реализующий команду remove_first.
     */
    @Override
    public void removeFirst() {
        storage.getCollection().remove(0);
    }

    /**
     * Метод, реализующий команду remove_lower.
     *
     * @param vehicle Объект сравнения.
     */
    @Override
    public void removeLower(Vehicle vehicle) {
        Vector<Vehicle> vehicles = storage.getCollection();
        List<Vehicle> toBeSortedVehicles = new ArrayList<>(vehicles);
        toBeSortedVehicles.sort(Comparator.comparing(Vehicle::getEnginePower));
        List<Vehicle> toBeRemovedVehicles = toBeSortedVehicles.stream().
                filter(v -> v.compareTo(vehicle) < 0).
                collect(Collectors.toList());
        toBeRemovedVehicles.
                forEach(vehicle1 -> storage.getCollection().remove(vehicle1));
    }

    /**
     * Метод, реализующий команду add_if_max.
     *
     * @param vehicle Транспорт, добавляемый в коллекцию, если его значение превышает значение наибольшего элемента.
     */
    @Override
    public void addIfMax(Vehicle vehicle) {
        Vector<Vehicle> vehicles = storage.getCollection();
        Vehicle maxVehicle = new Vehicle();
        List<Vehicle> toBeSortedVehicles = new ArrayList<>(vehicles);
        toBeSortedVehicles.sort(Comparator.comparing(Vehicle::getEnginePower));
        for (Vehicle v : toBeSortedVehicles) {
            if (v.compareTo(maxVehicle) > 0)
                maxVehicle = v;
        }
        if (maxVehicle.getEnginePower() < vehicle.getEnginePower()) {
            storage.getCollection().add(vehicle);
        }
    }

    /**
     * Метод, реализующий команду filter_less_than_engine_power.
     *
     * @param enginePower Мощность двигателя транспорта для фильтра.
     * @return Отсортированная коллекция.
     */
    @Override
    public String filterLessThanEnginePower(long enginePower) {
        Vehicle vehicle = new Vehicle();
        return storage.getCollection().stream()
                .filter(v -> v.getEnginePower() < enginePower)
                .map(v -> vehicle.returnVehicleDescribe(v) + "\n\n")
                .collect(Collectors.joining());
    }

    /**
     * Метод, реализующий команду count_greater_than_price.
     *
     * @param fuelType Тип топлива
     * @return Число объектов с типом топлива, ниже указанного.
     */
    @Override
    public int countLessThanFuelType(FuelType fuelType) {
        Iterator<Vehicle> itr = storage.getCollection().iterator();
        int counter = 0;
        while (itr.hasNext()) {
            Vehicle vehicle = itr.next();
            FuelType fl = vehicle.getFuelType();
            if (FuelType.compareFuelType(fuelType) > FuelType.compareFuelType(fl)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Метод, реализующий команду print_field_descending_distance_travelled.
     */
    public List<Float> printFieldDescendingDistanceTravelled() {
        List<Float> distanceTravelled = new ArrayList<>();
        for (Vehicle v : storage.getCollection()) {
            distanceTravelled.add(v.getDistanceTravelled());
        }
        return distanceTravelled.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

    }


    /**
     * Метод, возвращающий размер хранимой коллекции.
     *
     * @return размер коллекции.
     */
    public int getSize() {
        return storage.getCollection().size();
    }

    /**
     * Метод, возвращающий объект по ID.
     *
     * @param id ID для поиска.
     * @return Объект, ID которого равно заданному.
     */
    public Vehicle returnById(long id) {
        return storage.getCollection().stream()
                .filter(v -> (id == v.getId()))
                .findAny()
                .orElse(null);
    }

    /**
     * Метод, проверяющий наличие объекта по ID.
     *
     * @param id ID для поиска.
     * @return True если объект существует, иначе false.
     */
    @Override
    public boolean findById(long id) {
        return storage.getCollection().stream().filter(v -> (id == v.getId())).findAny().orElse(null) != null;
    }

    public void close() throws IOException {
        save();
        System.exit(0);
    }

}
