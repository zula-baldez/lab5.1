package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class PrintFieldAscendingWingspan extends Command {

    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {
        ArrayList<Float> toSort = new ArrayList<>();
        toSort = (ArrayList<Float>) listManager.getCopyOfList().stream().map((s) -> s.getWingspan()).sorted(new Comparator<Float>() {
            @Override
            public int compare(Float o1, Float o2) {
                return (int) (o1-o2);
            }
        }).collect(Collectors.toList());
        ioManager.getOutputManager().writeServerMessage(new ServerMessage(toSort, ResponseCode.OK));
    }
}
