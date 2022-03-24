package zula.app;


import zula.client.ConnectionManager;
import zula.common.commands.*;
import zula.common.data.*;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.WrongArgumentException;
import zula.common.exceptions.WrongCommandException;
import zula.common.util.*;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;


public class App {
    private final IoManager ioManager;
    private final ConnectionManager connectionManager;
    HashMap<String, Command> commands;

    public App(IoManager ioManager1, ConnectionManager connectionManager1,  HashMap<String, Command> commands1) {
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
        }
        while (ioManager.isProcessStillWorks()) {
            readAndExecute();
        }
    }

    public void readAndExecute() throws PrintException, ClassNotFoundException {
        try {
            ioManager.getOutputManager().write("Введите команду!");
            String readLine;
            readLine = ioManager.getInputManager().read(ioManager);
            String command;
            try {
                command = CommandParser.commandParse(readLine, ioManager);
                if (!commands.containsKey(command)) {
                    throw new WrongCommandException();
                }

            } catch (WrongCommandException e) {
                ioManager.getOutputManager().write("Такой команды не существует. Повторите ввод");
                return;
            }
            readLine = (readLine.replace(command, ""));
            if (readLine.length() >= 1 && readLine.charAt(0) == ' ') {
                readLine = readLine.substring(1);
            }

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

            if (command.equals("exit")) {
                ioManager.exitProcess();
            }
            if (!command.equals("execute_script")) {
                try {
                    connectionManager.sendToServer(commands.get(command), args);
                } catch (IOException e) {
                    ioManager.getOutputManager().write("Ошибка при отправке на сервер");
                    ioManager.exitProcess();
                    return;
                }

                ServerMessage serverMessage;
                try {
                    serverMessage = connectionManager.getMessage();
                } catch (IOException e) {
                    ioManager.getOutputManager().write("Ошибка чтения");
                    return;
                } catch (WrongArgumentException e) {
                    ioManager.getOutputManager().write("Неверные входные данные");
                    return;
                }
                String answer = serverMessage.getArguments().toString();
                ioManager.getOutputManager().write(answer);

            }
        }catch (NoSuchElementException e) {
            ioManager.exitProcess();
            return;
            }
        }




    private Serializable readArgs(String command, String commandArguments) throws WrongArgumentException, PrintException, IOException, ClassNotFoundException {
        ArgumentParser argumentParser = new ArgumentParser();
        if (command.equals("average_of_wingspan") || command.equals("clear") || command.equals("help") || command.equals("info") || command.equals("print_ascending") ||
                command.equals("print_field_ascending_wingspan") || command.equals("reorder") || command.equals("save") || command.equals("show") || command.equals("remove_last")) {
            if (argumentParser.checkIfTheArgsEmpty(commandArguments)) return null;
        }
        if (command.equals("add")) {
            if (!argumentParser.checkIfTheArgsEmpty(commandArguments)) throw new WrongArgumentException();
            try {
                return readAddArgs();
            } catch (IOException | PrintException e) {
                // TODO
            }
        }
        if (command.equals("remove_by_id") || command.equals("remove_lower")) {
            return argumentParser.parseArgFromString(commandArguments, Objects::nonNull, Integer::parseInt);
        }
        if (command.equals("update_id")) {
            if (argumentParser.checkIfTheArgsEmpty(commandArguments)) throw new WrongArgumentException("Такого id не существует");
            connectionManager.sendToServer(new DragonByIdCommand(), Integer.parseInt(commandArguments));
            ServerMessage serverMessage = connectionManager.getMessage();
            if(serverMessage.getResponseStatus()==ResponseCode.OK) {
                Dragon dragon = (Dragon) readAddArgs();
                dragon.addAttributes(new Date(), Integer.parseInt(commandArguments));
                return dragon;
            } else {
                throw new WrongArgumentException();
            }
        }
        if (command.equals("execute_script")) {
            if(Objects.nonNull(commandArguments)) {

                        if (ioManager.getInputManager().containsFileInStack(commandArguments)) {
                    throw new WrongArgumentException("Угроза рекурсии!");
                }
                ioManager.getInputManager().setFileReading(true, commandArguments);
            }
        }

        return commandArguments;

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
