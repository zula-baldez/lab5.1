package zula.common.commands;


import zula.common.data.Dragon;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.util.IoManager;
import zula.common.util.ListManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Show extends Command {
    @Override
    public void doInstructions(IoManager ioManager, ListManager listManager, Serializable argument) throws IOException, PrintException {
        List<Dragon> toSort = listManager.getCopyOfList();
        toSort = toSort.stream().sorted((o1, o2) -> {
            if(o1.getName().length() != o2.getName().length()) {
                return o1.getName().length() - o2.getName().length();
            }
            else {
                return o1.getName().compareTo(o2.getName());
            }
        }).collect(Collectors.toList());
        String s = "";
        for (Dragon e: toSort) {
            s+=e.toString()+'\n';
        }
        ioManager.getOutputManager().write(s);
    }
}
