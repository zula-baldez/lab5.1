package zula.client;

import zula.common.commands.Command;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.GetServerMessageException;
import zula.common.exceptions.PrintException;
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
    private ObjectInputStream objectDeserializer; //А само использования этих объектов обусловлено тем, что в java нет специальных методов для сериализации напрямую
    private boolean isItNotFirstDeserialization = false;
    private boolean isItNotFirstSerialization = false;
    private final int buffSize = 5555;
    private final int waitingTime = 1000;
    private final int maxIterationsOnTheWaitingLoop = 30; //ждем ответа не более 30 секунд
    private final int intByteSize = 4;
    public ConnectionManager(String ip, int port, IoManager ioManager1) {
        serverIp = ip;
        serverPort = port;
        ioManager = ioManager1;
    }

    private void connect() throws PrintException, IOException {

            client = SocketChannel.open();
            client.configureBlocking(false);
            client.connect(new InetSocketAddress(serverIp, serverPort));
            client.finishConnect();
            if (!client.isConnected()) {
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
        writerToObjectDeserializationBuffer = new PipedInputStream(objectDeserializationBuffer, buffSize * buffSize);
    }


    public void sendToServer(Command command, Serializable args) throws SendException {
        try {
            ServerMessage serverMessage = new ServerMessage(command, args, ResponseCode.OK);
            ByteBuffer byteBuffer = ByteBuffer.wrap(serialize(serverMessage));
            while (byteBuffer.hasRemaining()) {
                client.write(byteBuffer);
            }
            CONNECTIONLOGGER.info("Успешная отправка на сервер");
        }  catch (IOException e) {
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
            ByteBuffer intParserBuffer = ByteBuffer.allocate(intByteSize);
            while (counter > 0) { //читаем первые 4 символа - количество байт во входных данных
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
                    CONNECTIONLOGGER.severe("Something happened???");
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
        if (isItNotFirstSerialization) {
            objectSerializationBuffer.reset();
        }
        objectSerializer.writeObject(serverMessage);
        isItNotFirstSerialization = true;
        return objectSerializationBuffer.toByteArray();
    }

}

