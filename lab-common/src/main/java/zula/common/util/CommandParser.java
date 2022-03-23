package zula.common.util;

import zula.common.commands.Command;
import zula.common.exceptions.WrongCommandException;

import java.util.HashMap;

/**
 * class that read the command from the String
 */
public final class CommandParser {
    private CommandParser() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static String commandParse(String arguments, IoManager ioManager) throws WrongCommandException {
        String[] args = arguments.split(" ");
        if (args.length == 0) {
            throw new WrongCommandException();
        }
        String command = args[0];
        return  command;

    }
}

