package zula.commands;

import zula.dragon.Dragon;
import zula.util.ConsoleManager;


public class AverageOfWingspan extends Command {
    @Override
    public void doInstructions(ConsoleManager consoleManager, String argument) {
        double sum = 0;
        for (Dragon dragon : consoleManager.getListManager().getList()) {
            sum += dragon.getWingspan();
        }
        consoleManager.getOutputManager().write(Double.toString(sum / consoleManager.getListManager().getList().size()));
    }
}
