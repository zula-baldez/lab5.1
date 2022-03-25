package zula.common.commands;


import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;
import java.io.IOException;
import java.io.Serializable;

public class Info extends Command {

    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {
        ioManager.getOutputManager().write("размер " + listManager.getCopyOfList().size() + " Дата создания - " + listManager.getDate() + " Тип - LinkedList");
    }
}
