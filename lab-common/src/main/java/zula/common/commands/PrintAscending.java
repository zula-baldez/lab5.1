package zula.common.commands;


import zula.common.exceptions.PrintException;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;

import java.io.Serializable;

public class PrintAscending extends Command {
    @Override
    public void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable argument) throws PrintException {

        ioManager.getOutputManager().write((Serializable) collectionManager.printAscending());
    }
}
