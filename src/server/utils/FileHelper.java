package server.utils;

import server.Server;
import server.collection.VehicleStorage;
import server.interaction.StorageInteraction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

public class FileHelper {

    public static void readFile(String path, StorageInteraction storageInteraction) throws IOException {
        Path p = Paths.get(path);
        boolean exists = Files.exists(p);
        boolean isDirectory = Files.isDirectory(p);
        boolean isFile = Files.isRegularFile(p);
        if (exists && !isDirectory && isFile){
            File file = new File(path);
            if (file.canWrite() && file.canRead()){
                try {
                    VehicleStorage.vehicles = Parser.readArrayFromFile(Parser.initParser(path));
                    Server.logger.log(Level.INFO, "Коллекция создается на основе содержимого файла." + "\n");
                } catch (Exception e) {
                    Server.logger.log(Level.SEVERE, "Был считан неверный JSON файл.\n");
                }
            } else {
                throw new IOException("Нет необходимого доступа к файлу с коллекцией.");
            }
        } else {
            throw new IllegalArgumentException("С файлом беда.");
        }
    }
}
