package zula.parser;


import zula.commands.Command;
import zula.exceptions.WrongCommandException;
import zula.util.ConsoleManager;

import java.util.HashMap;

/**
 * class that read the command from the String
 */
public final class CommandReader {
    private CommandReader() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static String commandReader(String arguments, ConsoleManager consoleManager) throws WrongCommandException {
        String[] args = arguments.split(" ");

        String command = args[0];

        HashMap<String, Command> commands = consoleManager.getListManager().getCommands();
        if (commands.containsKey(command)) {
            return command;
        } else {
            throw new WrongCommandException();
        }
    }
}

