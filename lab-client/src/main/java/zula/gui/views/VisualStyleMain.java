package zula.gui.views;

import zula.client.ConnectionManager;
import zula.gui.controllers.ConnectionAndLoginScreenController;
import zula.util.BasicGUIElementsFabric;
import zula.util.Constants;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ResourceBundle;

public class VisualStyleMain {
    private static final int NORTH_PANEL_HEIGHT = Constants.SCREEN_HEIGHT / 10;
    private static final int CENTER_PANEL_HEIGHT = Constants.SCREEN_HEIGHT * 7 / 10;
    private static final int SOUTH_PANEL_HEIGHT = Constants.SCREEN_HEIGHT / 10;
    private static final int ERROR_PANEL_HEIGHT = Constants.SCREEN_HEIGHT / 10;
    private static final int ERROR_PANEL_WIDTH = Constants.SCREEN_WIDTH / 10;
    private final JFrame mainFrame;
    private final JButton backButton;
    private JPanel northPanel = new JPanel();
    private JPanel centralPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private ConnectionManager connectionManager;
    private CoordinatesDemo coordinatesDemo;
    private ResourceBundle currentBundle;
    private final ConnectionAndLoginScreenController connectionAndLoginScreenController = new ConnectionAndLoginScreenController();
    //нет смысла создавать отдельный контроллер, ConnectionAndLoginScreenController содержит необходимые методы
    private final JComboBox<String> languages = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);

    public VisualStyleMain(JFrame mainFrame, ConnectionManager connectionManager, ResourceBundle currentBundle) {
        this.currentBundle = currentBundle;
        this.mainFrame = mainFrame;
        this.connectionManager = connectionManager;
        backButton = BasicGUIElementsFabric.createBasicButton(currentBundle.getString("Back to normal"));

    }

    private void setLanguages() {
        languages.setSelectedItem(Constants.getNameByBundle(currentBundle));
        languages.setFont(Constants.MAIN_FONT);
        languages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String language = languages.getSelectedItem().toString();
                currentBundle = Constants.getBundleFromLanguageName(language);
                VisualStyleMain visualStyleMain = new VisualStyleMain(mainFrame, connectionManager, currentBundle);
                visualStyleMain.printGraphics();
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
        try {
            coordinatesDemo = new CoordinatesDemo(this);
        } catch (IOException e) {
            return;
        }
    }


    public void printGraphics() {
        northPanel = new JPanel();
        centralPanel = new JPanel();
        southPanel = new JPanel();
        mainFrame.setVisible(true);
        JPanel mainPanel = new JPanel();
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

    public void errorHandler(String s) {
        JFrame errorFrame = new JFrame();
        errorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        errorFrame.add(BasicGUIElementsFabric.createBasicLabel(currentBundle.getString(s)));
        errorFrame.pack();
        errorFrame.setResizable(false);
        errorFrame.setVisible(true);
        errorFrame.setLocationRelativeTo(null);
        mainFrame.setEnabled(false);

    }

}
