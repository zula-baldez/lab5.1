package zula.server.commands;


import zula.common.util.CollectionManager;
import zula.common.util.IoManager;
import zula.server.util.XmlManager;

import javax.xml.parsers.ParserConfigurationException;

public class Save  {
    public void execute(IoManager ioManager, CollectionManager collectionManager) {
        try {
            new XmlManager(collectionManager, ioManager).toXML(collectionManager.getCopyOfList(), collectionManager.getPath());
        } catch (ParserConfigurationException e) {
            return;
        }
    }
}
