package zula.commands;

import zula.util.ConsoleManager;

public class Reorder extends Command {
    @Override
    public void doInstructions(ConsoleManager consoleManager, String arguments) {
        consoleManager.getListManager().reverseList();
    }
}
