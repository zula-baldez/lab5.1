package zula.common.data;

import zula.common.commands.Command;

import java.io.Serializable;

public class ServerMessage implements Serializable {
    private Command command;
    private Serializable[] arguments;
    private final ResponseCode responseCode;
    private String name = "";
    private String password = "";

    public ServerMessage(Command commandToWrite, Serializable[] argToWrite, ResponseCode respCode) {
        this.command = commandToWrite;
        arguments = argToWrite;
        this.responseCode = respCode;
    }
    public ServerMessage(Serializable[] argument, ResponseCode respCode) {
        arguments = argument;
        responseCode = respCode;
    }
    public ServerMessage(Serializable argument, ResponseCode respCode) {
        arguments = new Serializable[]{argument};
        responseCode = respCode;
    }

    public void setIndentification(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Command getCommand() {
        return command;
    }

    public Serializable[] getArguments() {
        return arguments;
    }


    public ResponseCode getResponseStatus() {
        return responseCode;
    }




}
