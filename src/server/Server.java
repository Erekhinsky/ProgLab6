package server;

import common.SerializationTool;
import common.commands.Command;
import common.commands.Save;
import common.elementsOfCollection.Vehicle;
import common.exception.IncorrectValueException;
import common.ui.CommandCenter;
import common.ui.UserInterface;
import server.collection.VehicleStorage;
import server.interaction.StorageInteraction;
import server.utils.Parser;

import javax.crypto.spec.PSource;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {
    public static final Logger logger = Logger.getLogger(Server.class.getName());
    private String[] arguments;
    private final UserInterface userInterface = new UserInterface(new InputStreamReader(System.in), true, new OutputStreamWriter(System.out));
    private final VehicleStorage vehicleStorage = new VehicleStorage();
    private StorageInteraction storageInteraction;

    private SocketAddress socketAddress;
    public DatagramChannel datagramChannel;
    private Selector selector;


    public Server() {
        int PORT = 8700;
        this.socketAddress = new InetSocketAddress(PORT);
        logger.log(Level.INFO, "Сервер начал работу." + "\n");
    }

    public static void main(String[] args) {
        logger.log(Level.INFO, "commons.app.server Запущен." + "\n");
        Server server = new Server();
        server.setArguments(args);
        server.run();
    }

    public void setArguments(String[] arguments) {
        logger.log(Level.INFO, "Установка аргументов сервера" + "\n");
        this.arguments = arguments;
    }

    public void openChannel() throws IOException {
        selector = Selector.open();
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.register(selector, SelectionKey.OP_READ);
        datagramChannel.bind(socketAddress);
        logger.log(Level.INFO, "Канал открыт и привязан к адресу." + "\n");
    }

    private Object readRequest() throws IOException {
        byte[] buffer = new byte[65536];
        ByteBuffer bufferAnswer = ByteBuffer.wrap(buffer);
        socketAddress = datagramChannel.receive(bufferAnswer);
        logger.log(Level.INFO, "Запрос на чтение сервера." + "\n");
        return SerializationTool.deserialize(bufferAnswer.array());
    }

    public String executeCommand(Command cmd) throws IOException, IncorrectValueException {
        String argument;
        Vehicle vehicle;
        if (cmd.getCmdLine().equals("exit")) {
            logger.log(Level.INFO, "Начато сохранение коллекции" + "\n");
            return CommandCenter.getInstance().executeCommand(userInterface, "save", storageInteraction);
        } else {
            if (cmd.getArgumentAmount() == 0) {
                logger.log(Level.INFO, "Выполнение команды без аргументов" + "\n");
                return CommandCenter.getInstance().executeCommand(userInterface, cmd, storageInteraction);
            }
            if (cmd.getArgumentAmount() == 1 && !cmd.getNeedsObject()) {
                logger.log(Level.INFO, "Выполнение команды с аргументом" + "\n");
                argument = cmd.getArgument();
                return CommandCenter.getInstance().executeCommand(userInterface, cmd, argument, storageInteraction);
            }
            if (cmd.getArgumentAmount() == 1 && cmd.getNeedsObject()) {
                logger.log(Level.INFO, "Выполнение команды с аргументом-объектом" + "\n");
                vehicle = cmd.getObject();
                return CommandCenter.getInstance().executeCommand(userInterface, cmd, storageInteraction, vehicle);
            }
            if (cmd.getArgumentAmount() == 2 && cmd.getNeedsObject()) {
                logger.log(Level.INFO, "Выполнение команды с аргументом и аргументом-объектом" + "\n");
                argument = cmd.getArgument();
                vehicle = cmd.getObject();
                return CommandCenter.getInstance().executeCommand(userInterface, cmd, argument, storageInteraction, vehicle);
            } else return "Слишком много аргументов.";
        }
    }

    public void sendAnswer(String str) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(Objects.requireNonNull(SerializationTool.serialize(str)));
        datagramChannel.send(byteBuffer, socketAddress);
        logger.log(Level.INFO, "Сервер отправил ответ клиенту." + "\n");
    }

    public void run() {
        String path;
        try {
            path = arguments[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            try {
                userInterface.showMessage("Файла нет.");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            path = null;
            System.exit(0);
        }
        Path p = Paths.get(path);
        boolean exists = Files.exists(p);
        boolean isDirectory = Files.isDirectory(p);
        boolean isFile = Files.isRegularFile(p);

        try {
            if (!exists || isDirectory || !isFile) {
                throw new IllegalArgumentException();
            }
            storageInteraction = new StorageInteraction(vehicleStorage, arguments[0]);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.log(Level.INFO, "Сохранение коллекции." + "\n");
                try {
                    CommandCenter.getInstance().executeServerCommand(new Save(), storageInteraction);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
            try {
                VehicleStorage.vehicles = Parser.readArrayFromFile(Parser.initParser(arguments[0]));
                logger.info("Коллекция создается на основе содержимого файла." + "\n");
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
            }

            openChannel();

            while (true) {
                int SERVER_WAITING_TIME = 60 * 60 * 1000;
                int readyChannels = selector.select(SERVER_WAITING_TIME);
                if (readyChannels == 0) {
                    selector.close();
                    datagramChannel.close();
                    logger.log(Level.INFO, "Выключение сервера." + "\n");
                    storageInteraction.close();
                    break;
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        datagramChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    }
                    if (key.isWritable()) {
                        sendAnswer(executeCommand((Command) readRequest()));
                        datagramChannel.register(selector, SelectionKey.OP_READ);
                    }
                    keyIterator.remove();
                }
            }
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "В аргументе команды нет пути к файлу или введено неверное имя пути." + "\n");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
