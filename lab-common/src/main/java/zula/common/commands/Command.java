package zula.common.commands;

import zula.common.exceptions.PrintException;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;


import java.io.IOException;
import java.io.Serializable;

/**
 * Abstract Command class, all the commands extends from it
 */
public abstract class Command implements Serializable {
      public void execute(IoManager ioManager, CollectionManager collectionManager, Serializable arguments) throws IOException, PrintException {
          doInstructions(ioManager, collectionManager, arguments);

      }

      public abstract void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable arguments) throws IOException, PrintException;



}
