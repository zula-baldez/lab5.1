package zula.common.commands;


import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public class Info extends Command {

    @Override
    public ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
       return new ServerMessage("размер " + client.getCollectionManager().getCopyOfList().size() + " Дата создания - " + client.getCollectionManager().getDate() + " Тип - LinkedList", ResponseCode.OK);
    }
}
