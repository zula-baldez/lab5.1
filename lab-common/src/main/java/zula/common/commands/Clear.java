package zula.common.commands;


import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public class Clear extends Command {
    @Override
    public ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        if (client.getSqlManager().removeUsersDragons(client.getUserId()) == ResponseCode.OK) {
            client.getCollectionManager().clearDragons(client.getUserId());
            return new ServerMessage("Successful execution!", ResponseCode.OK);
        } else {
           return new ServerMessage("Impossible to change data base", ResponseCode.ERROR);
        }
    }
}
