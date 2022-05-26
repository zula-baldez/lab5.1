package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
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
    public ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        Dragon dragon = (Dragon) arguments[0];
        dragon.addAttributes(new Date(), client.getUserId());
        int id = client.getSqlManager().add(dragon);
        if (id >= 0) {
            dragon.setId(id);
            client.getCollectionManager().addDragonWithoutGeneratingId(dragon);
        } else {
            return new ServerMessage("Impossible to add element", ResponseCode.ERROR);
        }
        return new ServerMessage("Successful execution!", ResponseCode.OK);
    }







}
