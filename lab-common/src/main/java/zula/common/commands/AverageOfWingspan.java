package zula.common.commands;

import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;


public class AverageOfWingspan extends Command {
    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {
        double sum = 0;
        for (Dragon dragon : listManager.getCopyOfList()) {
            sum += dragon.getWingspan();
        }
        ioManager.getOutputManager().write(String.valueOf((sum / listManager.getCopyOfList().size())));
    }
}
