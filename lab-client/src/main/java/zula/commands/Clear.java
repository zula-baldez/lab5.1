package zula.commands;


import zula.util.ConsoleManager;

public class Clear extends Command {
    @Override
    public void doInstructions(ConsoleManager consoleManager, String argument) {
        consoleManager.getListManager().clearDragons();
    }
}
