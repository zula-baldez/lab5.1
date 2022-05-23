package zula.resources;

import java.util.HashMap;
import java.util.ListResourceBundle;

public class resource_mkd extends ListResourceBundle {



    private static final Object[][] contents = {
            {"LANGUAGE", "Јазик"},
            {"REMOTE HOST ADDRESS:", "Адреса на далечински домаќин:"},
            {"REMOTE HOST PORT:", "Порта за далечински домаќин::"},
            {"SUBMIT", "Поднесете"},
            {"LOGIN", "Логирај Се"},
            {"PASSWORD", "Лозинка"},
            {"REGISTER", "Регистрирајте се"},
            {"ARGUMENT", "Аргумент"},
            {"COMMAND", "Команда"},
            {"Visual style", "Визуелен стил"},
    };



    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
