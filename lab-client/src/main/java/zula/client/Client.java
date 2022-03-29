package zula.client;

import zula.app.App;
import zula.common.commands.Command;
import zula.common.commands.GetListOfCommands;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.WrongArgumentException;
import zula.common.util.IoManager;
import zula.common.util.InputManager;
import zula.common.util.OutputManager;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.logging.Logger;

public final class Client {
    private static int port;
    private static String ip = "127.0.0.1";
    private static final Logger CLIENTLOGGER = Logger.getLogger("ClintLogger");

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        try {
            IoManager ioManager = new IoManager(new InputManager(new InputStreamReader(System.in)), new OutputManager(new OutputStreamWriter(System.out)));
            if (args.length != 2) {
                return;
            }
            ip = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                ioManager.getOutputManager().write("Неверные аргументы командной строки");
                return;
            }
            ConnectionManager connectionManager = new ConnectionManager(ip, port, ioManager);
            connect(connectionManager, ioManager);
            HashMap<String, Command> commands;
            try {
                connectionManager.sendToServer(new GetListOfCommands(), "");
                ServerMessage serverMessage = connectionManager.getMessage();
                commands = (HashMap<String, Command>) serverMessage.getArguments();
            } catch (IOException | ClassNotFoundException | WrongArgumentException e) {
                ioManager.getOutputManager().write("Не удалось получить список доступных команд");
                return;
            }
            CLIENTLOGGER.info("Получен список доступных команд");
            ioManager.getOutputManager().write("Список существующих команд загружен успешно.");
            App app = new App(ioManager, connectionManager, commands);
            try {
                app.startApp();
            } catch (IOException e) {
                ioManager.getOutputManager().write("Соединение потеряно");
            } catch (ClassNotFoundException e) {
                ioManager.getOutputManager().write("Ответ сервера не соответствует протоколу обмена сообщениями");
            }
        } catch (PrintException e) {
            System.out.println("Запись невозможна");
        }
    }


    private static void connect(ConnectionManager connectionManager, IoManager ioManager) throws PrintException {
        try {
            connectionManager.connectToServer();
            CLIENTLOGGER.info("Подключение установлено");
        } catch (IOException e) {
            ioManager.getOutputManager().write("Не удалось подключиться к серверу");
            CLIENTLOGGER.severe("Ошибка при соединении");
        }

    }

}
