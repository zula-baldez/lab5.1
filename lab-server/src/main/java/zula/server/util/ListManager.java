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

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;


/**
 * Class that contains all the
 * information like list of commands or
 * list of objects we work with
 */
public class ListManager implements CollectionManager {
    private String path = "C:\\Users\\moyak\\IdeaProjects\\prog5test\\XMLL.xml";
    private final LinkedList<Dragon> dragons = new LinkedList<>();
    private final Date date = new Date();
    private final HashSet<Integer> usedId = new HashSet<>();
    private final HashMap<String, Command> commands = new HashMap<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock(true);

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
    public void updateId(Dragon dragon) {
        Lock lock1 = lock.writeLock();
        try {
            lock1.lock();
            lock.writeLock();
            dragons.removeIf(n -> n.getId() == dragon.getId());
            dragon.addAttributes(new Date(), dragon.getId(), dragon.getOwnerId());
            dragons.add(dragon);
        } finally {
            lock1.unlock();
        }
    }

    @Override
    public List<Float> printFieldAscendingWingspan() {
        Lock lock1 = lock.writeLock();
        try {
            return getCopyOfList().stream().map(Dragon::getWingspan).sorted((o1, o2) -> (int) (o1 - o2)).collect(Collectors.toList());
        } finally {
            lock1.unlock();
        }
    }

    @Override
    public List<Dragon> printAscending() {
        Lock lock1 = lock.writeLock();
        try {
            List<Dragon> toSort = getCopyOfList();
            toSort = toSort.stream().sorted((o1, o2) -> (int) (o1.getWingspan() - o2.getWingspan())).collect(Collectors.toList());
            return toSort;
        } finally {
            lock1.unlock();
        }
    }

    @Override
    public String show() {
        Lock lock1 = lock.writeLock();
        try {
            List<Dragon> toSort = getCopyOfList();
            toSort = toSort.stream().sorted((o1, o2) -> {
                if (o1.getName().length() != o2.getName().length()) {
                    return o1.getName().length() - o2.getName().length();
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            }).collect(Collectors.toList());
            StringBuilder result = new StringBuilder();

            for (Dragon e : toSort) {
                result.append(e.toString()).append('\n');
            }
            return result.toString();
        } finally {
            lock1.unlock();
        }
    }

    @Override
    public double getAverageOfWingspan() {
        Lock lock1 = lock.writeLock();
        try {
            return getCopyOfList().stream().mapToDouble(d -> (double) d.getWingspan()).average().orElse(0);
        } finally {
            lock1.unlock();
        }
    }


    public void addDragonWithoutGeneratingId(Dragon dragon) {
        Lock lock1 = lock.writeLock();
        try {
            dragons.add(dragon);
        } finally {
            lock1.unlock();
        }
    }


    public LinkedList<Dragon> getCopyOfList() {
        Lock lock1 = lock.writeLock();
        try {
            return (LinkedList<Dragon>) dragons.clone();
        } finally {
            lock1.unlock();
        }
    }

    public void deleteDragon(Dragon dragon) {
        Lock lock1 = lock.writeLock();
        try {
            dragons.remove(dragon);
        } finally {
            lock1.unlock();
        }
    }

    public HashMap<String, Command> getCloneOfCommands() {
        Lock lock1 = lock.writeLock();
        try {
            return (HashMap<String, Command>) commands.clone();
        } finally {
            lock1.unlock();
        }
    }

    public Date getDate() {
        Lock lock1 = lock.writeLock();
        try {
            return date;
        } finally {
            lock1.unlock();
        }
    }

    public int getIdOfLast() {
        Lock lock1 = lock.writeLock();
        try {
            return dragons.getLast().getId();
        } finally {
            lock1.unlock();
        }
    }

    public boolean idIsUsed(int id) {
        Lock lock1 = lock.writeLock();
        try {
            return usedId.contains(id);
        } finally {
            lock1.unlock();
        }
    }

    public String getPath() {
        Lock lock1 = lock.writeLock();
        try {
            return path;
        } finally {
            lock1.unlock();
        }
    }


    public void clearDragons(int userId) {
        Lock lock1 = lock.writeLock();
        try {
            dragons.removeIf(x -> x.getOwnerId() == userId);
        } finally {
            lock1.unlock();
        }
    }

    public ServerMessage deleteLast() {
        Lock lock1 = lock.writeLock();
        try {
            if (dragons.size() != 0) {
                dragons.removeLast();
                return new ServerMessage("Удаление проведено успешно", ResponseCode.OK);
            } else {
                return new ServerMessage("Нечего удалять", ResponseCode.ERROR);
            }
        } finally {
            lock1.unlock();
        }
    }

    public ServerMessage removeById(int id) {
        Lock lock1 = lock.writeLock();
        try {
            dragons.removeIf(n -> n.getId() == id);
            return new ServerMessage("Удаление проведено успешно", ResponseCode.OK);
        } finally {
            lock1.unlock();
        }
    }

    public ServerMessage getById(int id) {
        Lock lock1 = lock.writeLock();
        try {
            for (Dragon e : dragons) {
                if (e.getId() == id) {
                    return new ServerMessage("", ResponseCode.OK);
                }
            }
            return new ServerMessage("Элемента с заданным id не существует", ResponseCode.ERROR);
        } finally {
            lock1.unlock();
        }
    }

    public ServerMessage removeLower(int id, int userId) {
        Lock lock1 = lock.writeLock();
        try {
            dragons.removeIf(n -> n.getId() < id && n.getOwnerId() == userId);
            usedId.remove(id);
            return new ServerMessage("Удаление проведено успешно", ResponseCode.OK);
        } finally {
            lock1.unlock();
        }
    }

    public void reverseList() {
        Lock lock1 = lock.writeLock();
        try {
            Collections.reverse(dragons);
        } finally {
            lock1.unlock();
        }
    }


}
