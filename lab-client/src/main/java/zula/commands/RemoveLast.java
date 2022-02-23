package zula.commands;

import zula.util.ConsoleManager;


public class RemoveLast extends Command {


    @Override
    public void doInstructions(ConsoleManager consoleManager, String argument) {
        consoleManager.getListManager().deleteLast(consoleManager);
    }
}
