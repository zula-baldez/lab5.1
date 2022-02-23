package zula.commands;


import zula.dragon.DragonValidator;
import zula.exceptions.WrongArgumentException;
import zula.util.ArgumentReader;
import zula.util.ConsoleManager;

public class RemoveById extends Command {

    @Override
    public void doInstructions(ConsoleManager consoleManager, String arguments) {
        int id;
        try {
            ArgumentReader argumentReader = new ArgumentReader(consoleManager);
            DragonValidator dragonValidator = new DragonValidator(consoleManager);
            id = argumentReader.parseArgFromString(arguments, dragonValidator::idValidator, Integer::parseInt);
        } catch (WrongArgumentException e) {
            consoleManager.getOutputManager().write("Неверные аргументы");
            return;
        }
        consoleManager.getListManager().removeById(id);
    }
    @Override
    public void checkAmountOfArgs(String arguments) throws WrongArgumentException {
        if (arguments.split(" ").length != 1) {
            throw new WrongArgumentException();
        }
        }
}

