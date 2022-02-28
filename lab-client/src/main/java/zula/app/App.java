package zula.app;
import org.xml.sax.SAXException;
import zula.exceptions.WrongArgumentException;
import zula.exceptions.WrongCommandException;
import zula.util.CommandParser;
import zula.util.XmlManager;
import zula.util.ConsoleManager;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class App {
    public void startApp(ConsoleManager consoleManager, String path) {
        try {
            readFile(consoleManager, path);
        } catch (IOException e) {
            consoleManager.getOutputManager().write("Файл не найден или отсутствуют права доступа");
            return;
        } catch (WrongArgumentException e) {
            consoleManager.getOutputManager().write("В содержимом XML - файла ошибка, данные записаны неверно");
            return;
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            return;
        }
        while (consoleManager.isProcessStillWorks()) {
            readAndExecute(consoleManager);
        }
    }

    public static void readAndExecute(ConsoleManager consoleManager) {
        consoleManager.getOutputManager().write("Введите команду!");
        String readLine;
        readLine = consoleManager.getInputManager().read(consoleManager);
        String command;
        try {
            command = CommandParser.commandParse(readLine, consoleManager);

        } catch (WrongCommandException e) {
            consoleManager.getOutputManager().write("Такой команды не существует. Повторите ввод");
            return;
        }
        readLine = (readLine.replace(command, ""));
        if (readLine.length() >= 1 && readLine.charAt(0) == ' ') {
            readLine = readLine.substring(1);
        }
        if ("execute_script".equals(command) && consoleManager.getInputManager().containsFileInStack(readLine)) {
            consoleManager.getOutputManager().write("Угроза рекурсии!");
            return;
        }
        try {
            consoleManager.getListManager().getCloneOfCommands().get(command).execute(readLine, consoleManager);
        } catch (WrongArgumentException e) {
            consoleManager.getOutputManager().write("Аргументы неверные");
        }
    }


    private void readFile(ConsoleManager consoleManager, String path) throws WrongArgumentException, ParserConfigurationException, IOException, SAXException {
        consoleManager.getListManager().setPath(path);
        new XmlManager(consoleManager).fromXML(path);
    }
}
