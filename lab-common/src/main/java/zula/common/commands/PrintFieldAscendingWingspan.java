package zula.common.commands;



import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PrintFieldAscendingWingspan extends Command {

    @Override
    public void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable argument) throws IOException, PrintException {
        ArrayList<Float> toSort;
        toSort = (ArrayList<Float>) collectionManager.getCopyOfList().stream().map((s) -> s.getWingspan()).sorted((o1, o2) -> (int) (o1 - o2)).collect(Collectors.toList());
        ioManager.getOutputManager().writeServerMessage(new ServerMessage(toSort, ResponseCode.OK));
    }
}