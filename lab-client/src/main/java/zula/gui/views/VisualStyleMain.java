package zula.gui.views;

import zula.client.ConnectionManager;
import zula.gui.controllers.ConnectionAndLoginScreenController;
import zula.util.Constants;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class VisualStyleMain {
    private static final int NORTH_PANEL_HEIGHT = Constants.SCREEN_HEIGHT / 10;
    private static final int CENTER_PANEL_HEIGHT = Constants.SCREEN_HEIGHT * 8 / 10;
    private static final int SOUTH_PANEL_HEIGHT = Constants.SCREEN_HEIGHT / 10;
    private final JFrame mainFrame;
    private final JButton backButton;
    private final JPanel northPanel = new JPanel();
    private final JPanel centralPanel = new JPanel();
    private final JPanel southPanel = new JPanel();
    private final ConnectionManager connectionManager;
    private CoordinatesDemo coordinatesDemo;
    private ResourceBundle currentBundle;
    private final ConnectionAndLoginScreenController connectionAndLoginScreenController = new ConnectionAndLoginScreenController();
    //нет смысла создавать отдельный контроллер, ConnectionAndLoginScreenController содержит необходимые методы
    private final JComboBox<String> languages = new JComboBox<>(Constants.LANGUAGES);

    public VisualStyleMain(JFrame mainFrame, ConnectionManager connectionManager, ResourceBundle currentBundle) {
        this.currentBundle = currentBundle;
        this.mainFrame = mainFrame;
        this.connectionManager = connectionManager;
        backButton = new JButton(currentBundle.getString("Back to normal"));

    }
    private void setLanguages() {
        languages.setSelectedItem(Constants.getNameByBundle(currentBundle));
        languages.setFont(Constants.MAIN_FONT);
        languages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String language = languages.getSelectedItem().toString();
                currentBundle = Constants.getBundleFromLanguageName(language);
                printGraphics();
            }
        });
        northPanel.add(languages);
    }
    private void setSettingsForPanels() {
        northPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, NORTH_PANEL_HEIGHT));
        centralPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, CENTER_PANEL_HEIGHT));
        southPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, SOUTH_PANEL_HEIGHT));
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
        backButton.setFont(Constants.MAIN_FONT);
        backButton.setText(currentBundle.getString("Back to normal"));
        setSettingsForPanels();
        setLanguages();
        centralPanel.add(coordinatesDemo);
        coordinatesDemo.startTimer();
        mainFrame.setContentPane(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectionAndLoginScreenController.moveToMainScreen(connectionManager, mainFrame, currentBundle);
            }
        });

    }


    public JFrame getMainFrame() {
        return mainFrame;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public ResourceBundle getCurrentBundle() {
        return currentBundle;
    }
}
