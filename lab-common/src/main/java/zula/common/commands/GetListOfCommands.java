package zula.common.commands;

import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;


import java.io.Serializable;

public class GetListOfCommands extends Command {

    @Override
    public void doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
       ioManager.getOutputManager().writeServerMessage(new ServerMessage(new Serializable[]{client.getCollectionManager().getCloneOfCommands()}, ResponseCode.OK));
    }
}
