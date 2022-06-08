package zula.resources;


import java.util.ListResourceBundle;

public class Resource_ru_RU extends ListResourceBundle {


    private static final Object[][] CONTENTS = {
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
            {"Visual style", "Визуализация"},
            {"Back to normal", "Вернуться"},
            {"Impossible to get commands", "Не удалось получить список доступных команд"},
            {"Enter command!", "Введите команду!"},
            {"There is no such command", "Такой команды не существует"},
            {"Error sending response", "Ошибка при отправке на сервер"},
            {"Error while getting response", "Ошибка при получении ответа"},
            {"The Threat of Recursion!", "Угроза рекурсии!"},
            {"File not found or no permissions", "Файл не найден или отсутствуют права доступа"},
            {"Wrong args", "Неверные аргументы"},
            {"FIELD", "Поле"},
            {"id", "id"},
            {"name", "имя"},
            {"x", "х"},
            {"y", "у"},
            {"creationDate", "Дата создания"},
            {"age", "возраст"},
            {"wingspan", "размах крыльев"},
            {"color", "цвет"},
            {"type", "тип"},
            {"depth", "глубина"},
            {"num. of tres.", "количество сокровищ"},
            {"Number ot Treasures", "количество сокровищ"},
            {"owner_id", "id создателя"},
            {"value", "значение"},
            {"REQUIREMENT", "ТРЕБОВАНИЯ"},
            {"NOT NULL", "Не пустое"},
            {">=-23, double", ">=-23, нецелое"},
            {"<=160, integer", "<=160, целое"},
            {"long, >0", "целое, >0"},
            {"float, >0", "нецелое, >0"},
            {"may be null", "может быть пустым"},
            {"Float, may be null", "Нецелое, может быть пустым"},
            {"Double, may be null", "Нецелое, может быть пустым"},
            {"CHECK THE CURRENCY OF THE DATA", "Проверьте корректность данных!"},
            {"Delete", "Удалить"},
            {"It is not your dragon", "Это не ваш дракон("},
            {"OK", "OK"},
            {"Filter!", "Фильтровать!"},
            {"More than", "Больше чем"},
            {"Less than", "Меньше чем"},
            {"Equals", "Равно"},
            {"help", "Помощь"},
            {"info", "Информация"},
            {"show", "Показать коллекцию"},
            {"update_id", "Переделать дракона"},
            {"remove_by_id", "Удалить дракона"},
            {"clear", "Отчистить все"},
            {"execute_script", "Выполнить скрипт"},
            {"exit", "Завершение работы"},
            {"remove_last", "Удалить последнего"},
            {"remove_lower", "Удалить меньших чем"},
            {"reorder", "Перемешать"},
            {"average_of_wingspan", "Ср. знач. размаха"},
            {"print_ascending", "По возрастанию"},
            {"print_field_ascending_wingspan", "wingspan по возрастанию"},
            {"add", "добавить дракона"},
            {"No arg needed", "Аргумент не нужен"},
            {"ID(int) needed", "Нужен id"},
            {"Table is correct", "На таблице отображается корректный результат"},
            {"Id must be a number!", "ID должен быть числом!"},
            {"From a to z", "По возрастанию"},
            {"From z to a", "По убыванию"},
            {"Sort!", "Отсортировать!"},
            {"Successful erasing!", "Успешное удаление"},
            {"Nothing to delete", "нечего удалять"},
            {"Successful execution!", "Команда выполнена!"},
            {"Impossible to add element", "Невозможно добавить элемент в коллекцию"},
            {"Impossible to change data base", "Невозможно изменить состояние базы данных"},
            {"There is no element with such id or you are not its creator", "Элемента с таким id не существует, или не вы его создатель"},
            {"Good buy!", "До свидания!"},
            {"Successful authorization", "Успешная авторизация!"},
            {"Failed to login, please check the correctness of the entered data", "Не удалось авторизоваться, проверьте правильность введенных данных"},
            {"Successful registration", "Успешная регистрация"},
            {"Error registering, please try another username", "не удалось зарегистрироваться, проверьте правильно введенных данных"},
            {"Removal completed successfully!", "Успешное удаление"},
            {"Either this id does not exist, or you are not its creator:(", "Элемента с таким id не существует, или не вы его создатель"},
            {"Nothing to Remove", "Нечего удалять"},
            {"There is no element with the given id", "Элемента с заданным id не существует"},
            {"Collection Shuffled", "Коллекция перемешана"},
            {"Object with this id does not exist or you do not have permissions", "Объект с таким id не существует или у вас нет прав доступа"},
            {"ERROR IN CONNECTION", "Ошибка при присоединении"},
            {"PORT MUST BE A NUMBER", "Порт должен быть числом"},
            {"Removal completed successfully", "Успешное удаление"},
            {"SERVER UMER", "Сервер умер"},
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
                    "help : показать справку по доступным командам\n"
                            + "info : вывести на стандартный вывод информацию о коллекции (тип, дату инициализации, количество элементов и т. д.)\n"
                            + "show : вывести на стандартный вывод все элементы коллекции в строковом представлении\n"
                            + "добавить {элемент} : добавить новый элемент в коллекцию\n"
                            + "update_id {element}: обновить значение элемента коллекции, идентификатор которого равен заданному\n"
                            + "remove_by_id id : удалить элемент из коллекции по его id\n"
                            + "очистить: очистить коллекцию\n"
                            + "execute_script имя_файла : чтение и выполнение скрипта из указанного файла. Скрипт содержит команды в том виде, в каком их вводит пользователь в интерактивном режиме.\n"
                            + "exit : выйти из программы (без сохранения в файл)\n"
                            + "remove_last : удалить последний элемент из коллекции\n"
                            + "remove_lower {element} : удалить из коллекции все элементы, которые меньше заданного\n"
                            + "переупорядочить: отсортировать коллекцию в порядке, обратном текущему\n"
                            + "average_of_wingspan: отображать среднее значение поля размаха крыльев для всех предметов в коллекции\n"
                            + "print_ascending : печатать элементы коллекции в порядке возрастания\n"
                            + "print_field_ascending_wingspan : Распечатать значения поля размаха крыльев всех элементов в порядке возрастания"
            },
            {"size - ", "размер - "},
            {"date of creation - ", "дата создания - "},
            {"type - ", "тип - "},
            {"Script is completing...", "Скрипт выполняется..."}

    };

    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
}
