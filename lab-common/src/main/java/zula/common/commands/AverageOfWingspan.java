package zula.common.commands;

import zula.common.exceptions.PrintException;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;

import java.io.IOException;
import java.io.Serializable;


public class AverageOfWingspan extends Command {
    @Override
    public void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable argument) throws IOException, PrintException {
/*        double averageOfWingspan =  collectionManager.getCopyOfList().stream().mapToDouble(d -> (double) d.getWingspan()).average().orElse(0);
        ioManager.getOutputManager().write(averageOfWingspan);*/
        ioManager.getOutputManager().write(collectionManager.getAverageOfWingspan());

    }
}
