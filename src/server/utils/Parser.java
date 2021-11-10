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
                vehicle.setId(Long.parseLong((String) jsonObject1.get("id")));
            }

            if (ValidationClass.validateName((String) jsonObject1.get("name"), true, ui)) {
                vehicle.setName((String) jsonObject1.get("name"));
            }

            JSONObject coordinates = (JSONObject) jsonObject1.get("coordinates");
            if (ValidationClass.validateDouble((String) coordinates.get("x"), true, ui, false) &&
                    ValidationClass.validateDouble((String) coordinates.get("y"), true, ui, false)) {
                vehicle.setCoordinates(new Coordinates(Double.parseDouble((String) coordinates.get("x")), Double.parseDouble((String) coordinates.get("y"))));
            }

            if (ValidationClass.validateDate((String) jsonObject1.get("creationDate"), true, ui)) {
                LocalDate creationDate = LocalDate.parse((String) jsonObject1.get("creationDate"));
                vehicle.setCreationDate(creationDate);
            }

            if (ValidationClass.validateLong((String) jsonObject1.get("enginePower"), true, ui, false)) {
                vehicle.setEnginePower(Long.parseLong((String) jsonObject1.get("enginePower")));
            }

            if (ValidationClass.validateLong((String) jsonObject1.get("numberOfWheels"), true, ui, true)) {
                vehicle.setNumberOfWheels(Long.parseLong((String) jsonObject1.get("numberOfWheels")));
            }

            if (ValidationClass.validateFloat((String) jsonObject1.get("distanceTravelled"), true, ui, true)) {
                vehicle.setDistanceTravelled(Float.parseFloat((String) jsonObject1.get("distanceTravelled")));
            }

            if (ValidationClass.validateFuelType((String) jsonObject1.get("fuelType"), true, ui)) {
                vehicle.setFuelType(FuelType.valueOf((String) jsonObject1.get("fuelType")));
            }
            vehicles.add(vehicle);
            vehicle.showVehicle();
        }
        return vehicles;
    }
}
