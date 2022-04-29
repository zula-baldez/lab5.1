package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;
import java.util.Date;

/**
 * Realisation of add command
 */


public class Add extends Command implements Serializable {
    @Override
    public String doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        Dragon dragon = (Dragon) arguments[0];
        dragon.addAttributes(new Date(), client.getUserId());
        int id = client.getSqlManager().add(dragon);
        if (id >= 0) {
            dragon.setId(id);
            client.getCollectionManager().addDragonWithoutGeneratingId(dragon);
        } else {
            return "Не удалось добавить элемент в базу";
        }
        return "Команда выполнена!";
    }







}
