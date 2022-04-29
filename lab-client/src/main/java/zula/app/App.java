package zula.app;


import zula.client.ConnectionManager;
import zula.common.commands.Command;
import zula.common.commands.DragonByIdCommand;
import zula.common.commands.GetListOfCommands;
import zula.common.commands.LoginCommand;
import zula.common.commands.RegisterCommand;
import zula.common.data.Color;
import zula.common.data.Coordinates;
import zula.common.data.Dragon;
import zula.common.data.DragonCave;
import zula.common.data.DragonType;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.GetServerMessageException;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.SendException;
import zula.common.exceptions.WrongArgumentException;
import zula.common.exceptions.WrongCommandException;
import zula.common.util.ArgumentParser;
import zula.common.util.IoManager;
import zula.util.ArgumentReader;
import zula.util.CommandParser;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.logging.Logger;


public class App {
    private static final Logger APPLOGGER = Logger.getLogger("App logger");
    private final IoManager ioManager;
    private final ConnectionManager connectionManager;
    private HashMap<String, Command> commands;
    private final ArgumentParser argumentParser = new ArgumentParser();
    private String login;
    private String password;
    public App(IoManager ioManager1, ConnectionManager connectionManager1) {
        ioManager = ioManager1;
        connectionManager = connectionManager1;
    }

    public void startApp() throws PrintException, ClassNotFoundException {

        try {
            authenticate();
        } catch (WrongArgumentException e) {
            return;
        }
        try {
            connectionManager.sendToServer(new GetListOfCommands(),  new Serializable[]{""});
            ServerMessage serverMessage = connectionManager.getMessage();
            commands = (HashMap<String, Command>) serverMessage.getArguments()[0];
        } catch (SendException | GetServerMessageException | ClassCastException e) {
            ioManager.getOutputManager().write("Не удалось получить список доступных команд");
            return;
        }
        while (ioManager.isProcessStillWorks()) {
            readAndExecute();
        }
    }
    private void authenticate() throws PrintException, WrongArgumentException {
        try {
            ioManager.getOutputManager().write("Если вы новый пользователь, зарегистрируйтесь, введя команду register, иначе введите login");
            String command = ioManager.getInputManager().read(ioManager); //TODO зачем нужен ioManager в параметре
            if ("login".equals(command)) {
                LoginCommand loginCommand = new LoginCommand();
                Serializable[] args = readArgsForLoginAndRegistration();
                connectionManager.sendToServer(loginCommand, args);
            } else if ("register".equals(command)) {
                RegisterCommand registerCommand = new RegisterCommand();
                Serializable[] args = readArgsForLoginAndRegistration();
                connectionManager.sendToServer(registerCommand, args);
            } else {
                ioManager.getOutputManager().write("Неверная команда");
                throw new WrongArgumentException();
            }
            connectionManager.setLogin(login);
            connectionManager.setPassword(password);
            ServerMessage serverMessage = connectionManager.getMessage();
            if (serverMessage.getResponseStatus() == ResponseCode.OK) {
                ioManager.getOutputManager().write(serverMessage.getArguments()[0]);
            } else {
                ioManager.getOutputManager().write(serverMessage.getArguments()[0]);
                throw new WrongArgumentException();
            }
        } catch (SendException | GetServerMessageException e) {
            e.printStackTrace();
        }
    }
    private Serializable[] readArgsForLoginAndRegistration() throws PrintException {
        ioManager.getOutputManager().write("Введите логин");
        login = ioManager.getInputManager().read(ioManager);
        ioManager.getOutputManager().write("Введите пароль");
        password = ioManager.getInputManager().read(ioManager);
        return new Serializable[]{login, password};
    }


    private void readAndExecute() throws PrintException {
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
            }
            if ("exit".equals(command)) {
                ioManager.exitProcess();
            }
            connectionManager.sendToServer(commands.get(command), new Serializable[]{args});
            ServerMessage serverMessage = connectionManager.getMessage();
            String answer = serverMessage.getArguments()[0].toString();
            ioManager.getOutputManager().write(answer);
        } catch (NoSuchElementException e) {
            ioManager.exitProcess();
        } catch (WrongCommandException e) {
            ioManager.getOutputManager().write("Такой команды не существует");
        } catch (SendException e) {
            ioManager.getOutputManager().write("Ошибка при отправке на сервер");
            ioManager.exitProcess();
        } catch (GetServerMessageException e) {
            ioManager.getOutputManager().write("Ошибка при получении ответа");
            ioManager.exitProcess();
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

    private Serializable readArgs(String command, String commandArguments1) throws WrongArgumentException, PrintException, GetServerMessageException, SendException {
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
            try {
                ioManager.getInputManager().setFileReading(true, commandArguments);
            } catch (FileNotFoundException e) {
                throw new WrongArgumentException("Файл не найден или отсутствуют права доступа");
            }
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
        } catch (PrintException e) {
            ioManager.exitProcess();
            throw new WrongArgumentException("");
        }
    }

    private Serializable parseUpdate(String commandArguments) throws WrongArgumentException, PrintException, SendException, GetServerMessageException {
        if (argumentParser.checkIfTheArgsEmpty(commandArguments)) {
            throw new WrongArgumentException("Неверные аргументы");
        }
        ServerMessage serverMessage;
        connectionManager.sendToServer(new DragonByIdCommand(), new Serializable[]{Integer.parseInt(commandArguments)});
        serverMessage = connectionManager.getMessage();

        if (serverMessage.getResponseStatus().equals(ResponseCode.OK)) {
            Dragon dragon = (Dragon) readAddArgs();
            dragon.setId(Integer.parseInt(commandArguments));
            return dragon;
        } else {
            throw new WrongArgumentException(serverMessage.getArguments()[0].toString());
        }
    }
    private Serializable readAddArgs() throws PrintException {
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
