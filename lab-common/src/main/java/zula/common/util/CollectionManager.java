package zula.common.util;

import zula.common.commands.Command;
import zula.common.data.Dragon;
import zula.common.data.ServerMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public interface CollectionManager {
    void addDragonWithoutGeneratingId(Dragon dragon);

    void updateId(Dragon dragon);

    List<Float> printFieldAscendingWingspan();

    List<Dragon> printAscending();

    String show();

    double getAverageOfWingspan();

    void setPath(String path1);

    HashSet<Integer> getUsedId();


    LinkedList<Dragon> getCopyOfList();

    void deleteDragon(Dragon dragon);

    HashMap<String, Command> getCloneOfCommands();

    Date getDate();

    boolean idIsUsed(int id);

    String getPath();


    void clearDragons(int userId);

    int getIdOfLast();

    ServerMessage deleteLast();

    ServerMessage removeById(int id);

    ServerMessage getById(int id);

    ServerMessage removeLower(int id, int userId);

    void reverseList();

}
