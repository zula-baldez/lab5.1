package zula.commands;

import zula.exceptions.WrongArgumentException;
import zula.util.ConsoleManager;

import java.io.IOException;


public class ExecuteScript extends Command {

    @Override
    public void doInstructions(ConsoleManager consoleManager, String arguments) {
        try {
            consoleManager.getInputManager().setFileReading(true, arguments);
        } catch (IOException e) {
            consoleManager.getOutputManager().write("Не удалось открыть файл");
        }
    }

    @Override
    public void checkAmountOfArgs(String arguments) throws WrongArgumentException {
        if (arguments.split(" ").length != 1) {
            throw new WrongArgumentException();
        }
    }
}
