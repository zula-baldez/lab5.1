package zula.server.util;

import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.util.OutputManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

public class ServerOutputManager extends OutputManager {
    private final OutputStream outputStreamWriter; //
    private final ObjectOutputStream serializer; //сериализатор
    private final ByteArrayOutputStream serializationBuffer = new ByteArrayOutputStream(); //нужен для получения количества символов в сериализованном потоке
    private final int sizeOfIntInBytes = 4;
    private final Logger serverOutputLogger = Logger.getLogger("ServerOutput");
    public ServerOutputManager(OutputStream outputStream1) throws IOException {
        super(new OutputStreamWriter(outputStream1));
        outputStreamWriter = outputStream1;
        serializer = new ObjectOutputStream(serializationBuffer);
    }
    @Override
    public void write(Serializable arg)  {
        ServerMessage serverMessage;
        if (!(arg == null)) {
            serverMessage = new ServerMessage(arg, ResponseCode.OK);
        } else {
            serverMessage = new ServerMessage("", ResponseCode.OK);
        }
        try {
            outputStreamWriter.write(serialize(serverMessage));
        } catch (IOException e) {
            serverOutputLogger.severe("Ошибка сериализации");
        }


    }
    @Override
    public void writeWithResponse(Serializable arg, ResponseCode responseCode) {
        ServerMessage serverMessage;
        if (!(arg == null)) {
            serverMessage = new ServerMessage(arg, responseCode);
        } else {
            serverMessage = new ServerMessage("", responseCode);
        }
        try {
            outputStreamWriter.write(serialize(serverMessage));
        } catch (IOException e) {
           serverOutputLogger.severe("Ошибка сериализации");
        }

    }

    @Override
    public void writeServerMessage(ServerMessage serverMessage) {
        try {
        outputStreamWriter.write(serialize(serverMessage));
        } catch (IOException e) {
            serverOutputLogger.severe("Ошибка сериализации");
        }
    }



    private byte[] serialize(ServerMessage serverMessage) throws IOException {
        serializer.writeObject(serverMessage);
        byte[] resultOfSerialization = new byte[serializationBuffer.size() + sizeOfIntInBytes];
        byte[] sizeOfPackage = ByteBuffer.allocate(sizeOfIntInBytes).putInt(serializationBuffer.size()).array();
        System.arraycopy(sizeOfPackage, 0, resultOfSerialization, 0, sizeOfIntInBytes);
        for (int i = sizeOfIntInBytes; i < resultOfSerialization.length; i++) {
            resultOfSerialization[i] = serializationBuffer.toByteArray()[i - sizeOfIntInBytes];
        }
        serializationBuffer.reset();
        return resultOfSerialization;
    }
}
