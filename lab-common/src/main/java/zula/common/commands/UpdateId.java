package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;
import zula.common.util.SQLManager;

import java.io.Serializable;
import java.util.Date;


public class UpdateId extends Command {

    @Override
    public void doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        Dragon dragon = (Dragon) arguments[0];
        dragon.addAttributes(new Date(), client.getUserId());
        if(client.getSqlManager().updateId(client.getUserId(), dragon) < 0) {
            ioManager.getOutputManager().write("Объекта с таким id не существует или у вас нет прав доступа");
        } else {
            client.getCollectionManager().removeById(dragon.getId());
            client.getCollectionManager().addDragonWithoutGeneratingId(dragon);
            ioManager.getOutputManager().write("Команда выполнена!");
        }
    }
}
