package server.utils;

import common.exception.IncorrectValueException;
import org.json.simple.parser.ParseException;
import server.Server;
import server.collection.VehicleStorage;
import server.interaction.StorageInteraction;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

public class FileHelper {

    public static void readFile(String path, StorageInteraction storageInteraction) throws IOException, ParseException, IncorrectValueException {
        Path p = Paths.get(path);
        boolean exists = Files.exists(p);
        boolean isDirectory = Files.isDirectory(p);
        boolean isFile = Files.isRegularFile(p);
        if (exists && !isDirectory && isFile){
            File file = new File(path);
            if (file.canWrite() && file.canRead()){
                if (!isFileEmpty(file)) {
                    VehicleStorage.vehicles = Parser.readArrayFromFile(Parser.initParser(path));
                    Server.logger.log(Level.INFO, "Коллекция создается на основе содержимого файла." + "\n");
                } else {
                    Server.setStringMessage("Был считан пустой файл.");
                }
            } else {
                Server.setStringMessage("Нет необходимого доступа к файлу с коллекцией.\n");
                throw new IOException("Нет необходимого доступа к файлу с коллекцией.");
            }
        } else {
            Server.setStringMessage("С файлом беда.\n");
            throw new IllegalArgumentException("С файлом беда.");
        }
    }

    public static boolean isFileEmpty(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        return br.readLine() == null;
    }
}
