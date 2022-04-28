package zula.common.commands;

import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;
import zula.common.util.SQLManager;

import java.io.Serializable;

public abstract class Command implements Serializable {
      public void execute(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
          doInstructions(ioManager, client, arguments);

      }

      public abstract void doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException;



}
