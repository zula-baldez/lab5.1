package zula.commands;


import zula.util.ConsoleManager;

public class Exit extends Command {
    @Override
    public void doInstructions(ConsoleManager consoleManager, String argument) {
        consoleManager.getOutputManager().write("До свидания!");
        consoleManager.exitProcess();
    }
}
