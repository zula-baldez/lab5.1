package zula.server;

import zula.common.commands.LoginCommand;
import zula.common.commands.RegisterCommand;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.server.util.Client;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

public class ServerApp {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private final Logger appLogger = Logger.getLogger("App logger");
    private boolean isClientAlive = true;
    public void startApp(Client client) throws PrintException {
        try {
            while (isClientAlive) {
                ServerMessage serverMessage = (ServerMessage) client.getObjectInputStream().readObject();
                if (!(serverMessage.getCommand() instanceof LoginCommand || serverMessage.getCommand() instanceof RegisterCommand)) {
                    if (client.getSqlManager().login(serverMessage.getName(), serverMessage.getPassword(), client).getResponseStatus() == ResponseCode.ERROR) {
                        client.getIoManager().getOutputManager().write("Ошибка при проверке пароля");
                    }
                }

                ServerMessage answer = serverMessage.getCommand().execute(client.getIoManager(), client, serverMessage.getArguments());
                answerForResponse(answer, client);
                appLogger.info("Успешное выполнение команды");

            }
        } catch (PrintException | ClassNotFoundException e) {
            e.printStackTrace();
            appLogger.severe("Ошибка выполнения команды");
        } catch (IOException e) {
            e.printStackTrace();
            appLogger.severe("Ошибка соединения");
            //тут, наверное, стоит завершить работу приложения, так как IOException может возникнуть
            //только при readObject() => считывание данных больше невозможно
            isClientAlive = false;
        }
    }
    private synchronized void answerForResponse(ServerMessage serverMessage, Client client) throws PrintException {
        ExecutorService service = Executors.newFixedThreadPool(2 * 2 * 2);
        service.submit(() -> {
            try {
                client.getIoManager().getOutputManager().writeServerMessage(serverMessage);
            } catch (PrintException e) {
                service.shutdown();

            }
        }
        );
    }
}

