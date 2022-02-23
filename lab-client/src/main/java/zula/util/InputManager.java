package zula.util;


import zula.exceptions.EndOfFileException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputManager {
    private final Scanner scanner = new Scanner(System.in);
    private InputStreamReader inputStreamReader;
    private boolean fileReading = false;
    private String path = "";
    public String read(ConsoleManager consoleManager) {
        if (isFileReading()) {
            try {
                String command = readLine();
                consoleManager.getOutputManager().write(command);
                return command;
                } catch (EndOfFileException e) {
                  consoleManager.getOutputManager().write("Конец файла. Переход на ручной ввод");
                  return scanner.nextLine();
                } catch (IOException e) {
                consoleManager.getOutputManager().write("Ошибка при работе с файлом");
                fileReading = false;
                return scanner.nextLine();
            }

        } else {
            return scanner.nextLine();
        }
    }
    public String getPath() {
        return path;
    }
    public void setFileReading(boolean readingFromFile, String pathToFile) throws FileNotFoundException {
        inputStreamReader = new InputStreamReader(new FileInputStream(pathToFile));
        this.fileReading = readingFromFile;
        this.path = pathToFile;
    }


    public boolean isFileReading() {
        return fileReading;
    }
    public String readLine() throws IOException, EndOfFileException {
        StringBuilder readedLine = new StringBuilder();
        while (inputStreamReader.ready()) {

            char readedSymbol = (char) inputStreamReader.read();
            if (readedSymbol == '\n') {
                return readedLine.substring(0, readedLine.length() - 1);
            } else {
                readedLine.append(readedSymbol);
            }
        }

            fileReading = false;
            throw new EndOfFileException();



    }


}
