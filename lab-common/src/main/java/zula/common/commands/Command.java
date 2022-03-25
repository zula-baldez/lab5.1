package zula.common.commands;

import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;

/**
 * Abstract Command class, all the commands extends from it
 */
public abstract class Command implements Serializable {
      public void execute(IoManager ioManager, ListManager listManager, Serializable arguments) throws IOException, PrintException {
          doInstructions(ioManager, listManager, arguments);

      }

      public abstract void doInstructions(IoManager ioManager, ListManager listManager, Serializable arguments) throws IOException, PrintException;



}
