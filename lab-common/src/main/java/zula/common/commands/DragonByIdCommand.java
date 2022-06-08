package zula.common.commands;

import zula.common.data.Dragon;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public class DragonByIdCommand extends Command {

    @Override
    public ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws  PrintException {
        Dragon dragon = client.getCollectionManager().getById((Integer) arguments[0], client.getUserId());
        if (dragon != null) {
            return new ServerMessage(dragon, ResponseCode.OK);
        } else {
            return new ServerMessage("There is no element with such id or you are not its creator", ResponseCode.ERROR);
        }
    }
}
