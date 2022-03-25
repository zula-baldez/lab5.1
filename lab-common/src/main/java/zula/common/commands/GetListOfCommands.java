package zula.common.commands;

import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;

public class GetListOfCommands extends Command {

    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable arguments) throws IOException, PrintException {
       ioManager.getOutputManager().writeServerMessage(new ServerMessage(listManager.getCloneOfCommands(), ResponseCode.OK));
    }
}
