package zula.server;

import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.server.commands.Save;
import zula.server.commands.ServerExit;
import zula.server.util.ListManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

public class ServerApp {
    private boolean serverStillWorks = true;
    private final Logger appLogger = Logger.getLogger("App logger");
    private final Scanner scanner = new Scanner(System.in);
    public void startApp(IoManager ioManager, ObjectInputStream in, ListManager listManager) throws IOException, PrintException {
        while (ioManager.isProcessStillWorks()) {
            try {
            if (System.in.available() > 0) {
                String command = scanner.nextLine();
                if ("exit".equals(command)) {
                    ServerExit serverExit = new ServerExit();
                    serverExit.execute(ioManager, listManager, "");
                    appLogger.info("До свидания!");
                    serverStillWorks = false;
                    break;
                }
                if ("save".equals(command)) {
                    Save save = new Save();
                    save.execute(ioManager, listManager);
                    appLogger.info("Команда выполнена!");
                }
            }
            } catch (NoSuchElementException e) {
                serverStillWorks = false;
                break;
            }
            try {
                ServerMessage serverMessage = (ServerMessage) in.readObject();
                serverMessage.getCommand().execute(ioManager, listManager, serverMessage.getArguments());
                appLogger.info("Успешное выполнение команды");
            } catch (PrintException | ClassNotFoundException e) {
                appLogger.severe("Ошибка выполнения команды");
            } catch (IOException e) {
                appLogger.severe("Ошибка соединения");
                //тут, наверное, стоит завершить работу приложения, так как IOException может возникнуть
                //только при readObject() => считывание данных больше невозможно
                ioManager.exitProcess();
            }
        }
    }

    public boolean isServerStillWorks() {
        return serverStillWorks;
    }


}
