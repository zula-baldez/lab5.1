package zula.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ResourceBundle;

public class LoginScreen {
    public void startLogin(JFrame mainFrame, ResourceBundle resourceBundle) {
        ConnectionAndLoginScreenFabric connectionAndLoginScreenFabric = new ConnectionAndLoginScreenFabric(resourceBundle);
        JPanel mainPanel = connectionAndLoginScreenFabric.createLoginScreen(mainFrame);
        mainFrame.getContentPane().removeAll();
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
    }

}
