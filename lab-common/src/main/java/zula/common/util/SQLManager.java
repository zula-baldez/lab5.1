package zula.common.util;

import zula.common.data.Dragon;
import zula.common.data.ServerMessage;

public interface SQLManager {
    ServerMessage register(String login, String password, AbstractClient client);
    ServerMessage login(String login, String password, AbstractClient client);
    int add(Dragon dragon);
    int remove(int id, int userId);
    int removeUsersDragons(int userId);
    int removeLower(int userId, int id);
    int updateId(int userId, Dragon dragon);
}
