package zula.common.commands;


import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;
import zula.common.util.SQLManager;

import java.io.Serializable;

public class Reorder extends Command {
    @Override
    public void doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        client.getCollectionManager().reverseList();
        ioManager.getOutputManager().write("Коллекция перемешана");
    }
}
