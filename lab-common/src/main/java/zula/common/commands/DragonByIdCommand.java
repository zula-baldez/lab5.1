package zula.common.commands;

import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;

public class DragonByIdCommand extends Command {

    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {
        ioManager.getOutputManager().writeServerMessage(listManager.getById(ioManager, (Integer) argument));
    }
}
