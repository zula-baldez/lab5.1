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
    private static final int SERVERPORT = 4004;
    private static final Logger CLIENTLOGGER = Logger.getLogger("ClintLogger");
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        try {
            IoManager ioManager = new IoManager(new InputManager(new InputStreamReader(System.in)), new OutputManager(new OutputStreamWriter(System.out)));
            ConnectionManager connectionManager = new ConnectionManager("127.0.0.1", SERVERPORT, ioManager);
            try {
                connectionManager.connectToServer();
                CLIENTLOGGER.info("Подключение установлено");
            } catch (IOException e) {
                ioManager.getOutputManager().write("Не удалось подключиться к серверу");
                CLIENTLOGGER.severe("Ошибка при соединении");
                return;
            }
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
            if (args.length != 1) {
                ioManager.getOutputManager().write("Неверный аргумент командной строки");
                return;
            }
            try {
                app.startApp(args[0]);
            } catch (IOException e) {
                ioManager.getOutputManager().write("Соединение потеряно");
            } catch (ClassNotFoundException e) {
                ioManager.getOutputManager().write("Ответ сервера не соответствует протоколу обмена сообщениями");
            }
        } catch (PrintException e) {
            System.out.println("Запись невозможна");
        }
    }
}
