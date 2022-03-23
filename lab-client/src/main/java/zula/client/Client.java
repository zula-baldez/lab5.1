package zula.client;

import zula.app.App;
import zula.common.commands.Add;
import zula.common.commands.Command;
import zula.common.commands.GetListOfCommands;
import zula.common.commands.Show;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.InputManager;
import zula.common.util.OutputManager;


import java.io.*;
import java.util.HashMap;

public final class Client {

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        try {
            IoManager ioManager = new IoManager(new InputManager(new InputStreamReader(System.in)), new OutputManager(new OutputStreamWriter(System.out)));
            ConnectionManager connectionManager = new ConnectionManager("127.0.0.1", 4004, ioManager);

            try {
                connectionManager.connectToServer();

            } catch(IOException e) {
                ioManager.getOutputManager().write("Не удалось подключиться к серверу");
                return;
            }
            try {
                connectionManager.sendToServer(new GetListOfCommands(), "");

                ioManager.getOutputManager().write("Список существующих команд загружен успешно.");

            } catch (IOException e) {
                ioManager.getOutputManager().write("Не удалось получить список существующий команд");
                return;
            }

            HashMap<String, Command> commands;
            try {
                 commands = connectionManager.getMessage().getCommands();


            } catch (IOException | ClassNotFoundException e) {
                ioManager.getOutputManager().write("Не удалось получить список доступных команд");
                return;
            }
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
                e.printStackTrace();
            }
        } catch (PrintException e) {
            return;
        }

    }
}
