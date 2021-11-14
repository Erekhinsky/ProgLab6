package client;

import common.SerializationTool;
import common.commands.abstracts.Command;
import common.elementsOfCollection.Vehicle;
import common.exception.IncorrectValueException;
import common.ui.CommandCenter;
import common.ui.UserInterface;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    private SocketAddress address;
    private DatagramSocket socket;
    private final UserInterface userInterface = new UserInterface(new InputStreamReader(System.in), true, new OutputStreamWriter(System.out));

    public static void main(String[] args) {
        Client client = new Client();
        boolean tryingToConnect = true;
        while (tryingToConnect) {
            try {
                client.connect();
                client.run();
            } catch (IOException | IncorrectValueException | ClassNotFoundException e) {
                System.out.println("Сервер недоступен.");
                if (ask() <= 0) {
                    tryingToConnect = false;
                }
            }
        }
        client.getSocket().close();
        System.out.println("Завершение работы.");
    }

    public void connect() throws IOException {
        int PORT = 8700;
        String HOST = "localhost";
        address = new InetSocketAddress(HOST, PORT);
        socket = new DatagramSocket();
        userInterface.showMessage("Попытка подключения");
    }

    public void run() throws IOException, IncorrectValueException, ClassNotFoundException {

        sendServerCommand(CommandCenter.getInstance().getCmdCommand("server_info"));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите команду: (Введите \"help\" чтобы получить информацию о командах.)");
        String command = "";
        while (scanner.hasNextLine() && !command.equals("exit")) {
            String[] input = scanner.nextLine().trim().split(" ");
            command = input[0];
            Command cmd = CommandCenter.getInstance().getCmdCommand(input[0]);
            if (!(cmd == null) && !cmd.getServerCommandLabel()) {
                byte[] cmdByte;
                if (cmd.getArgumentAmount() == 0) {
                    cmdByte = SerializationTool.serialize(cmd);
                    send(cmdByte);
                    userInterface.showMessage(receive());
                }
                if (cmd.getArgumentAmount() == 1 && cmd.getNeedsObject()) {
                    cmd.setObject(userInterface.readVehicle(userInterface));
                    cmdByte = SerializationTool.serialize(cmd);
                    send(cmdByte);
                    userInterface.showMessage(receive());
                }
                if (cmd.getArgumentAmount() == 1 && !cmd.getNeedsObject()) {
                    cmd.setArgument(userInterface.readArgument("Введите " + cmd.getOptions(), false));
                    if (command.equals("execute_script")){

                    }
                    cmdByte = SerializationTool.serialize(cmd);
                    send(cmdByte);
                    userInterface.showMessage(receive());
                }
                if (cmd.getArgumentAmount() == 2 && cmd.getNeedsObject()) {
                    cmd.setArgument(userInterface.readArgument("Введите " + cmd.getOptions(), false));
                    Vehicle vehicle = userInterface.readVehicle(userInterface);
                    cmd.setObject(vehicle);
                    cmdByte = SerializationTool.serialize(cmd);
                    send(cmdByte);
                    userInterface.showMessage(receive());
                }
            } else {
                userInterface.showMessage("Введена несуществующая команда, используйте команду help, " +
                        "чтобы получить список возможных команд");
            }
        }
        scanner.close();
        System.out.println("Завершение работы.");
        System.exit(0);
    }

    public DatagramSocket getSocket() {
        return this.socket;
    }

    public void send(byte[] bytes) throws IOException {
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address);
        socket.send(packet);
    }

    public String receive() throws IOException {
        byte[] bytes = new byte[1000000];
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        socket.setSoTimeout(2 * 1000);
        socket.receive(packet);
        return (String) SerializationTool.deserialize(bytes);
    }

    public static int ask() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        System.out.println("Попробовать подключиться снова? (\"Да\"/\"Нет\")");
        while (scanner.hasNextLine()) {
            answer = scanner.nextLine();
            if (answer.equals("Да") || answer.equals("да")) {
                return 1;
            } else if (answer.equals("Нет") || answer.equals("нет")) {
                return 0;
            } else {
                System.out.println("Введите: \"да\" или \"нет\".");
            }
        }
        return -1;
    }

    public void sendServerCommand(Command cmd) throws IOException {
        byte[] cmdByte;
        cmdByte = SerializationTool.serialize(cmd);
        DatagramPacket packet = new DatagramPacket(Objects.requireNonNull(cmdByte), cmdByte.length, address);
        socket.send(packet);
        userInterface.showMessage(receive());
    }

}