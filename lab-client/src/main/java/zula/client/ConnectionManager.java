package zula.client;

import zula.common.commands.Command;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.WrongArgumentException;
import zula.common.util.IoManager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;

import java.net.SocketAddress;

import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class ConnectionManager {
    private static final Logger CONNECTIONLOGGER = Logger.getLogger("Connection logger");
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int countOfAccessAttemps = 0;
    private String serverIp;
    private int serverPort;
    private IoManager ioManager;
    private final int maxAttemps = 5;
    public ConnectionManager(String ip, int port, IoManager ioManager1) {
        serverIp = ip;
        serverPort = port;
        ioManager = ioManager1;
    }

    private void connect() throws PrintException, IOException {
        try {
            SocketChannel client = SocketChannel.open();
            SocketAddress sAddr = new InetSocketAddress(serverIp, serverPort);
            client.connect(sAddr);
            client.finishConnect();
            in = new ObjectInputStream(client.socket().getInputStream());
            out = new ObjectOutputStream(client.socket().getOutputStream());
        } catch (IOException e) {
            countOfAccessAttemps++;
            ioManager.getOutputManager().write("Попытка подключения...");
            if (countOfAccessAttemps > maxAttemps) {
                CONNECTIONLOGGER.severe("Не удалось установить соединение");
                throw new IOException();
            } else {
                connect();
            }

        }
    }

    public void connectToServer() throws PrintException, IOException {
        connect();
        CONNECTIONLOGGER.info("Установлено соединение");
        ioManager.getOutputManager().write("Подключение установлено!");
    }


    public void sendToServer(Command command, Serializable args) throws PrintException, IOException {
        try {
            countOfAccessAttemps = 0;
            out.writeObject(new ServerMessage(command, args, ResponseCode.OK));
            CONNECTIONLOGGER.info("Успешная отправка на сервер");
        } catch (IOException e) {
            connectToServer();
            out.writeObject(new ServerMessage(command, args, ResponseCode.OK));
        }
        }

    public ServerMessage getMessage() throws IOException, ClassNotFoundException, WrongArgumentException {
        return (ServerMessage) in.readObject();

    }

}

