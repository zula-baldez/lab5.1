package zula.common.commands;

import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;


import java.io.IOException;
import java.io.Serializable;


public class AverageOfWingspan extends Command {
    @Override
    public void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable argument) throws IOException, PrintException {
        double sum = 0;
        for (Dragon dragon : collectionManager.getCopyOfList()) {
            sum += dragon.getWingspan();
        }
        ioManager.getOutputManager().write(String.valueOf((sum / collectionManager.getCopyOfList().size())));
    }
}
