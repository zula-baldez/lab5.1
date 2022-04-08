package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;
import java.io.Serializable;


public class UpdateId extends Command {

    @Override
    public void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable argument) throws PrintException {
        collectionManager.updateId((Dragon) argument);
        ioManager.getOutputManager().write("Выбранный элемент успешно обновлен");
    }
}
