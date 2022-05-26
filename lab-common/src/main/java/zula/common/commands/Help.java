package zula.common.commands;


import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.IoManager;

import java.io.Serializable;

public class Help extends Command {
    @Override
    public ServerMessage doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        return new ServerMessage("help : display help on available commands\n"
                + "info : print to standard output information about the collection (type, initialization date, number of elements, etc.)\n"
                + "show : print to standard output all elements of the collection in string representation\n"
                + "add {element} : add a new element to the collection\n"
                + "update_id {element} : update the value of the collection element whose id is equal to the given\n"
                + "remove_by_id id : remove an element from the collection by its id\n"
                + "clear : clear collection\n"
                + "execute_script file_name : read and execute a script from the specified file. The script contains commands in the same form as the user enters them in interactive mode.\n"
                + "exit : exit program (without saving to file)\n"
                + "remove_last : remove the last element from the collection\n"
                + "remove_lower {element} : remove all elements from the collection that are smaller than the given one\n"
                + "reorder : sort the collection in reverse order of the current one\n"
                + "average_of_wingspan : display the average value of the wingspan field for all items in the collection\n"
                + "print_ascending : print collection items in ascending order\n"
                + "print_field_ascending_wingspan : Print the values of the wingspan field of all elements in ascending order", ResponseCode.OK);
    }


}
