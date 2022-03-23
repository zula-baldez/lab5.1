package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * Realisation of add command
 */


public class Add extends Command implements Serializable {
    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {

        Dragon dragon = (Dragon) argument;
        dragon.addAttributes(new Date(), listManager.generateID());
        listManager.addDragon(dragon);
        ioManager.getOutputManager().write("");
    }







}
