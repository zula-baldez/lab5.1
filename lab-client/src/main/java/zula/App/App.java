package zula.App;

import org.xml.sax.SAXException;
import zula.exceptions.WrongArgumentException;
import zula.exceptions.WrongCommandException;
import zula.parser.CommandReader;
import zula.parser.Mapper;
import zula.util.ConsoleManager;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class App {
    public void startApp(ConsoleManager consoleManager, String path) {
        try {
            readFile(consoleManager, path);
        } catch (IOException e) {
            System.out.println("Файл не найден или отсутствуют права доступа");
            return;
        } catch (WrongArgumentException e) {
            System.out.println("В содержимом XML - файла ошибка, данные записаны неверно");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        while (consoleManager.isProcessStillWorks()) {
            readAndExecute(consoleManager);
        }
    }

    public static void readAndExecute(ConsoleManager consoleManager) {
        consoleManager.getOutputManager().write("Введите команду!");
        String readedLine;
        readedLine = consoleManager.getInputManager().read(consoleManager);
        String command;
        try {
            command = CommandReader.commandReader(readedLine, consoleManager);

        } catch (WrongCommandException e) {
            consoleManager.getOutputManager().write("Такой команды не существует. Повторите ввод");
            return;
        }
        readedLine = (readedLine.replace(command, ""));
        if (readedLine.length() >= 1 && readedLine.charAt(0) == ' ') {
            readedLine = readedLine.substring(1);
        }
        if ("execute_script".equals(command) && readedLine.equals(consoleManager.getInputManager().getPath())) {
            consoleManager.getOutputManager().write("Угроза рекурсии!");
            return;
        }
        try {
            consoleManager.getListManager().getCommands().get(command).execute(readedLine, consoleManager);
        } catch (WrongArgumentException e) {
            consoleManager.getOutputManager().write("Аргументы неверные");
        }
    }


    private void readFile(ConsoleManager consoleManager, String path) throws WrongArgumentException, ParserConfigurationException, IOException, SAXException {
        consoleManager.getListManager().setPath(path);
        new Mapper(consoleManager).fromXML(path);


    }
}
