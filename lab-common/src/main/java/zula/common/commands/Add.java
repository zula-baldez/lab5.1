package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * Realisation of add command
 */


public class Add extends Command implements Serializable {
    @Override
    public void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable argument) throws IOException, PrintException {
        Dragon dragon = (Dragon) argument;
        dragon.addAttributes(new Date(), collectionManager.generateID());
        collectionManager.addDragon(dragon);
        ioManager.getOutputManager().write("");
    }







}
