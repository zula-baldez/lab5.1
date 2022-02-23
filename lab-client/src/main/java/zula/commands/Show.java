package zula.commands;

import zula.dragon.Dragon;
import zula.util.ConsoleManager;

public class Show extends Command {
    @Override
    public void doInstructions(ConsoleManager consoleManager, String arguments) {
        for (Dragon dragon: consoleManager.getListManager().getCopyOfList()) {
            consoleManager.getOutputManager().write(dragon.toString());
        }
    }
}
