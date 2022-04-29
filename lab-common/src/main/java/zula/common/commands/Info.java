package zula.common.commands;


import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public class Info extends Command {

    @Override
    public void doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        ioManager.getOutputManager().write("размер " + client.getCollectionManager().getCopyOfList().size() + " Дата создания - " + client.getCollectionManager().getDate() + " Тип - LinkedList");
    }
}
