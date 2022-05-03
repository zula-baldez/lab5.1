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
import java.util.logging.Logger;

public class ServerApp {
    private final Logger appLogger = Logger.getLogger("App logger");
    private boolean isClientAlive = true;

    public void startApp(Client client) throws PrintException {
        try {
            while (isClientAlive) {
                ServerMessage serverMessage = (ServerMessage) client.getObjectInputStream().readObject();
                requestHandle(serverMessage, client);
                appLogger.info("Успешное выполнение команды");
            }
        } catch (ClassNotFoundException e) {
            appLogger.severe("Ошибка выполнения команды");
        } catch (IOException e) {
            appLogger.severe("Ошибка соединения");
            //тут, наверное, стоит завершить работу приложения, так как IOException может возникнуть
            //только при readObject() => считывание данных больше невозможно
            isClientAlive = false;
        }
    }

        private void requestHandle(ServerMessage serverMessage, Client client) {
            Thread thread = new Thread(() -> {
                try {
                    if (!(serverMessage.getCommand() instanceof LoginCommand || serverMessage.getCommand() instanceof RegisterCommand)) {
                        if (client.getSqlManager().login(serverMessage.getName(), serverMessage.getPassword(), client) == ResponseCode.ERROR) {
                            client.getIoManager().getOutputManager().write("Ошибка при проверке пароля");
                        }
                    }

                    ServerMessage answer = serverMessage.getCommand().execute(client.getIoManager(), client, serverMessage.getArguments());
                    answerForResponse(answer, client);
                } catch (PrintException printException) {
                    printException.printStackTrace();
                }
            });
            thread.start();
        }
        private final ExecutorService service = Executors.newFixedThreadPool(2 * 2 * 2);

        private synchronized void answerForResponse (ServerMessage serverMessage, Client client) throws PrintException {
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

