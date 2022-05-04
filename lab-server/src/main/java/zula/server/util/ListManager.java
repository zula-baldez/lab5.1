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
    private final LinkedList<Dragon> dragons = new LinkedList<>();
    private final Date date = new Date();
    private final HashMap<String, Command> commands = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

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
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            dragons.removeIf(n -> n.getId() == dragon.getId());
            dragon.addAttributes(new Date(), dragon.getId(), dragon.getOwnerId());
            dragons.add(dragon);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<Float> printFieldAscendingWingspan() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return dragons.stream().map(Dragon::getWingspan).sorted((o1, o2) -> (int) (o1 - o2)).collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Dragon> printAscending() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            List<Dragon> toSort;
            toSort = dragons.stream().sorted((o1, o2) -> (int) (o1.getWingspan() - o2.getWingspan())).collect(Collectors.toList());
            return toSort;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String show() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            List<Dragon> toSort = dragons.stream().sorted((o1, o2) -> {
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
            readLock.unlock();
        }
    }

    @Override
    public double getAverageOfWingspan() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return dragons.stream().mapToDouble(d -> (double) d.getWingspan()).average().orElse(0);
        } finally {
            readLock.unlock();
        }
    }


    public void addDragonWithoutGeneratingId(Dragon dragon) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            dragons.add(dragon);
        } finally {
            writeLock.unlock();
        }
    }


    public LinkedList<Dragon> getCopyOfList() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return (LinkedList<Dragon>) dragons.clone();
        } finally {
            readLock.unlock();
        }
    }

    public void deleteDragon(Dragon dragon) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            dragons.remove(dragon);
        } finally {
            writeLock.unlock();
        }
    }

    public HashMap<String, Command> getCloneOfCommands() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return (HashMap<String, Command>) commands.clone();
        } finally {
            readLock.unlock();
        }
    }

    public Date getDate() {
        return date;
    }

    public int getIdOfLast() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            if (dragons.size() != 0) {
                return dragons.getLast().getId();
            } else {
                return -1;
            }
        } finally {
            readLock.unlock();
        }
    }



    public void clearDragons(int userId) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            dragons.removeIf(x -> x.getOwnerId() == userId);
        } finally {
            writeLock.unlock();
        }
    }

    public String deleteLast() {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            if (dragons.size() != 0) {
                dragons.removeLast();
                return "Удаление проведено успешно";
            } else {
                return "Нечего удалять";
            }
        } finally {
            writeLock.unlock();
        }
    }

    public String removeById(int id) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            dragons.removeIf(n -> n.getId() == id);
            return "Удаление проведено успешно";
        } finally {
            writeLock.unlock();
        }
    }

    public ResponseCode getById(int id) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            for (Dragon e : dragons) {
                if (e.getId() == id) {
                    return  ResponseCode.OK;
                }
            }
            return ResponseCode.ERROR;
        } finally {
            readLock.unlock();
        }
    }

    public ResponseCode removeLower(int id, int userId) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            dragons.removeIf(n -> n.getId() < id && n.getOwnerId() == userId);
            return ResponseCode.OK;
        } finally {
            writeLock.unlock();
        }
    }

    public void reverseList() {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            Collections.reverse(dragons);
        } finally {
            writeLock.unlock();
        }
    }


}
