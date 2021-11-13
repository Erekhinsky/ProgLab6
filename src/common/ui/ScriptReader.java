package common.ui;

import common.commands.abstracts.Command;
import common.elementsOfCollection.Vehicle;
import common.exception.IncorrectValueException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Stack;

public class ScriptReader {

    static Stack<Command> scriptStack = new Stack<>();
    private static final HashSet<String> paths = new HashSet<>();

    public static Stack<Command> scriptRead(String path) throws IOException {
        Path p = Paths.get(path);
        UserInterface scriptInteraction = new UserInterface(new FileReader(path), false, new OutputStreamWriter(System.out));
        boolean exists = Files.exists(p);
        boolean isDirectory = Files.isDirectory(p);
        boolean isFile = Files.isRegularFile(p);
        try {
            if (exists && !isDirectory && isFile) {
                paths.add(path);
                String line;
                while (scriptInteraction.hasNextLine()) {
                    line = scriptInteraction.read();
                    String cmdLine = line.split(" ")[0];
                    String cmdArgument;
                    try {
                        cmdArgument = line.split(" ")[1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        cmdArgument = null;
                    }
                    Command cmd = CommandCenter.getInstance().getCmdCommand(cmdLine);
                    if (cmd.getArgumentAmount() == 0) {
                        scriptStack.push(cmd);
                    } else {
                        if (cmd.getArgumentAmount() == 1 && cmd.getNeedsObject()) {
                            Vehicle vehicle = scriptInteraction.readVehicle(scriptInteraction);
                            cmd.setObject(vehicle);
                            scriptStack.push(cmd);
                        }
                        if (cmd.getArgumentAmount() == 1 && !cmd.getNeedsObject()) {
                            if (cmd.getCmdLine().equals("execute_script")) {
                                if (!paths.contains(path)) {
                                    paths.add(path);
                                    scriptStack.push(cmd);
                                } else {
                                    paths.clear();
                                    throw new InvalidAlgorithmParameterException("Выполнение скрипта остановлено, т.к. возможна рекурсия");
                                }
                            }
                            cmd.setArgument(cmdArgument);
                            scriptStack.push(cmd);
                        }
                        if (cmd.getArgumentAmount() == 2 && cmd.getNeedsObject()) {
                            Vehicle vehicle = scriptInteraction.readVehicle(scriptInteraction);
                            cmd.setArgument(cmdArgument);
                            cmd.setObject(vehicle);
                            scriptStack.push(cmd);
                        }
                    }
                }
                reverse(scriptStack);
                paths.clear();
                return scriptStack;
            } else return null;
        } catch (InvalidParameterException | FileNotFoundException | NoSuchElementException e) {
            paths.clear();
        } catch (InvalidAlgorithmParameterException | IncorrectValueException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void reverse(Stack<Command> arr){
        arr.sort(Collections.reverseOrder());
    }

}
