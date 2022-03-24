package zula.common.commands;



import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.WrongArgumentException;
import zula.common.exceptions.WrongCommandException;
import zula.common.util.CommandParser;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;


public class ExecuteScript extends Command {

    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {

        ioManager.getOutputManager().write("");



    }
}
