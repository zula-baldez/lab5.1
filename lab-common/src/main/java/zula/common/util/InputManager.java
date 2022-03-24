package zula.common.util;



import zula.common.exceptions.EndOfFileException;
import zula.common.exceptions.PrintException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Stack;

public class InputManager {
    private Stack<InputStreamReader> readers = new Stack<>();
    private Stack<String> files = new Stack<>();
    private final Scanner scanner = new Scanner(System.in);
    private InputStreamReader currentInputStreamReader;
    private boolean fileReading = false;
    public InputManager(InputStreamReader inputStreamReader) {
        currentInputStreamReader = inputStreamReader;
    }


    public String read(IoManager ioManager) throws PrintException {
        if (isFileReading()) {
            try {
                String command = readLine();
                ioManager.getOutputManager().write(command);
                return command;
                } catch (EndOfFileException e) {
                  ioManager.getOutputManager().write("Конец файла. Переход на ручной ввод");
                  return scanner.nextLine();
                } catch (IOException e) {
                ioManager.getOutputManager().write("Ошибка при работе с файлом");
                fileReading = false;
                return scanner.nextLine();
            }

        } else {
            return scanner.nextLine();
        }
    }
    public boolean containsFileInStack(String path) {
        if (files.contains(path)) {
            return true;
        }
        return false;

    }
    public void setFileReading(boolean readingFromFile, String pathToFile) throws FileNotFoundException {
        InputStreamReader buffer = currentInputStreamReader;
        currentInputStreamReader = new InputStreamReader(new FileInputStream(pathToFile), StandardCharsets.UTF_8);
        this.fileReading = readingFromFile;
        files.add(pathToFile);
        if (buffer != null) {
            readers.add(buffer);
        }

    }


    public boolean isFileReading() {
        return fileReading;
    }

    public String readLine() throws IOException, EndOfFileException {
        StringBuilder readedLine = new StringBuilder();
        while (currentInputStreamReader.ready()) {
            char readedSymbol = (char) currentInputStreamReader.read();
            if (readedSymbol == '\n' | readedSymbol  == '\r') {
                if (readedSymbol == '\r') {
                    currentInputStreamReader.read();
                }
                return readedLine.toString();
            } else {
                readedLine.append(readedSymbol);
            }
        }
            if (readers.size() == 0) {
                fileReading = false;
                throw new EndOfFileException();
            } else {
                InputStreamReader isr = readers.pop();
                currentInputStreamReader = isr;
                files.pop();
                return readLine();
            }


    }


}
