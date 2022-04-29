package zula.common.commands;

import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public abstract class Command implements Serializable {
      public String execute(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
         return doInstructions(ioManager, client, arguments);

      }

      public abstract String doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException;



}
