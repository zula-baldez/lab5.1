package zula.commands;

import zula.dragon.Dragon;
import zula.util.ConsoleManager;

import java.util.ArrayList;
import java.util.Collections;

public class PrintFieldAscendingWingspan extends Command {

    @Override
    public void doInstructions(ConsoleManager consoleManager, String argument) {
        ArrayList<Float> toSort = new ArrayList<>();
        for (Dragon dragon : consoleManager.getListManager().getList()) {
            toSort.add(dragon.getWingspan());
        }
        Collections.sort(toSort);
        consoleManager.getOutputManager().write(toSort.toString());
    }
}
