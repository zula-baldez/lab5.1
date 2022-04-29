package zula.common.commands;


import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public class RemoveLower extends Command {



    @Override
    public void doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        int id = Integer.parseInt(arguments[0].toString());
        if (client.getSqlManager().removeLower(client.getUserId(), id) >= 0) {
            client.getCollectionManager().removeLower(id, client.getUserId());
            ioManager.getOutputManager().write("Команда выполнена!");
        } else {
            ioManager.getOutputManager().write("Элемента с заданным id не существует");
        }

    }

}



