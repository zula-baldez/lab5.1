package zula.common.commands;

import zula.common.exceptions.PrintException;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;

import java.io.IOException;
import java.io.Serializable;

public class Show extends Command {
    @Override
    public void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable argument) throws IOException, PrintException {

        ioManager.getOutputManager().write(collectionManager.show());
    }
}
