package zula.common.commands;


import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;
import java.io.IOException;
import java.io.Serializable;

public class Help extends Command {
    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {
        ioManager.getOutputManager().write("help : вывести справку по доступным командам\n"
                + "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n"
                + "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n"
                + "add {element} : добавить новый элемент в коллекцию\n"
                + "update_id {element} : обновить значение элемента коллекции, id которого равен заданному\n"
                + "remove_by_id id : удалить элемент из коллекции по его id\n"
                + "clear : очистить коллекцию\n"
                + "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n"
                + "exit : завершить программу (без сохранения в файл)\n"
                + "remove_last : удалить последний элемент из коллекции\n"
                + "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n"
                + "reorder : отсортировать коллекцию в порядке, обратном нынешнему\n"
                + "average_of_wingspan : вывести среднее значение поля wingspan для всех элементов коллекции\n"
                + "print_ascending : вывести элементы коллекции в порядке возрастания\n"
                + "print_field_ascending_wingspan : вывести значения поля wingspan всех элементов в порядке возрастания");
    }


}
