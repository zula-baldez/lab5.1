package zula.common.util;

import zula.common.commands.Command;
import zula.common.data.Dragon;
import zula.common.data.ResponseCode;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public interface CollectionManager {
    void addDragonWithoutGeneratingId(Dragon dragon);

    void updateId(Dragon dragon);

    List<Float> printFieldAscendingWingspan();

    List<Dragon> printAscending();

    String show();

    double getAverageOfWingspan();



    LinkedList<Dragon> getCopyOfList();

    void deleteDragon(Dragon dragon);

    HashMap<String, Command> getCloneOfCommands();

    Date getDate();




    void clearDragons(int userId);

    int getIdOfLast();

    String deleteLast();

    String removeById(int id);

    ResponseCode getById(int id);

    ResponseCode removeLower(int id, int userId);

    void reverseList();

}
