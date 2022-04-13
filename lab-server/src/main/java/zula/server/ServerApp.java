package zula.server;

import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.server.util.Client;
import zula.server.util.ListManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerApp {
    private final int intSize = 4;
    private final Logger appLogger = Logger.getLogger("App logger");
    public void startApp(ListManager listManager, ArrayList<Client> clients) throws IOException, PrintException {
        for (Client client : clients) {
            try {
                if (client.getSocket().getInputStream().available() >= intSize && client.getExpectedBytes() == null) {
                    byte[] expectedBytes = new byte[intSize];
                    client.getSocket().getInputStream().read(expectedBytes);
                    ByteBuffer intBuffer = ByteBuffer.wrap(expectedBytes);
                    if (client.getObjectInputStream() == null) {
                        client.setExpectedBytes(intBuffer.getInt() - intSize);
                    } else {
                        client.setExpectedBytes(intBuffer.getInt());
                    }
                    if (client.getObjectInputStream() == null) {
                        client.setObjectInputStream(new ObjectInputStream(client.getSocket().getInputStream()));
                    }
                }
                if (client.getExpectedBytes() != null && client.getSocket().getInputStream().available() == client.getExpectedBytes()) {
                    ServerMessage serverMessage = (ServerMessage) client.getObjectInputStream().readObject();
                    serverMessage.getCommand().execute(client.getIoManager(), listManager, serverMessage.getArguments());
                    appLogger.info("Успешное выполнение команды");
                    client.setExpectedBytes(null);
                }
            } catch (PrintException | ClassNotFoundException e) {
                appLogger.severe("Ошибка выполнения команды");
            } catch (IOException e) {
                appLogger.severe("Ошибка соединения");
                //тут, наверное, стоит завершить работу приложения, так как IOException может возникнуть
                //только при readObject() => считывание данных больше невозможно
                clients.remove(client);
            }
        }
        }

}
