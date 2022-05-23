package zula.client;

import zula.common.exceptions.PrintException;
import zula.gui.ConnectionScreen;

public final class Client {
   private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws PrintException {

/*

        MainScreen mainScreen = new MainScreen();
        mainScreen.startMain();
*/

        ConnectionScreen connectionScreen = new ConnectionScreen();
        connectionScreen.printScreen();

    }





}
