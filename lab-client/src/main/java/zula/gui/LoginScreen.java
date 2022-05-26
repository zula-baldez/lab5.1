package zula.gui;

import zula.gui.controllers.ConnectionHandler;
import zula.gui.views.ConnectionAndLoginScreenFabric;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ResourceBundle;

public class LoginScreen {
    public void startLogin(JFrame mainFrame, ResourceBundle resourceBundle, ConnectionHandler connectionHandler) {
        ConnectionAndLoginScreenFabric connectionAndLoginScreenFabric = new ConnectionAndLoginScreenFabric(resourceBundle, connectionHandler);
        JPanel mainPanel = connectionAndLoginScreenFabric.createLoginScreen(mainFrame);
        mainFrame.getContentPane().removeAll();
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
    }

}
