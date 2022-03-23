package zula.common.commands;



import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

public class PrintAscending extends Command {
    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {
        LinkedList<Dragon> toSort = listManager.getCopyOfList();
        Collections.sort(toSort);
        for (Dragon dragon : toSort) {
            ioManager.getOutputManager().write(dragon.toString());

        }
    }
}
