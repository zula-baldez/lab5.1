package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;


public class UpdateId extends Command {

    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {
        listManager.removeById(ioManager, (((Dragon) argument).getId()));
        Dragon dragon = (Dragon) argument;
        listManager.addDragon(dragon);
        ioManager.getOutputManager().write("Выбранный элемент успешно обновлен");
    }
}
