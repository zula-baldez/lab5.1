package zula.app;


import zula.client.ConnectionManager;
import zula.common.commands.Command;
import zula.common.commands.DragonByIdCommand;
import zula.common.commands.ReadDataFromFile;
import zula.common.data.Color;
import zula.common.data.Coordinates;
import zula.common.data.Dragon;
import zula.common.data.DragonCave;
import zula.common.data.DragonType;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.WrongArgumentException;
import zula.common.exceptions.WrongCommandException;
import zula.common.util.ArgumentParser;
import zula.common.util.ArgumentReader;
import zula.common.util.CommandParser;
import zula.common.util.IoManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.logging.Logger;


public class App {
    private static final Logger APPLOGGER = Logger.getLogger("App logger");
    private final IoManager ioManager;
    private final ConnectionManager connectionManager;
    private final HashMap<String, Command> commands;
    private final ArgumentParser argumentParser = new ArgumentParser();

    public App(IoManager ioManager1, ConnectionManager connectionManager1, HashMap<String, Command> commands1) {
        ioManager = ioManager1;
        connectionManager = connectionManager1;
        commands = commands1;
    }

    public void startApp(String path) throws PrintException, IOException, ClassNotFoundException {
        connectionManager.sendToServer(new ReadDataFromFile(), path);
        try {
            connectionManager.getMessage();
        } catch (WrongArgumentException e) {
            ioManager.getOutputManager().write(e.getMessage());
            return;
        }
        APPLOGGER.info("Загружен файл для работы");
        while (ioManager.isProcessStillWorks()) {
            readAndExecute();
        }
    }

    public void readAndExecute() throws PrintException, ClassNotFoundException {
        try {
            ioManager.getOutputManager().write("Введите команду!");
            String readLine = ioManager.getInputManager().read(ioManager);
            String command = parseCommand(readLine);
            Serializable args;
            try {
                args = readArgs(command, readLine);
            } catch (WrongArgumentException e) {
                ioManager.getOutputManager().write(e.getMessage());
                return;
            } catch (IOException e) {
                ioManager.getOutputManager().write("Файл не найден");
                return;
            }
            if ("exit".equals(command)) {
                ioManager.exitProcess();
            }
            try {
                connectionManager.sendToServer(commands.get(command), args);
                } catch (IOException e) {
                    ioManager.getOutputManager().write("Ошибка при отправке на сервер");
                    ioManager.exitProcess();
                    return;
                }
                try {
                   ServerMessage serverMessage = connectionManager.getMessage();
                    String answer = serverMessage.getArguments().toString();
                    ioManager.getOutputManager().write(answer);
                } catch (IOException e) {
                    ioManager.getOutputManager().write("Ошибка чтения");
                } catch (WrongArgumentException e) {
                    ioManager.getOutputManager().write("Неверные входные данные");
                }
        } catch (NoSuchElementException e) {
            ioManager.exitProcess();
            } catch (WrongCommandException e) {
            ioManager.getOutputManager().write("Такой команды не существует");
        }
    }
        private String parseCommand(String readLine)  throws WrongCommandException {
            String command;
                command = CommandParser.commandParse(readLine, ioManager);
                if (!commands.containsKey(command)) {
                    throw new WrongCommandException();
                }
            APPLOGGER.info("Из входных данных получена команда");
            return command;
        }
    private String parseArgs(String command, String readLine) {
        String readLine1 = (readLine.replace(command, ""));
        if (readLine1.length() >= 1 && readLine1.charAt(0) == ' ') {
            readLine1 = readLine1.substring(1);
        }
        return readLine1;
    }

    private Serializable readArgs(String command, String commandArguments1) throws WrongArgumentException, PrintException, IOException, ClassNotFoundException {
        String commandArguments = parseArgs(command, commandArguments1);
        Serializable arguments = commandArguments;
        if ("add".equals(command)) {
          arguments = parseAdd(commandArguments);
        }
        if ("remove_by_id".equals(command) || "remove_lower".equals(command)) {
            arguments = argumentParser.parseArgFromString(commandArguments, Objects::nonNull, Integer::parseInt);
        }
        if ("update_id".equals(command)) {
            arguments = parseUpdate(commandArguments);
        }
        if ("execute_script".equals(command)) {
            if (ioManager.getInputManager().containsFileInStack(commandArguments)) {
                throw new WrongArgumentException("Угроза рекурсии!");
            }
            ioManager.getInputManager().setFileReading(true, commandArguments);
        }
        if ("add".equals(command) || "execute_script".equals(command) || "remove_by_id".equals(command) || "remove_lower".equals(command) || "update_id".equals(command)) {
            APPLOGGER.info("Получены аргументы");
            return arguments;
        } else {
            if ("".equals(commandArguments)) {
                APPLOGGER.info("Получены аргументы");
                return "";
            }
            throw new WrongArgumentException("Неверные аргументы");

        }
    }

    private Serializable parseAdd(String commandArguments) throws WrongArgumentException {
        if (!argumentParser.checkIfTheArgsEmpty(commandArguments)) {
            throw new WrongArgumentException("Неверные аргументы");
        }
        try {
            return readAddArgs();
        } catch (IOException | PrintException e) {
            ioManager.exitProcess();
            throw new WrongArgumentException("");
        }
    }

    private Serializable parseUpdate(String commandArguments) throws WrongArgumentException, PrintException, IOException, ClassNotFoundException {
        if (argumentParser.checkIfTheArgsEmpty(commandArguments)) {
            throw new WrongArgumentException("Неверные аргументы");
        }
        connectionManager.sendToServer(new DragonByIdCommand(), Integer.parseInt(commandArguments));
        ServerMessage serverMessage = connectionManager.getMessage();
        if (serverMessage.getResponseStatus().equals(ResponseCode.OK)) {
            Dragon dragon = (Dragon) readAddArgs();
            dragon.addAttributes(new Date(), Integer.parseInt(commandArguments));
            return dragon;
        } else {
            throw new WrongArgumentException(serverMessage.getArguments().toString());
        }
    }
    private Serializable readAddArgs() throws IOException, PrintException {
        ArgumentReader argumentReader = new ArgumentReader(ioManager);
            String name = argumentReader.readName();
            Coordinates coordinates = argumentReader.readCoordinates();
            long age = argumentReader.readAge();
            float wingspan = argumentReader.readWingspan();
            Color color = argumentReader.readColor();
            DragonType type = argumentReader.readType();
            DragonCave cave = argumentReader.readCave();
            return new Dragon(name, coordinates, age, wingspan, color, type, cave);
    }




}
