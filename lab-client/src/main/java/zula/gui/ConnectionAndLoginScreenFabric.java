package zula.gui;

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
    This class has methods to create frames of one type.
*/
public class ConnectionAndLoginScreenFabric {
    private final ConnectionLogics connectionLogics = new ConnectionLogics();
    private int amountOfPanels = 7; //can change
    private final int relativeWidthDisplacementForLanguagesList = Constants.screenWidth / 50;
    private final int relativeHeightDisplacementForLanguagesList = Constants.screenHeight / 25; //Константы, управляющие размерами объектов и зависящие от размера экрана
    private final int relativeWidthDisplacementForTextFields = Constants.screenWidth * 8 / 10;
    private final int relativeHeightDisplacementForTextFields = Constants.screenHeight / 20;
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
    private final JComboBox<String> listToChooseLanguage = new JComboBox<>(Constants.languages);
    private ResourceBundle currentBundle = Constants.ruBundle;

    public ConnectionAndLoginScreenFabric(ResourceBundle resourceBundle) {
        this.currentBundle = resourceBundle;
    }

    private void setPanelsSizeDueToAmountOfPanels() {
        languageListPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight / amountOfPanels));
        addressTextPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight / amountOfPanels));
        addressTextFieldPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight / amountOfPanels));
        portTextPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight / amountOfPanels));
        portTextFieldPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight / amountOfPanels));
        submitButtonPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight / amountOfPanels));
        errorFieldPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight / amountOfPanels));
        secondButtonPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight / amountOfPanels));
    }

    public JTextField createStandartTextField() {
        JTextField jTextField = new JTextField(SwingConstants.CENTER);
        jTextField.setFont(Constants.mainFont);
        jTextField.setPreferredSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jTextField.setMinimumSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jTextField.setMaximumSize(new Dimension(relativeWidthDisplacementForTextFields, relativeHeightDisplacementForTextFields));
        jTextField.setBorder(BorderFactory.createLineBorder(Constants.mainColor, 3));
        return jTextField;
    }

    private void printError(String error, JFrame mainFrame) {
        JLabel jLabel = new JLabel();


        jLabel.setText(error);
        jLabel.setFont(Constants.mainFont);
        jLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        errorFieldPanel.removeAll();
        errorFieldPanel.add(jLabel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }





    public JPanel createPanelWithSevenLayers(String nameOfTheFrame, String valueOfTheFirstJLabel, String valueOfTheSecondJLabel, String textOfTheFirstButton, JFrame mainWindow) {
        mainWindow.setTitle(nameOfTheFrame);
        setPanelsSizeDueToAmountOfPanels();
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(languageListPanel);
        mainPanel.add(addressTextPanel);
        mainPanel.add(addressTextFieldPanel);
        mainPanel.add(portTextPanel);
        mainPanel.add(portTextFieldPanel);
        mainPanel.add(submitButtonPanel);
        mainPanel.add(errorFieldPanel);
        listToChooseLanguage.setFont(Constants.mainFont);
        listToChooseLanguage.setSelectedItem(Constants.getNameByBundle(currentBundle));
        languageListPanel.add(listToChooseLanguage);
        //Host address text
        addressText = new JLabel(currentBundle.getString(valueOfTheFirstJLabel) , SwingConstants.CENTER);
        addressText.setFont(Constants.mainFont);
        addressTextPanel.add(addressText, BorderLayout.CENTER);
        //TextField for getting address
        fieldForAddress = createStandartTextField();
        addressTextFieldPanel.add(fieldForAddress);
        //host port text
        portText = new JLabel( currentBundle.getString(valueOfTheSecondJLabel), SwingConstants.CENTER);
        portText.setFont(Constants.mainFont);
        portTextPanel.add(portText, BorderLayout.CENTER);
        //Textfield for getting port
        fieldForPort = createStandartTextField();
        portTextFieldPanel.add(fieldForPort);
        //Submit button
        submitButton = new JButton(currentBundle.getString(textOfTheFirstButton));
        submitButton.setFont(Constants.mainFont);
        submitButtonPanel.add(submitButton);
        return mainPanel;
    }



    public JPanel createFrameWithEightLayers(String nameOfTheFrame, String valueOfTheFirstJLabel, String valueOfTheSecondJLabel, String textOfTheFirstButton, String textOfTheSecondButton, JFrame mainFrame) {
        amountOfPanels = 8;
        JPanel mainPanel = createPanelWithSevenLayers(nameOfTheFrame, valueOfTheFirstJLabel, valueOfTheSecondJLabel, textOfTheFirstButton, mainFrame);
        mainPanel.setMaximumSize(new Dimension(Constants.screenWidth, Constants.screenHeight    ));
        secondButton = new JButton(currentBundle.getString(textOfTheSecondButton));
        secondButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        secondButton.setFont(Constants.mainFont);
        secondButtonPanel.add(secondButton);
        mainPanel.add(secondButton);
        amountOfPanels = 7;
        return mainPanel;
    }


    private void setConnectionListenerForFirstButton(JFrame mainWindow) {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String address = fieldForAddress.getText();
                String port = fieldForPort.getText();
                String answer = connectionLogics.connect(address, port);

                if(answer != null) {
                    printError(answer, mainWindow);
                } else {
                    LoginScreen loginScreen = new LoginScreen();
                    loginScreen.startLogin(mainWindow, currentBundle);
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
                String answer = connectionLogics.login(address, port);

                if(answer != null) {
                    printError(answer, mainWindow);
                } else {
                    MainScreen mainScreen = new MainScreen(connectionLogics.getConnectionManager(), mainWindow, currentBundle);
                    mainScreen.startMain();
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
                String answer = connectionLogics.register(address, port);

                if(answer != null) {
                    printError(answer, mainWindow);
                } else {
                    MainScreen mainScreen = new MainScreen(connectionLogics.getConnectionManager(), mainWindow, currentBundle);
                    mainScreen.startMain();
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
                JPanel mainPanel =  connectionAndLoginScreenFabric.createConnectionFrame(mainFrame);
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
                JPanel mainPanel =  connectionAndLoginScreenFabric.createLoginScreen(mainFrame);
                mainFrame.getContentPane().removeAll();
                mainFrame.setContentPane(mainPanel);
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        });
        return mainPanel;
    }











}
