package zula.common.commands;
import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class Show extends Command {
    @Override
    public void doInstructions(IoManager ioManager, CollectionManager collectionManager, Serializable argument) throws IOException, PrintException {
        List<Dragon> toSort = collectionManager.getCopyOfList();
        toSort = toSort.stream().sorted((o1, o2) -> {
            if (o1.getName().length() != o2.getName().length()) {
                return o1.getName().length() - o2.getName().length();
            } else {
                return o1.getName().compareTo(o2.getName());
            }
        }).collect(Collectors.toList());
        String s = "";
        for (Dragon e: toSort) {
            s += e.toString() + '\n';
        }
        ioManager.getOutputManager().write(s);
    }
}
