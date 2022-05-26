package zula.client;


import zula.gui.ConnectionScreen;


public final class Client {
   private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        ConnectionScreen connectionScreen = new ConnectionScreen();
        connectionScreen.printScreen();

    }





}
