package zula.common.commands;


import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public class RemoveLower extends Command {



    @Override
    public ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        int id = Integer.parseInt(arguments[0].toString());
        if (client.getSqlManager().removeLower(client.getUserId(), id) == ResponseCode.OK) {
            client.getCollectionManager().removeLower(id, client.getUserId());
            return new ServerMessage("Removal completed successfully", ResponseCode.OK);
        } else {
            return new ServerMessage("There is no element with the given id", ResponseCode.ERROR);
        }

    }

}



