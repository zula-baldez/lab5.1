package zula.common.commands;



import zula.common.data.Dragon;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class PrintAscending extends Command {
    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {
        List<Dragon> toSort = listManager.getCopyOfList();
        toSort = toSort.stream().sorted((o1, o2) -> (int) (o1.getWingspan()-o2.getWingspan())).collect(Collectors.toList());

        ioManager.getOutputManager().writeServerMessage(new ServerMessage((Serializable) toSort, ResponseCode.OK));
    }
}
