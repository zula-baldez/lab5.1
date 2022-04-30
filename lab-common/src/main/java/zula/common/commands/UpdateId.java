package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;
import java.util.Date;


public class UpdateId extends Command {

    @Override
    public ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        Dragon dragon = (Dragon) arguments[0];
        dragon.addAttributes(new Date(), client.getUserId());
        if (client.getSqlManager().updateId(client.getUserId(), dragon) < 0) {
            return new ServerMessage("Объекта с таким id не существует или у вас нет прав доступа", ResponseCode.ERROR);
        } else {
            client.getCollectionManager().removeById(dragon.getId());
            client.getCollectionManager().addDragonWithoutGeneratingId(dragon);
            return new ServerMessage("Команда выполнена!", ResponseCode.OK);
        }
    }
}
