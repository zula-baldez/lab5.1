package zula.common.commands;


import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;
import zula.common.util.SQLManager;

import java.io.Serializable;

public class RemoveById extends Command {

    @Override
    public void doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {

        int id = Integer.parseInt(arguments[0].toString());
        if(client.getSqlManager().remove(id, client.getUserId()) >= 0) {
            client.getCollectionManager().removeById(id);
            ioManager.getOutputManager().write("Удаление проведено успешно!");
        } else {
            ioManager.getOutputManager().write("Либо такогo id не существует, либо не вы его создатель:(");
        }



    }

}

