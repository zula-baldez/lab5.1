package zula.common.data;

import zula.common.commands.Command;

import java.io.Serializable;

public class ServerMessage implements Serializable {
    private Command command;
    private Serializable arguments;
    private final ResponseCode responseCode;

    public ServerMessage(Command commandToWrite, Serializable argToWrite, ResponseCode respCode) {
        this.command = commandToWrite;
        this.arguments = argToWrite;
        this.responseCode = respCode;
    }
    public ServerMessage(Serializable argument, ResponseCode respCode) {
        arguments = argument;
        responseCode = respCode;
    }


    public Command getCommand() {
        return command;
    }

    public Serializable getArguments() {
        return arguments;
    }


    public ResponseCode getResponseStatus() {
        return responseCode;
    }




}
