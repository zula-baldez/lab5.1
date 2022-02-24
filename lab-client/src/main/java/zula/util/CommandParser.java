package zula.util;
import zula.commands.Command;
import zula.exceptions.WrongCommandException;
import java.util.HashMap;

/**
 * class that read the command from the String
 */
public final class CommandParser {
    private CommandParser() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static String commandParse(String arguments, ConsoleManager consoleManager) throws WrongCommandException {
        String[] args = arguments.split(" ");

        String command = args[0];

        HashMap<String, Command> commands = consoleManager.getListManager().getCloneOfCommands();
        if (commands.containsKey(command)) {
            return command;
        } else {
            throw new WrongCommandException();
        }
    }
}

