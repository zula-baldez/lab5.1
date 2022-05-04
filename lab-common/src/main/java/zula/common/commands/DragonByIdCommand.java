package zula.common.commands;

import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public class DragonByIdCommand extends Command {

    @Override
    public ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws  PrintException {
        ResponseCode responseCode = client.getCollectionManager().getById((Integer) arguments[0]);
        if (responseCode == ResponseCode.OK) {
            return new ServerMessage("", ResponseCode.OK);
        } else {
            return new ServerMessage("Элемента с заданным id не существует", ResponseCode.ERROR);
        }
    }
}
