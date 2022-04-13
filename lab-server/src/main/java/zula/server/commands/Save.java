package zula.server.commands;


import zula.common.util.CollectionManager;
import zula.server.util.XmlManager;

import javax.xml.parsers.ParserConfigurationException;

public class Save  {
    public void execute(XmlManager xmlManager, CollectionManager collectionManager, String path) {
        try {
            xmlManager.toXML(collectionManager.getCopyOfList(), path);
            System.out.println("Успешное сохранение!");
        } catch (ParserConfigurationException e) {
            return;
        }
    }
}
