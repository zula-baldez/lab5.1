package zula.commands;


import zula.parser.Mapper;
import zula.util.ConsoleManager;

import javax.xml.parsers.ParserConfigurationException;

public class Save extends Command {
    @Override
    public void doInstructions(ConsoleManager consoleManager, String arguments) {
        try {
            new Mapper(consoleManager).toXML(consoleManager.getListManager().getList(), consoleManager.getListManager().getPath());
        } catch (ParserConfigurationException e) {
            consoleManager.getOutputManager().write("Ошибка при сохранении в файл");
        }
    }
}
