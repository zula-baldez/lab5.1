package zula.resources;

import java.util.HashMap;
import java.util.ListResourceBundle;

public class resource_lva extends ListResourceBundle {



    private static final Object[][] contents = {
            {"LANGUAGE", "Valoda"},
            {"REMOTE HOST ADDRESS:", "Attālās saimniekdatora adrese:"},
            {"REMOTE HOST PORT:", "Attālā saimniekdatora ports:"},
            {"SUBMIT", "Iesniegt"},
            {"LOGIN", "Pieslēgties"},
            {"PASSWORD", "Parole"},
            {"REGISTER", "Reģistrēties"},
            {"ARGUMENT", "Arguments"},
            {"COMMAND", "Pavēli"},
            {"Visual style", "Vizuālais stils"}
    };


    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
