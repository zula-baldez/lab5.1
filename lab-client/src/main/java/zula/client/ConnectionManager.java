package zula.client;

import zula.common.commands.Command;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.WrongArgumentException;
import zula.common.util.IoManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;




public class ConnectionManager {
    private static final Logger CONNECTIONLOGGER = Logger.getLogger("Connection logger");
    private SocketChannel client;
    private int countOfAccessAttemps = 0;
    private final String serverIp;
    private final int serverPort;
    private final IoManager ioManager;
    private final int maxAttemps = 5;
    private ByteArrayOutputStream objectSerializationBuffer = new ByteArrayOutputStream(); //Нужны для сериализации и десериализации
    private ObjectOutputStream objectSerializer; //проблема в том, что у Object stream'ов при первом обращении существуют специальные символы
    private final PipedOutputStream objectDeserializationBuffer = new PipedOutputStream(); //то есть первый поток байт отличается от последующих
    private PipedInputStream writerToObjectDeserializationBuffer; //для этого приходится сохранять созданные объекты, чтобы потоки байт обрабатывались корректно
    private ObjectInputStream objectDeserealizer; //А само использования этих объектов обусловлено тем, что в java нет специальных методов для сериализации напрямую
    private boolean isItNotFirstDeserialization = false;
    private boolean isItNotFirstSerialization = false;
    private final int buffSize = 5555;
    private final int waitingTime = 1000;
    public ConnectionManager(String ip, int port, IoManager ioManager1) {
        serverIp = ip;
        serverPort = port;
        ioManager = ioManager1;
    }

    private void connect() throws PrintException, IOException {
        try {
            client = SocketChannel.open();
            client.configureBlocking(false);
            client.connect(new InetSocketAddress(serverIp, serverPort));
            client.finishConnect();
        } catch (IOException e) {
            countOfAccessAttemps++;
            ioManager.getOutputManager().write("Попытка подключения...");
            try {
                Thread.sleep(waitingTime);
            } catch (InterruptedException ee) {
                return;
            }
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
        objectSerializer = new ObjectOutputStream(objectSerializationBuffer);
        writerToObjectDeserializationBuffer = new PipedInputStream(objectDeserializationBuffer, buffSize);
    }


    public void sendToServer(Command command, Serializable args) throws PrintException, IOException {
        try {
            countOfAccessAttemps = 0;
            ServerMessage serverMessage = new ServerMessage(command, args, ResponseCode.OK);
            ByteBuffer byteBuffer = ByteBuffer.wrap(serialize(serverMessage));
            client.write(byteBuffer);
            CONNECTIONLOGGER.info("Успешная отправка на сервер");
        } catch (IOException e) {
            connectToServer();
            ServerMessage serverMessage = new ServerMessage(command, args, ResponseCode.OK);
            ByteBuffer byteBuffer = ByteBuffer.wrap(serialize(serverMessage));

            client.write(byteBuffer);
        } catch (NotYetConnectedException e) {
            throw new IOException();
        }
        }

    public ServerMessage getMessage() throws IOException, ClassNotFoundException, WrongArgumentException {
       try {
            Thread.sleep(waitingTime); //ждем данных
        } catch (InterruptedException e) {
            throw new IOException();
        }
        ByteBuffer byteBuffer  = ByteBuffer.allocate(buffSize);
        client.read(byteBuffer);
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes, 0, byteBuffer.limit());
        return deserialize(bytes);

    }


    public ServerMessage deserialize(byte[] data) throws IOException, ClassNotFoundException {
        objectDeserializationBuffer.write(data, 0, data.length);
        if (!isItNotFirstDeserialization) {
            objectDeserealizer = new ObjectInputStream(writerToObjectDeserializationBuffer);
            isItNotFirstDeserialization = true;
        }
        return (ServerMessage) objectDeserealizer.readObject();
    }

    public byte[] serialize(ServerMessage serverMessage) throws IOException {
        if (isItNotFirstSerialization) {
            objectSerializationBuffer.reset();
        }
        objectSerializer.writeObject(serverMessage);
        isItNotFirstSerialization = true;
        return objectSerializationBuffer.toByteArray();
    }

}

