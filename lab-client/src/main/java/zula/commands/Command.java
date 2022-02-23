package zula.commands;

import zula.exceptions.WrongArgumentException;
import zula.util.ConsoleManager;

/**
 * Abstract Command class, all the commands extends from it
 */
public abstract class Command {
      public void execute(String arguments, ConsoleManager consoleManager) throws WrongArgumentException {
            checkAmountOfArgs(arguments);
            doInstructions(consoleManager, arguments);
            report(consoleManager);
      }
      public void checkAmountOfArgs(String arguments) throws WrongArgumentException {
            if (!arguments.replace(" ", "").equals("")) {
                  throw new WrongArgumentException();
            }
      }
      public abstract void doInstructions(ConsoleManager consoleManager, String arguments);

      public void report(ConsoleManager consoleManager) {
            consoleManager.getOutputManager().write("Команда выполнена!");
      }

}
