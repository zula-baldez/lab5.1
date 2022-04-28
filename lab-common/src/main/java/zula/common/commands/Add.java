package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.data.ResponseCode;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;
import zula.common.util.SQLManager;

import java.io.Serializable;
import java.util.Date;

/**
 * Realisation of add command
 */


public class Add extends Command implements Serializable {
    @Override
    public void doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        Dragon dragon = (Dragon) arguments[0];
        dragon.addAttributes(new Date(), client.getUserId());
        int id = client.getSqlManager().add(dragon);
        if (id >= 0) {
            dragon.setId(id);
            client.getCollectionManager().addDragonWithoutGeneratingId(dragon);
        } else {
            ioManager.getOutputManager().write("Не удалось добавить элемент в базу");
        };


        ioManager.getOutputManager().write("Команда выполнена!");
    }







}
