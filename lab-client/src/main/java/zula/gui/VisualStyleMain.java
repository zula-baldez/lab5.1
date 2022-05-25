package zula.gui;

import zula.client.ConnectionManager;
import zula.util.Constants;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class VisualStyleMain {
    private JFrame mainFrame;
    private final JButton backButton = new JButton("Back to normal");
    private final JPanel northPanel = new JPanel();
    private final JPanel centralPanel = new JPanel();
    private final JPanel southPanel = new JPanel();
    private final ConnectionManager connectionManager;
    CoordinatesDemo coordinatesDemo;
    private ResourceBundle currentBundle;
    public VisualStyleMain(JFrame mainFrame, ConnectionManager connectionManager, ResourceBundle currentBundle) {
        this.currentBundle = currentBundle;
        this.mainFrame = mainFrame;
        this.connectionManager = connectionManager;
    }
    public void setSettingsForPanels() {
        northPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight/10));
        centralPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight*8/10));
        southPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight/10));
        northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        centralPanel.setLayout(new GridLayout());
        southPanel.setLayout(new GridBagLayout());
        coordinatesDemo = new CoordinatesDemo(this);



    }



    public void printGraphics() {
        mainFrame.setVisible(true);
        JPanel mainPanel = new JPanel();
        centralPanel.removeAll();
        mainPanel.add(northPanel);
        mainPanel.add(centralPanel);
        mainPanel.add(southPanel);
        southPanel.add(backButton);
        backButton.setFont(Constants.mainFont);
        setSettingsForPanels();
        centralPanel.add(coordinatesDemo);
        coordinatesDemo.startTimer();
        mainFrame.setContentPane(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreen mainScreen = new MainScreen(connectionManager, mainFrame, currentBundle);
                mainScreen.startMain(true);
            }
        });

    }


    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JPanel getNorthPanel() {
        return northPanel;
    }

    public JPanel getCentralPanel() {
        return centralPanel;
    }

    public JPanel getSouthPanel() {
        return southPanel;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public ResourceBundle getCurrentBundle() {
        return currentBundle;
    }
}
