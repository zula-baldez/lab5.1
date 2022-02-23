package zula.commands;


import zula.util.ConsoleManager;


public class Info extends Command {


    @Override
    public void doInstructions(ConsoleManager consoleManager, String argument) {
        consoleManager.getOutputManager().write("размер " + consoleManager.getListManager().getList().size() + " Дата создания - " + consoleManager.getListManager().getDate() + " Тип - LinkedList");
    }
}
