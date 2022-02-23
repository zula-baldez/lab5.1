package zula.commands;



import zula.dragon.DragonValidator;
import zula.exceptions.WrongArgumentException;
import zula.util.ArgumentReader;
import zula.util.ConsoleManager;


public class UpdateId extends Command {

    @Override
    public void checkAmountOfArgs(String arguments) throws WrongArgumentException {
        if (arguments.split(" ").length != 1) {
            throw new WrongArgumentException();
        }
    }

    @Override
    public void doInstructions(ConsoleManager consoleManager, String arguments) {
        int id;
        try {
           ArgumentReader argumentReader = new ArgumentReader(consoleManager);
            id = argumentReader.parseArgFromString(arguments, new DragonValidator(consoleManager)::idValidator, Integer::parseInt);
        } catch (WrongArgumentException e) {
            consoleManager.getOutputManager().write("Неверные аргументы");
            return;
        }
        consoleManager.getListManager().updateId(consoleManager, id);
    }
}
