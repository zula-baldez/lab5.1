package zula.common.commands;

import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public abstract class Command implements Serializable {


      public ServerMessage execute(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
         return doInstructions(ioManager, client, arguments);

      }

      public abstract ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException;

      public boolean isNeedsLoginCheck() {
          return true;
      }


}
