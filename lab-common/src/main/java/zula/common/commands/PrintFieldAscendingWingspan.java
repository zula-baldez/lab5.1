package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class PrintFieldAscendingWingspan extends Command {

    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {
        ArrayList<Float> toSort = new ArrayList<>();
        for (Dragon dragon : listManager.getCopyOfList()) {
            toSort.add(dragon.getWingspan());
        }
        Collections.sort(toSort);
        ioManager.getOutputManager().write(toSort.toString());
    }
}
