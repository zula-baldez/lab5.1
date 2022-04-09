package zula.common.util;

import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public class OutputManager {
    private OutputStreamWriter outputStreamWriter;
    public OutputManager(OutputStreamWriter outputStream1) {
        this.outputStreamWriter = outputStream1;
    }
    public void write(Serializable arg) throws PrintException {
        try {
            outputStreamWriter.write(arg.toString());
            outputStreamWriter.write('\n');
            outputStreamWriter.flush();
        } catch (IOException e) {
            throw new PrintException();
        }

    }
    public void writeServerMessage(ServerMessage serverMessage) throws PrintException {
        write(serverMessage.getArguments());
    }
    public void writeWithResponse(Serializable arg, ResponseCode responseCode) throws PrintException {
        write(arg);

    }

}
