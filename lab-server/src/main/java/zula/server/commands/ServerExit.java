package zula.server.commands;

import zula.common.commands.Command;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;

import java.io.Serializable;

public class ServerExit extends Command {
    @Override
    public void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable argument)  {
        Save save = new Save();
        save.execute(ioManager, collectionManager);
        ioManager.exitProcess();
    }
}
