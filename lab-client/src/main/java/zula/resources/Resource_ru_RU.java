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
            {"ERROR IN CONNECTION", "Ошибка при присоединении" },
            {"PORT MUST BE A NUMBER", "Порт должен быть числом"},

    };

    @Override
    protected Object[][] getContents() {
        return CONTENTS;
    }
}
