package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;

public class Show extends Command {
    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {
        String s = "";
        for(Dragon e: listManager.getCopyOfList()) {
            s+=(e.toString()+"\n");
        }
        ioManager.getOutputManager().write(s);
    }
}
