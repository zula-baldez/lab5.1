package zula.commands;


import zula.util.XmlManager;
import zula.util.ConsoleManager;

import javax.xml.parsers.ParserConfigurationException;

public class Save extends Command {
    @Override
    public void doInstructions(ConsoleManager consoleManager, String arguments) {
        try {
            new XmlManager(consoleManager).toXML(consoleManager.getListManager().getCopyOfList(), consoleManager.getListManager().getPath());
        } catch (ParserConfigurationException e) {
            consoleManager.getOutputManager().write("Ошибка при сохранении в файл");
        }
    }
}
