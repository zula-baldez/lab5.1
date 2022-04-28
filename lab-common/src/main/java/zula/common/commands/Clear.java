package zula.common.commands;


import zula.common.data.ResponseCode;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;
import zula.common.util.SQLManager;

import java.io.Serializable;

public class Clear extends Command {
    @Override
    public void doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        if (client.getSqlManager().removeUsersDragons(client.getUserId()) >= 1) {
            client.getCollectionManager().clearDragons(client.getUserId());
            ioManager.getOutputManager().write("Команда выполнена!");
        } else {
            ioManager.getOutputManager().write("Не удалось изменить состояние базы данных");
        }
    }
}
