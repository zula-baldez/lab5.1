package zula.common.commands;


import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public class Reorder extends Command {
    @Override
    public ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        client.getCollectionManager().reverseList();
        return new ServerMessage("Коллекция перемешана", ResponseCode.OK);
    }
}
