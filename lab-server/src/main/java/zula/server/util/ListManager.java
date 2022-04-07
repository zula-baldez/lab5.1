package zula.server.util;

import zula.common.commands.Add;
import zula.common.commands.AverageOfWingspan;
import zula.common.commands.Clear;
import zula.common.commands.Command;
import zula.common.commands.ExecuteScript;
import zula.common.commands.Exit;
import zula.common.commands.Help;
import zula.common.commands.Info;
import zula.common.commands.PrintAscending;
import zula.common.commands.PrintFieldAscendingWingspan;
import zula.common.commands.RemoveById;
import zula.common.commands.RemoveLast;
import zula.common.commands.RemoveLower;
import zula.common.commands.Reorder;
import zula.common.commands.Show;
import zula.common.commands.UpdateId;
import zula.common.data.Dragon;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Class that contains all the
 * information like list of commands or
 * list of objects we work with
 */
public class ListManager implements CollectionManager {
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

    @Override
    public List<Float> printFieldAscendingWingspan() {
        return getCopyOfList().stream().map(Dragon::getWingspan).sorted((o1, o2) -> (int) (o1 - o2)).collect(Collectors.toList());
    }

    @Override
    public List<Dragon> printAscending() {
        List<Dragon> toSort = getCopyOfList();
        toSort = toSort.stream().sorted((o1, o2) -> (int) (o1.getWingspan() - o2.getWingspan())).collect(Collectors.toList());
        return toSort;
    }

    @Override
    public String show() {
        List<Dragon> toSort = getCopyOfList();
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
        return s;
    }

    @Override
    public double getAverageOfWingspan() {
        return getCopyOfList().stream().mapToDouble(d -> (double) d.getWingspan()).average().orElse(0);
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
        if (usedId.contains(id)) {
            return false;
        } else {
            if (id < 0) {
                return false;
            }
            maxId = id + 1;
            usedId.add(id);
            return true;
        }
    }

    public int generateID() {
        usedId.add(maxId);
        maxId++;
        return maxId - 1;
    }

    public void clearDragons() {
        dragons.clear();
    }

    public ServerMessage deleteLast(IoManager ioManager) {
        if (dragons.size() != 0) {
            dragons.removeLast();
            return  new ServerMessage("Удаление проведено успешно", ResponseCode.OK);

        } else {
          return new ServerMessage("Нечего удалять", ResponseCode.ERROR);
        }
    }
    public ServerMessage removeById(IoManager ioManager, int id) {
        if (idIsUsed(id)) {
            dragons.removeIf(n -> n.getId() == id);
            usedId.remove(id);
            return  new ServerMessage("Удаление проведено успешно", ResponseCode.OK);

        } else {
          return new ServerMessage("Элемента с id=" + id + " не существует. Проверьте правильность введенных данных.", ResponseCode.ERROR);
        }
    }
    public ServerMessage getById(IoManager ioManager, int id) {
        for (Dragon e: dragons) {
            if (e.getId() == id) {
                return new ServerMessage("", ResponseCode.OK);
            }
        }
            return new ServerMessage("Элемента с заданным id не существует", ResponseCode.ERROR);
    }
    public ServerMessage removeLower(IoManager ioManager, int id) {
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



}
