package zula.commands;

import zula.dragon.DragonValidator;
import zula.exceptions.WrongArgumentException;
import zula.util.ArgumentParser;
import zula.util.ConsoleManager;

public class RemoveLower extends Command {

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
            ArgumentParser argumentParser = new ArgumentParser();
            id = argumentParser.parseArgFromString(arguments, new DragonValidator(consoleManager)::idValidator, Integer::parseInt);
        } catch (WrongArgumentException e) {
            consoleManager.getOutputManager().write("Неверные аргументы");
            return;
        }
        consoleManager.getListManager().removeLower(consoleManager, id);

    }

}



