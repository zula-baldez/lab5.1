package zula.server;

import zula.common.commands.LoginCommand;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.server.util.Client;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerApp {
    private final Logger appLogger = Logger.getLogger("App logger");
    private boolean isClientAlive = true;
    private final ExecutorService service = Executors.newFixedThreadPool(2 * 2 * 2, r -> {
                Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setDaemon(true);
                return thread;
            }
    );
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
            appLogger.info("client disconnected");
            //тут, наверное, стоит завершить работу приложения, так как IOException может возникнуть
            //только при readObject() => считывание данных больше невозможно
            isClientAlive = false;
        }
    }

        private void requestHandle(ServerMessage serverMessage, Client client) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (serverMessage.getCommand().isNeedsLoginCheck()) {
                            LoginCommand loginCommand = new LoginCommand();
                            ServerMessage answer = loginCommand.doInstructions(client.getIoManager(), client, new Serializable[]{serverMessage.getName(), serverMessage.getPassword()});
                            if (answer.getResponseStatus() == ResponseCode.ERROR) {
                                answerForResponse(serverMessage, client);
                                return;
                            }
                        }

                        ServerMessage answer = serverMessage.getCommand().execute(client.getIoManager(), client, serverMessage.getArguments());
                        answerForResponse(answer, client);
                    } catch (PrintException e) {
                        appLogger.severe("Ошибка при отправке ответа");
                    }
                }
            }
          );
            thread.setDaemon(true);
            thread.start();
        }


        private synchronized void answerForResponse(ServerMessage serverMessage, Client client) throws PrintException {
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

