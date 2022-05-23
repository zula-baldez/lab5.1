package zula.resources;

import java.util.HashMap;
import java.util.ListResourceBundle;

public class resource extends ListResourceBundle {



    private static final Object[][] contents = {
            {"LANGUAGE", "ЯЗЫК"},
            {"REMOTE HOST ADDRESS:", "АДРЕС СЕРВЕРА:"},
            {"REMOTE HOST PORT:", "ПОРТ:"},
            {"SUBMIT", "Выполнить"},
            {"RUSSIAN", "Русский"},
            {"LOGIN", "Логин"},
            {"PASSWORD", "Пароль"},
            {"REGISTER", "Вы новый пользователь? Зарегистрироваться"},
            {"ARGUMENT", "Аргумент"},
            {"COMMAND", "Команда"},
            {"Visual style", "Визуализация"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
