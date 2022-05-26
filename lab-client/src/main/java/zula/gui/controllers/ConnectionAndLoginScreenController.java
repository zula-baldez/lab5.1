package zula.gui.controllers;

import zula.client.ConnectionManager;
import zula.gui.LoginScreen;
import zula.gui.views.MainScreen;

import javax.swing.JFrame;
import java.util.ResourceBundle;

public class ConnectionAndLoginScreenController {
    public void moveToLogin(JFrame mainWindow, ResourceBundle currentBundle, ConnectionHandler connectionHandler) {
    LoginScreen loginScreen = new LoginScreen();
    loginScreen.startLogin(mainWindow, currentBundle, connectionHandler);
}
    public void moveToMainScreen(ConnectionManager connectionManager, JFrame mainWindow, ResourceBundle currentBundle) {
        MainScreen mainScreen = new MainScreen(connectionManager, mainWindow, currentBundle);
        mainScreen.startMain(true);
    }

}
