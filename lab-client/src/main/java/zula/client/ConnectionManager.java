package zula.client;

import zula.common.commands.Command;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.GetServerMessageException;
import zula.common.exceptions.SendException;
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
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.logging.Logger;




public class ConnectionManager {
    private static final Logger CONNECTIONLOGGER = Logger.getLogger("Connection logger");
    private SocketChannel client;
    private final String serverIp;
    private final int serverPort;
    private final IoManager ioManager;
    private final ByteArrayOutputStream objectSerializationBuffer = new ByteArrayOutputStream(); //Нужны для сериализации и десериализации
    private ObjectOutputStream objectSerializer; //проблема в том, что у Object stream'ов при первом обращении существуют специальные символы
    private final PipedOutputStream objectDeserializationBuffer = new PipedOutputStream(); //то есть первый поток байтов отличается от последующих
    private PipedInputStream writerToObjectDeserializationBuffer; //для этого приходится сохранять созданные объекты, чтобы потоки байтов обрабатывались корректно
    private ObjectInputStream objectDeserializer; //А само использования этих объектов обусловлено тем, что в java нет специальных методов для сериализации напрямую
    private boolean isItNotFirstDeserialization = false;
    private final int buffSize = 5555;
    private final int waitingTime = 100;
    private final int maxAttemps = 10;
    private final int maxIterationsOnTheWaitingLoop = 30; //ждем ответа не более 30 секунд
    private final int intByteSize = 4;
    private int userId;
    private String login;
    private String password;
    public ConnectionManager(String ip, int port, IoManager ioManager) {
        serverIp = ip;
        serverPort = port;
        this.ioManager = ioManager;
    }
    private void connect() throws IOException {

        client = SocketChannel.open();
        client.configureBlocking(false);
        try {
            client.connect(new InetSocketAddress(serverIp, serverPort));
        } catch (UnresolvedAddressException e) {
            CONNECTIONLOGGER.severe("Неверный адрес");
            throw new IOException();
        }

        client.finishConnect();
        if (!client.isConnected()) {
            try {
                Thread.sleep(waitingTime * maxAttemps);
            } catch (InterruptedException ee) {
                return;
            }
            if (!client.isConnected()) {
                throw new IOException();
            }
        }
    }


    public void connectToServer() throws IOException {
        connect();
        objectSerializer = new ObjectOutputStream(objectSerializationBuffer);
        writerToObjectDeserializationBuffer = new PipedInputStream(objectDeserializationBuffer, buffSize * buffSize);


    }
    public int getUserId() {
        return userId;
    }
    public void sendToServer(Command command, Serializable[] args) throws SendException {
        try {
            ServerMessage serverMessage = new ServerMessage(command, args, ResponseCode.OK);
            serverMessage.setIndentification(login, password);
            ByteBuffer byteBuffer = ByteBuffer.wrap(serialize(serverMessage));
            while (byteBuffer.hasRemaining()) {
                client.write(byteBuffer);
            }
            CONNECTIONLOGGER.info("Успешная отправка на сервер");
        } catch (IOException e) {
            throw new SendException();
        }
    }

    public ServerMessage getMessage() throws GetServerMessageException {
        try {
            ByteArrayOutputStream storageOfInputBytes = new ByteArrayOutputStream();
            ByteBuffer connectionBuffer = ByteBuffer.allocate(buffSize);
            int amountOfReadBytes = 0;
            int amountOfExpectedBytes = 0;
            int counter = maxIterationsOnTheWaitingLoop;
            ByteBuffer intParserBuffer = ByteBuffer.allocate(intByteSize); //парсит байты в инт
            while (counter > 0) { //читаем первые 4 символа - количество байтов во входных данных
                client.read(intParserBuffer);
                if (intParserBuffer.position() == intByteSize) {
                    intParserBuffer.flip();
                    amountOfExpectedBytes = intParserBuffer.getInt();
                    break;
                }
                counter--;
                try {
                    Thread.sleep(waitingTime);
                } catch (InterruptedException e) {
                    throw new GetServerMessageException();
                }
            }
            if (counter == 0) {
                throw new GetServerMessageException();
            }
            while (amountOfExpectedBytes > amountOfReadBytes) { //читаем все остальное
                client.read(connectionBuffer);
                connectionBuffer.flip();
                byte[] readBytes = new byte[connectionBuffer.limit()];
                amountOfReadBytes += readBytes.length;
                connectionBuffer.get(readBytes, 0, connectionBuffer.limit());
                storageOfInputBytes.write(readBytes);
                connectionBuffer.clear();
            }
            return deserialize(storageOfInputBytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            throw new GetServerMessageException();
        }
    }


    public ServerMessage deserialize(byte[] data) throws IOException {
        objectDeserializationBuffer.write(data, 0, data.length);
        if (!isItNotFirstDeserialization) {
            objectDeserializer = new ObjectInputStream(writerToObjectDeserializationBuffer);
            isItNotFirstDeserialization = true;
        }
        try {
            return (ServerMessage) objectDeserializer.readObject();
        } catch (ClassNotFoundException e) {
            CONNECTIONLOGGER.severe("ошибка при десериализации");
            ioManager.exitProcess();
            throw new IOException();
        }
    }

    public byte[] serialize(ServerMessage serverMessage) throws IOException {
        objectSerializer.writeObject(serverMessage);
        byte[] resultOfSerialization = new byte[objectSerializationBuffer.size()];
        for (int i = 0; i < resultOfSerialization.length; i++) {
            resultOfSerialization[i] = objectSerializationBuffer.toByteArray()[i];
        }
        objectSerializationBuffer.reset();
        return resultOfSerialization;

    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserId(int id) {
        this.userId = id;
    }
}



