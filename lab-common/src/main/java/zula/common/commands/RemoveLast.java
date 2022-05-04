package zula.common.commands;


import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public class RemoveLast extends Command {


    @Override
    public ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        int id = client.getCollectionManager().getIdOfLast();
        if (id != -1) {
            RemoveById removeById = new RemoveById();
            return removeById.doInstructions(ioManager, client, new Serializable[]{id});
        } else {
            return new ServerMessage("Нечего удалять", ResponseCode.ERROR);
        }
    }
}
