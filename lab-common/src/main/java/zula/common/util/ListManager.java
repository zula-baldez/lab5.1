package zula.common.util;

import zula.common.data.*;
import zula.common.exceptions.PrintException;
import zula.common.util.ArgumentReader;
import zula.common.util.IoManager;
import zula.common.commands.*;

import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Class that contains all the
 * information like list of commands or
 * list of objects we work with
 */
public class ListManager {
    private int maxId = 1;
    private String path = "C:\\Users\\moyak\\IdeaProjects\\prog5test\\XMLL.xml";
    private final LinkedList<Dragon> dragons = new LinkedList<>();
    private final Date date = new Date();
    private final HashSet<Integer> usedId = new HashSet<>();
    private final HashMap<String, Command> commands = new HashMap<>();

    public ListManager() {
        commands.put("help", new Help());
        commands.put("info", new Info());
        commands.put("show", new Show());
        commands.put("update_id", new UpdateId());
        commands.put("remove_by_id", new RemoveById());
        commands.put("clear", new Clear());
        commands.put("execute_script", new ExecuteScript());
        commands.put("exit", new Exit());
        commands.put("remove_last", new RemoveLast());
        commands.put("remove_lower", new RemoveLower());
        commands.put("reorder", new Reorder());
        commands.put("average_of_wingspan", new AverageOfWingspan());
        commands.put("print_ascending", new PrintAscending());
        commands.put("print_field_ascending_wingspan", new PrintFieldAscendingWingspan());
        commands.put("add", new Add());

    }

    public void setPath(String path1) {
        this.path = path1;
    }

    public HashSet<Integer> getUsedId() {
        return usedId;
    }

    public void addDragon(Dragon e) {
        dragons.add(e);
    }

    public LinkedList<Dragon> getCopyOfList() {
        return (LinkedList<Dragon>) dragons.clone();
    }

    public void deleteDragon(Dragon e) {
        dragons.remove(e);
    }

    public HashMap<String, Command> getCloneOfCommands() {
        return (HashMap<String, Command>) commands.clone();
    }

    public Date getDate() {
        return date;
    }

    public boolean idIsUsed(int id) {
        return usedId.contains(id);
    }
    public String getPath() {
        return path;
    }
    public boolean validateId(int id) {
        if(usedId.contains(id)) {
            return false;
        } else {
            if(id < 0) {
                return false;
            }
            maxId=id+1;
            usedId.add(id);
            return true;
        }
    }

    public int generateID() {
        usedId.add(maxId);
        maxId++;
        return maxId-1;
    }

    public void clearDragons() {
        dragons.clear();
    }

    public ServerMessage deleteLast(IoManager ioManager) throws PrintException {
        if (dragons.size() != 0) {
            dragons.removeLast();
            return  new ServerMessage("Удаление проведено успешно", ResponseCode.OK);

        } else {
          return new ServerMessage("Нечего удалять", ResponseCode.ERROR);
        }
    }
    public ServerMessage removeById(IoManager ioManager, int id) throws PrintException {
        if (idIsUsed(id)) {
            dragons.removeIf(n -> n.getId() == id);
            usedId.remove(id);
            return  new ServerMessage("Удаление проведено успешно", ResponseCode.OK);

        } else {
          return new ServerMessage("Элемента с id=" + id + " не существует. Проверьте правильность введенных данных.", ResponseCode.ERROR);
        }
    }
    public ServerMessage getById(IoManager ioManager, int id) throws PrintException {
        for(Dragon e: dragons) {
            if(e.getId()==id)
            return  new ServerMessage("", ResponseCode.OK);
        }
            return new ServerMessage("", ResponseCode.ERROR);

    }
    public ServerMessage removeLower(IoManager ioManager, int id) throws PrintException {
        if (idIsUsed(id)) {
            dragons.removeIf(n -> n.getId() < id);
            usedId.remove(id);
           return new ServerMessage("Удаление проведено успешно", ResponseCode.OK);
        } else {
            return  new ServerMessage("Элемента с id=" + id + " не существует. Проверьте правильность введенных данных.", ResponseCode.ERROR);
        }
    }
    public void reverseList() {
        Collections.reverse(dragons);
    }

    public void updateId(IoManager ioManager, int id) {
       /* for (Dragon drag : dragons) {
            if (drag.getId() == id) {
                ArgumentReader argumentReader = new ArgumentReader(ioManager);
                String name = argumentReader.readName();
                Coordinates coordinates = argumentReader.readCoordinates();
                long age = argumentReader.readAge();
                float wingspan = argumentReader.readWingspan();
                Color color = argumentReader.readColor();
                DragonType type = argumentReader.readType();
                DragonCave cave = argumentReader.readCave();
                Date date2 = drag.getCreationDate();
                deleteDragon(drag);
                System.out.println("Переопределен элемент с введенным id");
                Dragon dragon = new Dragon(name, coordinates, age, wingspan, color, type, cave);
                dragon.addAttributes(date2, id);
                addDragon(dragon);
                return;
            }
        }
        ioManager.getOutputManager().write("Элемента с id=" + id + " не существует. Проверьте правильность введенных данных.");
    */
    }

}
