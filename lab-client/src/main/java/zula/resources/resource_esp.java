package zula.resources;

import java.util.HashMap;
import java.util.ListResourceBundle;

public class resource_esp extends ListResourceBundle {



    private static final Object[][] contents = {
            {"LANGUAGE", "Idiome"},
            {"REMOTE HOST ADDRESS:", "Dirección de host remoto:"},
            {"REMOTE HOST PORT:", "Puerto de host remoto:"},
            {"SUBMIT", "Entregar"},
/*
            {"RUSSIAN", "Русский"},
*/
            {"LOGIN", "Acceso"},
            {"PASSWORD", "Clave"},
            {"REGISTER", "Registro"},
            {"ARGUMENT", "Argumento"},
            {"COMMAND", "Dominio"},
            {"Visual style", "Estilo visual"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
