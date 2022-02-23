package zula.commands;

import zula.dragon.Dragon;
import zula.util.ConsoleManager;

import java.util.Collections;
import java.util.LinkedList;

public class PrintAscending extends Command {
    @Override
    public void doInstructions(ConsoleManager consoleManager, String argument) {
        LinkedList<Dragon> toSort = consoleManager.getListManager().getCopyOfList();
        Collections.sort(toSort);
        for (Dragon dragon : toSort) {
            consoleManager.getOutputManager().write(dragon.toString());

        }
    }
}
