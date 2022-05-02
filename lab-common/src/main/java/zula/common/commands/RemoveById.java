package zula.common.commands;


import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public class RemoveById extends Command {

    @Override
    public ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {

        int id = Integer.parseInt(arguments[0].toString());
        if (client.getSqlManager().remove(id, client.getUserId()) == ResponseCode.OK) {
            client.getCollectionManager().removeById(id);
            return new ServerMessage("Удаление проведено успешно!", ResponseCode.OK);
        } else {
            return new ServerMessage("Либо такогo id не существует, либо не вы его создатель:(", ResponseCode.ERROR);
        }



    }

}

