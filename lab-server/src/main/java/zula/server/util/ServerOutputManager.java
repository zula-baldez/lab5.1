package zula.server.util;

import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.util.OutputManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public class ServerOutputManager extends OutputManager {
    private final ObjectOutputStream outputStreamWriter;

    public ServerOutputManager(ObjectOutputStream outputStream1) throws IOException {
        super(new OutputStreamWriter(outputStream1));
        outputStreamWriter = outputStream1;
    }
    @Override
    public void write(Serializable arg) {
        ServerMessage serverMessage;
        if (!(arg == null)) {
            serverMessage = new ServerMessage(arg, ResponseCode.OK);
        } else {
            serverMessage = new ServerMessage("", ResponseCode.OK);
        }
        try {
            outputStreamWriter.writeObject(serverMessage);
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void writeWithResponse(Serializable arg, ResponseCode responseCode) {
        ServerMessage serverMessage;
        if (!(arg == null)) {
            serverMessage = new ServerMessage(arg, responseCode);
        } else {
            serverMessage = new ServerMessage("", responseCode);
        }
        try {
            outputStreamWriter.writeObject(serverMessage);
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void writeServerMessage(ServerMessage serverMessage) throws IOException {
        outputStreamWriter.writeObject(serverMessage);
    }
}
