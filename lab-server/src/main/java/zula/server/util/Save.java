package zula.server.util;


import zula.common.commands.Command;
import zula.common.exceptions.PrintException;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Serializable;

public class Save extends Command {
    @Override
    public void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable argument) throws PrintException {
        try {
            new XmlManager(collectionManager, ioManager).toXML(collectionManager.getCopyOfList(), collectionManager.getPath());
        } catch (ParserConfigurationException e) {
            ioManager.getOutputManager().write("Ошибка при сохранении в файл");
        }
    }
}
