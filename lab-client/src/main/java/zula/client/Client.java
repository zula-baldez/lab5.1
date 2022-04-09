package zula.client;

import zula.app.App;
import zula.common.commands.Command;
import zula.common.commands.GetListOfCommands;
import zula.common.data.ServerMessage;
import zula.common.exceptions.GetServerMessageException;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.SendException;
import zula.common.util.InputManager;
import zula.common.util.IoManager;
import zula.common.util.OutputManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.logging.Logger;

public final class Client {
    private static int port;
    private static String ip;
    private static final Logger CLIENTLOGGER = Logger.getLogger("ClientLogger");
    private static final IoManager IO_MANAGER = new IoManager(new InputManager(new InputStreamReader(System.in)), new OutputManager(new OutputStreamWriter(System.out)));

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws PrintException {
            if (args.length != 2) {
                IO_MANAGER.getOutputManager().write("Неверные аргументы командной строки");
                return;
            }
            ip = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                IO_MANAGER.getOutputManager().write("Неверные аргументы командной строки");
            }
            ConnectionManager connectionManager = new ConnectionManager(ip, port, IO_MANAGER);
            try {
                connectionManager.connectToServer();
                CLIENTLOGGER.info("Подключение установлено");
            } catch (IOException e) {
                IO_MANAGER.getOutputManager().write("Не удалось подключиться к серверу");
                CLIENTLOGGER.severe("Ошибка при соединении");
                return;
            }
            HashMap<String, Command> commands;
            try {
                connectionManager.sendToServer(new GetListOfCommands(), "");
                ServerMessage serverMessage = connectionManager.getMessage();
                commands = (HashMap<String, Command>) serverMessage.getArguments();
            } catch (SendException | GetServerMessageException e) {
                IO_MANAGER.getOutputManager().write("Не удалось получить список доступных команд");
                return;
            }
            CLIENTLOGGER.info("Получен список доступных команд");
            IO_MANAGER.getOutputManager().write("Список существующих команд загружен успешно.");
            App app = new App(IO_MANAGER, connectionManager, commands);
            try {
                app.startApp();
            } catch (ClassNotFoundException e) {
                IO_MANAGER.getOutputManager().write("Ответ сервера не соответствует протоколу");
            }

    }




}
