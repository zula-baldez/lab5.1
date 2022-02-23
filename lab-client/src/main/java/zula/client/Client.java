package zula.client;

import zula.App.App;
import zula.util.ConsoleManager;
import zula.util.InputManager;
import zula.util.ListManager;
import zula.util.OutputManager;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        ConsoleManager consoleManager = new ConsoleManager(new InputManager(), new OutputManager(), new ListManager());
        App app = new App();
        if (args.length != 1) {
            consoleManager.getOutputManager().write("Неверный аргумент командной строки");
            return;
        }
        app.startApp(consoleManager, args[0]);
    }
}
