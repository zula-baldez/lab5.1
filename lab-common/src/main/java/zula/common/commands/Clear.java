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
        if (client.getSqlManager().removeUsersDragons(client.getUserId()) >= 1) {
            client.getCollectionManager().clearDragons(client.getUserId());
            return new ServerMessage("Команда выполнена!", ResponseCode.OK);
        } else {
           return new ServerMessage("Не удалось изменить состояние базы данных", ResponseCode.ERROR);
        }
    }
}
