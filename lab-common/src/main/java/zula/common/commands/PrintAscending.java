package zula.common.commands;


import zula.common.exceptions.PrintException;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;

import java.io.IOException;
import java.io.Serializable;

public class PrintAscending extends Command {
    @Override
    public void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable argument) throws IOException, PrintException {
        /*List<Dragon> toSort = collectionManager.getCopyOfList();
        toSort = toSort.stream().sorted((o1, o2) -> (int) (o1.getWingspan() - o2.getWingspan())).collect(Collectors.toList());
        */
        ioManager.getOutputManager().write((Serializable) collectionManager.printAscending());
    }
}
