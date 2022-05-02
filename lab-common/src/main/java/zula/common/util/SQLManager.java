package zula.common.util;

import zula.common.data.Dragon;
import zula.common.data.ResponseCode;

public interface SQLManager {
    ResponseCode register(String login, String password, AbstractClient client);
    ResponseCode login(String login, String password, AbstractClient client);
    int add(Dragon dragon);
    ResponseCode remove(int id, int userId);
    ResponseCode removeUsersDragons(int userId);
    ResponseCode removeLower(int userId, int id);
    ResponseCode updateId(int userId, Dragon dragon);
}
