package zula.resources;

import java.util.ListResourceBundle;

public class Resource_lv_LV extends ListResourceBundle {



    private static final Object[][] CONTENTS = {
            {"LANGUAGE", "Valoda"},
            {"REMOTE HOST ADDRESS:", "Attālās saimniekdatora adrese:"},
            {"REMOTE HOST PORT:", "Attālā saimniekdatora ports:"},
            {"SUBMIT", "Iesniegt"},
            {"LOGIN", "Pieslēgties"},
            {"PASSWORD", "Parole"},
            {"REGISTER", "Reģistrēties"},
            {"ARGUMENT", "Arguments"},
            {"COMMAND", "Pavēli"},
            {"Visual style", "Vizuālais stils"},
            {"Back to normal", "Atgriezties normālā stāvoklī"},
            {"Impossible to get commands", "Nav iespējams saņemt komandas"},
            {"Enter command!", "Ievadiet komandu!"},
            {"There is no such command", "Tādas komandas nav"},
            {"Error sending response", "Nosūtot atbildi, radās kļūda"},
            {"Error while getting response", "Saņemot atbildi, radās kļūda"},
            {"The Threat of Recursion!", "Rekursijas draudi!"},
            {"File not found or no permissions", "Fails nav atrasts vai nav atļauju"},
            {"Wrong args", "Nepareizi args"},
            {"FIELD", "LAUKS"},
            {"id", "id"},
            {"name", "nosaukums"},
            {"x", "х"},
            {"y", "у"},
            {"creationDate", "izveidošanas datums"},
            {"age", "vecums"},
            {"wingspan", "spārnu platums"},
            {"color", "krāsa"},
            {"type", "veids"},
            {"depth", "dziļums"},
            {"num. of tres.", "numuru. no kokiem."},
            {"Number ot Treasures", "Dārgumu skaits"},
            {"owner_id", "īpašnieka_id"},
            {"value", "vērtību"},
            {"REQUIREMENT", "PRASĪBA"},
            {"NOT NULL", "NAV NULL"},
            {">=-23, double", ">=-23, dubultā"},
            {"<=160, integer", "<=160, vesels skaitlis"},
            {"long, >0", "garš, >0"},
            {"float, >0", "pludiņš, >0"},
            {"may be null", "var būt nulle"},
            {"Float, may be null", "Float, var būt nulle"},
            {"Double, may be null", "Dubults, var būt nulle"},
            {"CHECK THE CURRENCY OF THE DATA", "PĀRBAUDIET DATU VALŪTU"},
            {"Delete", "Dzēst"},
            {"It is not your dragon", "Tas nav tavs pūķis("},
            {"OK", "OK"},
            {"Filter!", "Filtrs!"},
            {"More than", "Vairāk par"},
            {"Less than", "Mazāk nekā"},
            {"Equals", "Vienāds"},
            {"help", "palīdzēt"},
            {"info", "info"},
            {"show", "parādīt"},
            {"update_id", "atjaunināt id"},
            {"remove_by_id", "noņemt_pēc_id"},
            {"clear", "skaidrs"},
            {"execute_script", "izpildīt_skriptu"},
            {"exit", "Izeja"},
            {"remove_last", "noņemt_pēdējo"},
            {"remove_lower", "noņemt_nolaist"},
            {"reorder", "pārkārtot"},
            {"average_of_wingspan", "vidējais_spārnu_plētums"},
            {"print_ascending", "drukāt_augošā secībā"},
            {"print_field_ascending_wingspan", "drukas lauks augošs spārnu"},
            {"add", "pievienot"},
            {"No arg needed", "Nav nepieciešams arg"},
            {"ID(int) needed", "Nepieciešams ID(int)."},
            {"Table is correct", "Tabula ir pareiza"},
            {"Id must be a number!", "Id ir jābūt skaitlim!"},
            {"From a to z", "No a līdz z"},
            {"From z to a", "No z līdz a"},
            {"Sort!", "Kārtot!"},
            {"Successful erasing!", "Veiksmīga dzēšana!"},
            {"Nothing to delete", "Nav ko dzēst"},
            {"Successful execution!", "Veiksmīga izpilde!"},
            {"Impossible to add element", "Nevar pievienot elementu"},
            {"Impossible to change data base", "Nevar mainīt datu bāzi"},
            {"There is no element with such id or you are not its creator", "Nav neviena elementa ar šādu ID, vai arī jūs neesat tā radītājs"},
            {"Good buy!", "Labs pirkums!"},
            {"Successful authorization", "Veiksmīga autorizācija"},
            {"Failed to login, please check the correctness of the entered data", "Neizdevās pieteikties, lūdzu, pārbaudiet ievadīto datu pareizību"},
            {"Successful registration", "Veiksmīga reģistrācija"},
            {"Error registering, please try another username", "Reģistrējoties radās kļūda, lūdzu, mēģiniet citu lietotājvārdu"},
            {"Removal completed successfully!", "Noņemšana ir veiksmīgi pabeigta!"},
            {"Either this id does not exist, or you are not its creator:(", "Vai nu šis ID neeksistē, vai arī jūs neesat tā veidotājs:("},
            {"Nothing to Remove", "Nav ko noņemt"},
            {"There is no element with the given id", "Nav neviena elementa ar norādīto ID"},
            {"Collection Shuffled", "Kolekcija sajaukta"},
            {"Object with this id does not exist or you do not have permissions", "Objekts ar šo ID neeksistē vai jums nav atļauju"},
            {"ERROR IN CONNECTION", "KĻŪDA SAVIENOJUMĀ"},
            {"PORT MUST BE A NUMBER", "OSTĀ JĀBŪT CIPARAM"},
            {"Removal completed successfully", "Noņemšana ir veiksmīgi pabeigta"},
            {"SERVER UMER", "serveris nomira"},
            {"help : display help on available commands\n"
                    + "info : print to standard output information about the collection (type, initialization date, number of elements, etc.)\n"
                    + "show : print to standard output all elements of the collection in string representation\n"
                    + "add {element} : add a new element to the collection\n"
                    + "update_id {element} : update the value of the collection element whose id is equal to the given\n"
                    + "remove_by_id id : remove an element from the collection by its id\n"
                    + "clear : clear collection\n"
                    + "execute_script file_name : read and execute a script from the specified file. The script contains commands in the same form as the user enters them in interactive mode.\n"
                    + "exit : exit program (without saving to file)\n"
                    + "remove_last : remove the last element from the collection\n"
                    + "remove_lower {element} : remove all elements from the collection that are smaller than the given one\n"
                    + "reorder : sort the collection in reverse order of the current one\n"
                    + "average_of_wingspan : display the average value of the wingspan field for all items in the collection\n"
                    + "print_ascending : print collection items in ascending order\n"
                    + "print_field_ascending_wingspan : Print the values of the wingspan field of all elements in ascending order",
                    "help : parādīt palīdzību par pieejamajām komandām\n"
                            + "informācija : drukāt uz standarta izvades informāciju par kolekciju (tips, inicializācijas datums, elementu skaits utt.)\n"
                            + "rādīt : drukāt uz standarta izvadi visus kolekcijas elementus virknes attēlojumā\n"
                            + "pievienot {elementu} : pievienojiet kolekcijai jaunu elementu\n"
                            + "update_id {element} : atjauniniet kolekcijas elementa vērtību, kura ID ir vienāds ar doto\n"
                            + "remove_by_id id : noņemiet elementu no kolekcijas pēc tā id\n"
                            + "notīrīt : notīrīt kolekciju\n"
                            + "execute_script file_name : lasīt un izpildīt skriptu no norādītā faila. Skripts satur komandas tādā pašā formā, kādā lietotājs tās ievada interaktīvajā režīmā.\n"
                            + "iziet : iziet no programmas (bez saglabāšanas failā)\n"
                            + "remove_last : noņemt pēdējo elementu no kolekcijas\n"
                            + "remove_lower {element} : noņemiet no kolekcijas visus elementus, kas ir mazāki par norādīto\n"
                            + "pārkārtot : kārtot kolekciju apgrieztā secībā pašreizējai\n"
                            + "average_of_wingspan : parāda vidējo spārnu platuma lauka vērtību visiem kolekcijas vienumiem\n"
                            + "print_ascending : drukas kolekcijas vienumi augošā secībā\n"
                            + "print_field_ascending_wingspan : drukājiet visu elementu spārnu platuma lauka vērtības augošā secībā"
            },
            {"size - ", "Izmērs -"},
            {"date of creation - ", "izveides datums - "},
            {"type - ", "tips - "},
            {"Script is completing...", "Skripts tiek pabeigts..."}


    };


    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
}
