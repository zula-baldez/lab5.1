package zula.util;

import zula.commands.Command;
import zula.commands.Help;
import zula.commands.Info;
import zula.commands.Show;
import zula.commands.Add;
import zula.commands.AverageOfWingspan;
import zula.commands.Clear;
import zula.commands.ExecuteScript;
import zula.commands.Exit;
import zula.commands.PrintAscending;
import zula.commands.PrintFieldAscendingWingspan;
import zula.commands.RemoveById;
import zula.commands.RemoveLast;
import zula.commands.RemoveLower;
import zula.commands.Reorder;
import zula.commands.Save;
import zula.dragon.Color;
import zula.dragon.Coordinates;
import zula.dragon.DragonCave;
import zula.dragon.Dragon;
import zula.dragon.DragonType;
import zula.commands.UpdateId;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Collections;


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
        commands.put("save", new Save());
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

    public boolean idIsUsed(Integer i) {
        return usedId.contains(i);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int generateID() {
        maxId++;
        usedId.add(maxId);
        return maxId;
    }
    public boolean isIdValid(int id) {
        if (!(usedId.contains(id)) && id > 0) {
            maxId = Math.max(id + 1, maxId);
            usedId.add(id);
            return true;
        }
        return false;
    }
    public void clearDragons() {
        dragons.clear();
    }

    public void deleteLast(ConsoleManager consoleManager) {
        if (dragons.size() != 0) {
            dragons.removeLast();
        } else {
            consoleManager.getOutputManager().write("Нечего удалять");
        }
    }
    public void removeById(ConsoleManager consoleManager, int id) {
        if(idIsUsed(id)) {
            dragons.removeIf(n -> n.getId() == id);
            usedId.remove(id);
        } else {
            consoleManager.getOutputManager().write("Элемента с id=" + id + " не существует. Проверьте правильность введенных данных.");
        }
    }
    public void removeLower(ConsoleManager consoleManager, int id) {
        if (consoleManager.getListManager().idIsUsed(id)) {
            dragons.removeIf(n -> n.getId() < id);
            usedId.remove(id);
        } else {
            consoleManager.getOutputManager().write("Элемента с id=" + id + " не существует. Проверьте правильность введенных данных.");
        }
    }
    public void reverseList() {
        Collections.reverse(dragons);
    }

    public void updateId(ConsoleManager consoleManager, int id) {
        for (Dragon drag : dragons) {
            if (drag.getId() == id) {
                ArgumentReader argumentReader = new ArgumentReader(consoleManager);
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
        consoleManager.getOutputManager().write("Элемента с id=" + id + " не существует. Проверьте правильность введенных данных.");
    }
}
