package zula.server.util;


import zula.common.commands.Command;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Serializable;

public class Save extends Command {
    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws PrintException {
        try {
            new XmlManager(listManager, ioManager).toXML(listManager.getCopyOfList(), listManager.getPath());
        } catch (ParserConfigurationException e) {
            ioManager.getOutputManager().write("Ошибка при сохранении в файл");
        }
    }
}
