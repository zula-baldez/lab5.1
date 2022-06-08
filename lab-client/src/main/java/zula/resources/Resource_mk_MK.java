package zula.resources;

import java.util.ListResourceBundle;

public class Resource_mk_MK extends ListResourceBundle {



    private static final Object[][] CONTENTS = {
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
            {"Back to normal", "Врати се во нормала"},
            {"Impossible to get commands", "Невозможно е да се добијат команди"},
            {"Enter command!", "Внесете команда!"},
            {"There is no such command", "Не постои таква команда"},
            {"Error sending response", "Грешка при испраќање одговор"},
            {"Error while getting response", "Грешка при добивање одговор"},
            {"The Threat of Recursion!", "Заканата од рекурзија!"},
            {"File not found or no permissions", "Датотеката не е пронајдена или нема дозволи"},
            {"Wrong args", "Погрешни арги"},
                {"FIELD", "ПОЛЕ"},
            {"id", "id"},
            {"name", "име"},
            {"x", "х"},
            {"y", "у"},
            {"creationDate", "датум на производство"},
            {"age", "возраста"},
            {"wingspan", "распон на крилјата"},
            {"color", "боја"},
            {"type", "тип"},
            {"depth", "длабочина"},
            {"num. of tres.", "број. на дрвјата."},
            {"Number ot Treasures", "Број на богатства"},
            {"owner_id", "ИД на сопственикот"},
            {"value", "вредност"},
            {"REQUIREMENT", "БАРАЊЕ"},
            {"NOT NULL", "НЕ НУЛАТНИ"},
            {">=-23, double", ">=-23, двојно"},
            {"<=160, integer", "<=160, цел број"},
            {"long, >0", "долго, > 0"},
            {"float, >0", "плови, > 0"},
            {"may be null", "може да биде нула"},
            {"Float, may be null", "Плови, може да биде нула"},
            {"Double, may be null", "Двојно, може да биде нула"},
            {"CHECK THE CURRENCY OF THE DATA", "ПРОВЕРЕТЕ ЈА ВАЛУТАТА НА ПОДАТОЦИТЕ"},
            {"Delete", "Избриши"},
            {"It is not your dragon", "Тоа не е твој змеј("},
            {"OK", "OK"},
            {"Filter!", "Филтрирај!"},
            {"More than", "Повеќе од"},
            {"Less than", "Помалку од"},
            {"Equals", "Еднакви"},
            {"help", "помош"},
            {"info", "инфо"},
            {"show", "покажуваат"},
            {"update_id", "ажурирање id"},
            {"remove_by_id", "отстрани со ид"},
            {"clear", "јасно"},
            {"execute_script", "изврши скрипта"},
            {"exit", "излез"},
            {"remove_last", "отстранете го последното"},
            {"remove_lower", "отстрани пониски"},
            {"reorder", "пренарачување"},
            {"average_of_wingspan", "просечен распон на крилјата"},
            {"print_ascending", "печати растечки"},
            {"print_field_ascending_wingspan", "поле за печатење растечки"},
            {"add", "додадете"},
            {"No arg needed", "Не е потребен арг"},
            {"ID(int) needed", "Потребен е ID(int)."},
            {"Table is correct", "Табелата е точна"},
            {"Id must be a number!", "ИД мора да биде број!"},
            {"From a to z", "Од а до ш"},
            {"From z to a", "Од ш до a"},
            {"Sort!", "Подреди!"},
            {"Successful erasing!", "Успешно бришење!"},
            {"Nothing to delete", "Ништо да се избрише"},
            {"Successful execution!", "Успешно извршување!"},
            {"Impossible to add element", "Невозможно е да се додаде елемент"},
            {"Impossible to change data base", "Невозможно е да се смени базата на податоци"},
            {"There is no element with such id or you are not its creator", "Нема елемент со таков ид или вие не сте негов креатор"},
            {"Good buy!", "Добро купување!"},
            {"Successful authorization", "Успешно овластување"},
            {"Failed to login, please check the correctness of the entered data", "Не успеав да се најавите, проверете ја точноста на внесените податоци"},
            {"Successful registration", "Успешна регистрација"},
            {"Error registering, please try another username", "Грешка при регистрација, ве молиме обидете се со друго корисничко име"},
            {"Removal completed successfully!", "Отстранувањето заврши успешно!"},
            {"Either this id does not exist, or you are not its creator:(", "Или овој идентификатор не постои, или вие не сте неговиот креатор:("},
            {"Nothing to Remove", "Ништо да се отстрани"},
            {"There is no element with the given id", "Нема елемент со дадениот id"},
            {"Collection Shuffled", "Збирка измешана"},
            {"Object with this id does not exist or you do not have permissions", "Објектот со овој ID не постои или немате дозволи"},
            {"ERROR IN CONNECTION", "ГРЕШКА ВО ПОВРЗУВАЊЕ"},
            {"PORT MUST BE A NUMBER", "ПРИСТАНИЕТО МОРА ДА БИДЕ БРОЈ"},
            {"Removal completed successfully", "Отстранувањето е успешно завршено"},
            {"SERVER UMER", "серверот почина"},
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
                    "помош: прикажувај помош за достапните команди\n"
                            + "информации: печатете на стандардни излезни информации за колекцијата (тип, датум на иницијализација, број на елементи итн.)\n"
                            + "покажи : печати на стандарден излез сите елементи од колекцијата во претставата низа\n"
                            + "add {element} : додадете нов елемент во колекцијата\n"
                            + "update_id {element} : ажурирај ја вредноста на елементот за собирање чиј id е еднаков на дадениот\n"
                            + "remove_by_id id : отстрани елемент од колекцијата според неговиот id\n"
                            + "clear : clear collection\n"
                            + "execute_script file_name : читање и извршување на скрипта од наведената датотека. Скриптата содржи команди во иста форма како што корисникот ги внесува во интерактивен режим.\n"
                            + "излез : излез од програма (без зачувување во датотека)\n"
                            + "remove_last : отстранете го последниот елемент од колекцијата\n"
                            + "remove_lower {element} : отстранете ги сите елементи од колекцијата што се помали од дадената\n"
                            + "прередување : подредете ја колекцијата по обратен редослед од сегашната\n"
                            + "average_of_wingspan : прикажување на просечната вредност на полето за распон на крилјата за сите ставки во колекцијата\n"
                            + "print_ascending : печати ставки од колекција во растечки редослед\n"
                            + "print_field_ascending_wingspan : Печатете ги вредностите на полето за распон на крилата на сите елементи во растечки редослед"    },

            {"size - ", "големина - "},
            {"date of creation - ", "датум на создавање -"},
            {"type - ", "тип - "},
            {"Script is completing...", "Скриптата се комплетира..."}
    };



    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
}
