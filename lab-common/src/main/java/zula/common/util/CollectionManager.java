package zula.common.util;

import zula.common.commands.Command;
import zula.common.data.Dragon;
import zula.common.data.ServerMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public interface CollectionManager {




    void setPath(String path1);

    HashSet<Integer> getUsedId();

    void addDragon(Dragon e);

    LinkedList<Dragon> getCopyOfList();

    void deleteDragon(Dragon e);

    HashMap<String, Command> getCloneOfCommands();

    Date getDate();

    boolean idIsUsed(int id);

    String getPath();

    boolean validateId(int id);

    int generateID();

    void clearDragons();

    ServerMessage deleteLast(IoManager ioManager);

    ServerMessage removeById(IoManager ioManager, int id);

    ServerMessage getById(IoManager ioManager, int id);

    ServerMessage removeLower(IoManager ioManager, int id);

    void reverseList();

}
