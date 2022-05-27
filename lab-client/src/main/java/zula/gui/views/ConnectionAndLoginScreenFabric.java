package zula.gui.views;

import zula.gui.controllers.ConnectionAndLoginScreenController;
import zula.gui.controllers.ConnectionHandler;
import zula.util.BasicGUIElementsFabric;
import zula.util.Constants;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/*
    Создание окон коннекта и логина
*/
public class ConnectionAndLoginScreenFabric {
    private static final int SEVEN_PANELS = 7;
    private static final int EIGHT_PANELS = 8;
    private static final int DEFAULT_THICKNESS = 3;
    private int amountOfPanels = SEVEN_PANELS; //can change
    private final int relativeWidthDisplacementForLanguagesList = Constants.SCREEN_WIDTH / 50;
    private final int relativeHeightDisplacementForLanguagesList = Constants.SCREEN_HEIGHT / 25; //Константы, управляющие размерами объектов и зависящие от размера экрана
    private final int relativeWidthDisplacementForTextFields = Constants.SCREEN_WIDTH * 8 / 10;
    private final int relativeHeightDisplacementForTextFields = Constants.SCREEN_HEIGHT / 20;
    private final JPanel languageListPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, relativeWidthDisplacementForLanguagesList, relativeHeightDisplacementForLanguagesList));
    private final JPanel addressTextPanel = new JPanel(new BorderLayout());
    private final JPanel addressTextFieldPanel = new JPanel(new GridBagLayout());
    private final JPanel portTextFieldPanel = new JPanel(new GridBagLayout());
    private final JPanel portTextPanel = new JPanel(new BorderLayout());
    private final JPanel submitButtonPanel = new JPanel(new GridBagLayout());
    private final JPanel errorFieldPanel = new JPanel(new GridBagLayout());
    private final JPanel secondButtonPanel = new JPanel(new GridBagLayout()); //can be not in use, if it not in use than we don't count it
    private JButton submitButton;
    private JTextField fieldForAddress;
    private JLabel addressText;
    private JLabel portText;
    private JTextField fieldForPort;
    private JButton secondButton;
    private final JComboBox<String> listToChooseLanguage = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
    private ResourceBundle currentBundle;
    private ConnectionHandler connectionHandler = new ConnectionHandler();
    private final ConnectionAndLoginScreenController connectionAndLoginScreenController = new ConnectionAndLoginScreenController();

    public ConnectionAndLoginScreenFabric(ResourceBundle resourceBundle) {
        this.currentBundle = resourceBundle;
    }
    public ConnectionAndLoginScreenFabric(ResourceBundle resourceBundle, ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
        this.currentBundle = resourceBundle;
    }

    private void setPanelsSizeDueToAmountOfPanels() {
        languageListPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        addressTextPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        addressTextFieldPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        portTextPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));

        portTextFieldPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        submitButtonPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        errorFieldPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
        secondButtonPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT / amountOfPanels));
    }

    public JTextField createCenteredJField() {
        JTextField jTextField = new JTextField(SwingConstants.CENTER);
        jTextField.setFont(Constants.MAIN_FONT);
        jTextField.setPreferredSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jTextField.setMinimumSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jTextField.setMaximumSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jTextField.setBorder(BorderFactory.createLineBorder(Constants.MAIN_COLOR, DEFAULT_THICKNESS));
        return jTextField;
    }

    private void printError(String error, JFrame mainFrame) {
        JLabel jLabel = new JLabel();
        jLabel.setText(error);
        jLabel.setFont(Constants.MAIN_FONT);
        jLabel.setBorder(BorderFactory.createLineBorder(Color.RED, DEFAULT_THICKNESS));

        errorFieldPanel.removeAll();
        errorFieldPanel.add(jLabel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }


    public JPanel createPanelWithSevenLayers(String nameOfTheFrame, String valueOfTheFirstJLabel, String valueOfTheSecondJLabel, String textOfTheFirstButton, JFrame mainWindow) {
        mainWindow.setTitle(nameOfTheFrame);
        setPanelsSizeDueToAmountOfPanels();
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(languageListPanel);
        mainPanel.add(addressTextPanel);
        mainPanel.add(addressTextFieldPanel);
        mainPanel.add(portTextPanel);
        mainPanel.add(portTextFieldPanel);
        mainPanel.add(submitButtonPanel);
        mainPanel.add(errorFieldPanel);
        listToChooseLanguage.setFont(Constants.MAIN_FONT);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(currentBundle));
        languageListPanel.add(listToChooseLanguage);
        //Host address text
        addressText = new JLabel(currentBundle.getString(valueOfTheFirstJLabel), SwingConstants.CENTER);
        addressText.setFont(Constants.MAIN_FONT);
        addressTextPanel.add(addressText, BorderLayout.CENTER);
        //TextField for getting address
        fieldForAddress = createCenteredJField();
        addressTextFieldPanel.add(fieldForAddress);
        //host port text
        portText = new JLabel(currentBundle.getString(valueOfTheSecondJLabel), SwingConstants.CENTER);
        portText.setFont(Constants.MAIN_FONT);
        portTextPanel.add(portText, BorderLayout.CENTER);
        //Textfield for getting port
        fieldForPort = createCenteredJField();
        portTextFieldPanel.add(fieldForPort);
        //Submit button
        submitButton = BasicGUIElementsFabric.createBasicButton(currentBundle.getString(textOfTheFirstButton));
        submitButton.setFont(Constants.MAIN_FONT);
        submitButtonPanel.add(submitButton);
        return mainPanel;
    }


    public JPanel createFrameWithEightLayers(String nameOfTheFrame, String valueOfTheFirstJLabel, String valueOfTheSecondJLabel, String textOfTheFirstButton, String textOfTheSecondButton, JFrame mainFrame) {
        amountOfPanels = EIGHT_PANELS;
        JPanel mainPanel = createPanelWithSevenLayers(nameOfTheFrame, valueOfTheFirstJLabel, valueOfTheSecondJLabel, textOfTheFirstButton, mainFrame);
        mainPanel.setMaximumSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        secondButton = BasicGUIElementsFabric.createBasicButton(currentBundle.getString(textOfTheSecondButton));
        secondButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        secondButton.setFont(Constants.MAIN_FONT);
        secondButtonPanel.add(secondButton);
        mainPanel.add(secondButton);
        amountOfPanels = SEVEN_PANELS;
        return mainPanel;
    }


    private void setConnectionListenerForFirstButton(JFrame mainWindow) {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = fieldForAddress.getText();
                String port = fieldForPort.getText();
                String answer = connectionHandler.connect(address, port);

                if (answer != null) {
                    printError(currentBundle.getString(answer), mainWindow);
                } else {
                    connectionAndLoginScreenController.moveToLogin(mainWindow, currentBundle, connectionHandler);
                }

            }
        });
    }


    private void setLoginListenerForFirstButton(JFrame mainWindow) {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = fieldForAddress.getText();
                String port = fieldForPort.getText();
                String answer = connectionHandler.login(address, port);

                if (answer != null) {
                    printError(currentBundle.getString(answer), mainWindow);
                } else {
                    connectionAndLoginScreenController.moveToMainScreen(connectionHandler.getConnectionManager(), mainWindow, currentBundle);
                }

            }
        });
    }

    private void setRegisterListenerForTheSecondButton(JFrame mainWindow) {
        secondButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = fieldForAddress.getText();
                String port = fieldForPort.getText();
                String answer = connectionHandler.register(address, port);

                if (answer != null) {
                    printError(currentBundle.getString(answer), mainWindow);
                } else {
                   connectionAndLoginScreenController.moveToMainScreen(connectionHandler.getConnectionManager(), mainWindow, currentBundle);
                }
            }
        });
    }


    public JPanel createConnectionFrame(JFrame mainFrame) {
        JPanel mainPanel = createPanelWithSevenLayers("CONNECTION", "REMOTE HOST ADDRESS:", "REMOTE HOST PORT:", "SUBMIT", mainFrame);
        setConnectionListenerForFirstButton(mainFrame);
        listToChooseLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                currentBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                ConnectionAndLoginScreenFabric connectionAndLoginScreenFabric = new ConnectionAndLoginScreenFabric(currentBundle);
                JPanel mainPanel = connectionAndLoginScreenFabric.createConnectionFrame(mainFrame);
                mainFrame.getContentPane().removeAll();
                mainFrame.setContentPane(mainPanel);
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        return mainPanel;
    }

    public JPanel createLoginScreen(JFrame mainFrame) {
        JPanel mainPanel = createFrameWithEightLayers("Login", "LOGIN", "PASSWORD", "LOGIN", "REGISTER", mainFrame);
        setLoginListenerForFirstButton(mainFrame);
        setRegisterListenerForTheSecondButton(mainFrame);
        listToChooseLanguage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentBundle = Constants.getBundleFromLanguageName(listToChooseLanguage.getSelectedItem().toString());
                ConnectionAndLoginScreenFabric connectionAndLoginScreenFabric = new ConnectionAndLoginScreenFabric(currentBundle);
                JPanel mainPanel = connectionAndLoginScreenFabric.createLoginScreen(mainFrame);
                mainFrame.getContentPane().removeAll();
                mainFrame.setContentPane(mainPanel);
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        return mainPanel;
    }


}
