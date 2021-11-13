package server.utils;

import common.elementsOfCollection.Vehicle;
import common.ui.UserInterface;
import common.elementsOfCollection.Coordinates;
import common.elementsOfCollection.FuelType;
import common.exception.IncorrectValueException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.Server;
import server.collection.VehicleStorage;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Vector;

public class Parser {

    public static Path initParser(String path) {
        return Paths.get(path);
    }

    public static Vector<Vehicle> readArrayFromFile(Path p) throws IOException, ParseException, IncorrectValueException {

        int errorCounter = 0;
        InputStreamReader isr = new InputStreamReader(new FileInputStream(String.valueOf(p)));

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(isr);

        JSONArray vehicleArr = (JSONArray) jsonObject.get("vehicle");
        Vector<Vehicle> vehicles = new Vector<>();
        UserInterface ui = new UserInterface(new InputStreamReader(System.in), true, new OutputStreamWriter(System.out));

        for (Object o : vehicleArr) {
            JSONObject jsonObject1 = (JSONObject) o;
            Vehicle vehicle = new Vehicle();

            if (ValidationClass.validateLong((String) jsonObject1.get("id"), true, ui, true)) {
                if (VehicleStorage.checkID(Long.parseLong((String) jsonObject1.get("id")), vehicles))
                    vehicle.setId(Long.parseLong((String) jsonObject1.get("id")));
                else {
                    Server.setStringMessage("\nВ коллекции файла обнаружен объект с повторяющимся ID, он не будет добавлен \n");
                    errorCounter++;
                    continue;
                }
            }

            if (ValidationClass.validateName((String) jsonObject1.get("name"), true, ui)) {
                vehicle.setName((String) jsonObject1.get("name"));
            } else {
                Server.setStringMessage("\nВ объекте коллекции некорректно введено Название транспорта. \n");
                errorCounter++;
                continue;
            }

            JSONObject coordinates = (JSONObject) jsonObject1.get("coordinates");
            if (ValidationClass.validateDouble((String) coordinates.get("x"), true, ui, false) &&
                    ValidationClass.validateDouble((String) coordinates.get("y"), true, ui, false)) {
                vehicle.setCoordinates(new Coordinates(Double.parseDouble((String) coordinates.get("x")), Double.parseDouble((String) coordinates.get("y"))));
            } else {
                Server.setStringMessage("\nВ объекте коллекции некорректно введены Координаты. \n");
                errorCounter++;
                continue;
            }

            if (ValidationClass.validateDate((String) jsonObject1.get("creationDate"), true, ui)) {
                LocalDate creationDate = LocalDate.parse((String) jsonObject1.get("creationDate"));
                vehicle.setCreationDate(creationDate);
            } else {
                Server.setStringMessage("\nВ объекте коллекции некорректно введена дата создания объекта. \n");
                errorCounter++;
                continue;
            }

            if (ValidationClass.validateLong((String) jsonObject1.get("enginePower"), true, ui, false)) {
                vehicle.setEnginePower(Long.parseLong((String) jsonObject1.get("enginePower")));
            } else {
                Server.setStringMessage("\nВ объекте коллекции некорректно введена Мощность двигателя.  \n");
                errorCounter++;
                continue;
            }

            if (ValidationClass.validateLong((String) jsonObject1.get("numberOfWheels"), true, ui, true)) {
                vehicle.setNumberOfWheels(Long.parseLong((String) jsonObject1.get("numberOfWheels")));
            } else {
                Server.setStringMessage("\nВ объекте коллекции некорректно введено Число колёс транспорта. \n");
                errorCounter++;
                continue;
            }

            if (ValidationClass.validateFloat((String) jsonObject1.get("distanceTravelled"), true, ui, true)) {
                vehicle.setDistanceTravelled(Float.parseFloat((String) jsonObject1.get("distanceTravelled")));
            } else {
                Server.setStringMessage("\nВ объекте коллекции некорректно введена Пройденная дистанция. \n");
                errorCounter++;
                continue;
            }

            if (ValidationClass.validateFuelType((String) jsonObject1.get("fuelType"), true, ui)) {
                vehicle.setFuelType(FuelType.valueOf((String) jsonObject1.get("fuelType")));
            } else {
                Server.setStringMessage("\nВ объекте коллекции некорректно введен Тип топлива. \n");
                errorCounter++;
                continue;
            }
            vehicles.add(vehicle);
        }
        if (errorCounter == 0){
            Server.setStringMessage("Файл с коллекцией считался корректно.\n");
        } else {
            Server.setStringMessage("Количество объектов коллекции с ошибками: " + errorCounter + "\n");
        }
        return vehicles;
    }
}
