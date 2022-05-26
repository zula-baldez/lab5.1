package zula.gui;

import zula.client.ConnectionManager;
import zula.common.data.Coordinates;
import zula.common.data.Dragon;
import zula.common.data.DragonCave;
import zula.common.data.DragonType;
import zula.common.data.DragonValidator;
import zula.common.exceptions.WrongArgumentException;
import zula.common.util.ArgumentParser;
import zula.util.Constants;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class AddPanel {
    private static final int AMOUNT_OF_PARTS = 10;
    private static final int PARTS_TO_CENTER = 6;
    private static final int PARTS_OF_SOUTH = 3;
    private static final int AMOUNT_OF_COLS = 3;
    private static final int AMOUNT_OF_ROWS = 10;
    private final JFrame mainFrame;
    private final ConnectionManager connectionManager;
    private final JPanel northPanel = new JPanel();
    private final JPanel centralPanel = new JPanel();
    private final JPanel southPanel = new JPanel();
    private final JPanel errorPanel = new JPanel();
    private final JButton submitButton = BasicGUIElementsFabric.createBasicButton("SUBMIT");


    private final ArgumentParser argumentParser = new ArgumentParser();


    private final JLabel fieldText = BasicGUIElementsFabric.createBasicLabel("FIELD");
    private final JLabel nameText = BasicGUIElementsFabric.createBasicLabel("name");
    private final JLabel xText = BasicGUIElementsFabric.createBasicLabel("x");
    private final JLabel yText = BasicGUIElementsFabric.createBasicLabel("y");
    private final JLabel ageText = BasicGUIElementsFabric.createBasicLabel("age");
    private final JLabel wingspanText = BasicGUIElementsFabric.createBasicLabel("wingspan");
    private final JLabel colorText = BasicGUIElementsFabric.createBasicLabel("color");
    private final JLabel typeText = BasicGUIElementsFabric.createBasicLabel("type");
    private final JLabel depthText = BasicGUIElementsFabric.createBasicLabel("depth");
    private final JLabel numberOfTreasuresText = BasicGUIElementsFabric.createBasicLabel("num. of tres.");
    private final JLabel valueText = BasicGUIElementsFabric.createBasicLabel("value");
    private final JLabel requirementText = BasicGUIElementsFabric.createBasicLabel("REQUIREMENT");
    private final JLabel nameReq = BasicGUIElementsFabric.createBasicLabel("NOT NULL");
    private final JLabel xReq = BasicGUIElementsFabric.createBasicLabel(">=-23, double");
    private final JLabel yReq = BasicGUIElementsFabric.createBasicLabel("<=160, integer");
    private final JLabel ageReq = BasicGUIElementsFabric.createBasicLabel("long, >=0");
    private final JLabel wingspanReq = BasicGUIElementsFabric.createBasicLabel("float, >=0");
    private final JLabel colorReq = BasicGUIElementsFabric.createBasicLabel("may be null");
    private final JLabel typeReq = BasicGUIElementsFabric.createBasicLabel("NOT NULL");
    private final JLabel depthReq = BasicGUIElementsFabric.createBasicLabel("Float, may be null");
    private final JLabel numberOfTreasuresReq = BasicGUIElementsFabric.createBasicLabel("Double, may be null");
    private final String[] colors = Constants.colors;
    private final String[] types = Constants.types;
    private final JTextField nameField = BasicGUIElementsFabric.createBasicJTextField();
    private final JTextField xField = BasicGUIElementsFabric.createBasicJTextField();
    private final JTextField yField = BasicGUIElementsFabric.createBasicJTextField();
    private final JTextField ageField = BasicGUIElementsFabric.createBasicJTextField();
    private final JTextField wingspanField = BasicGUIElementsFabric.createBasicJTextField();
    private final JComboBox<String> colorField = BasicGUIElementsFabric.createBasicComboBox(colors);
    private final JComboBox<String> typeField = BasicGUIElementsFabric.createBasicComboBox(types);
    private final JTextField depthField = BasicGUIElementsFabric.createBasicJTextField();
    private final JTextField numberOfTreasuresField = BasicGUIElementsFabric.createBasicJTextField();
    private final JComboBox<String> languages = new JComboBox<>(Constants.languages);
    private final ResourceBundle currentBundle;
    private final CommandExecutor commandExecutor;


    public AddPanel(ConnectionManager connectionManager, ResourceBundle resourceBundle) {
        mainFrame = new JFrame();
        this.currentBundle = resourceBundle;
        this.connectionManager = connectionManager;
        commandExecutor = new CommandExecutor(connectionManager, mainFrame);


    }

    private void setBorders() {

        fieldText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        valueText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        requirementText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        nameText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        nameField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        nameReq.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        xText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        xField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        xReq.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        yText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        yField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        yReq.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        ageText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        ageField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        ageReq.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        wingspanText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        wingspanField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        wingspanReq.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        colorText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        colorField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        colorReq.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        typeText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        typeField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        typeReq.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        depthText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        depthField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        depthReq.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        numberOfTreasuresText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        numberOfTreasuresField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        numberOfTreasuresReq.setBorder(BorderFactory.createLineBorder(Color.black, 1));

    }
    private void initElemets() {
        mainFrame.setSize(new Dimension(Constants.screenWidth, Constants.screenHeight));
        mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        mainFrame.setVisible(false);
        mainFrame.setTitle("Add window");

        northPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight / AMOUNT_OF_PARTS));
        northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        northPanel.add(languages);

        centralPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight * PARTS_TO_CENTER / AMOUNT_OF_PARTS));
        centralPanel.setLayout(new GridLayout(AMOUNT_OF_ROWS, AMOUNT_OF_COLS));

        southPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight * PARTS_OF_SOUTH / AMOUNT_OF_PARTS * 2));
        southPanel.setLayout(new GridBagLayout());

        errorPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight * PARTS_OF_SOUTH / AMOUNT_OF_PARTS * 2));
        errorPanel.setLayout(new GridBagLayout());

        setBorders();

        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        setListenerForSubmitButton();

        languages.setFont(Constants.mainFont);
        languages.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    protected Dragon parseDragonFromData() throws WrongArgumentException {
        DragonValidator dragonValidator = new DragonValidator();
        String name = argumentParser.parseArgFromString(nameField.getText(), dragonValidator::nameValidator, (String s) -> s);
        Double x = argumentParser.parseArgFromString(xField.getText(), dragonValidator::xValidator, Double::parseDouble);
        Integer y = argumentParser.parseArgFromString(yField.getText(), dragonValidator::yValidator, Integer::parseInt);
        Coordinates coordinates = new Coordinates(x, y);
        Long age = argumentParser.parseArgFromString(ageField.getText(), dragonValidator::ageValidator, Long::parseLong);
        Float wingspan = argumentParser.parseArgFromString(wingspanField.getText(), dragonValidator::wingspanValidator, Float::parseFloat);
        zula.common.data.Color color = argumentParser.parseArgFromString(colorField.getSelectedItem().toString(), dragonValidator::colorValidator, zula.common.data.Color::valueOf);
        DragonType type = argumentParser.parseArgFromString(typeField.getSelectedItem().toString(), dragonValidator::typeValidator, DragonType::valueOf);
        Float depth = argumentParser.parseArgFromString(depthField.getText(), dragonValidator::depthValidator, Float::parseFloat);
        Double numberOfTreasures = argumentParser.parseArgFromString(numberOfTreasuresField.getText(), dragonValidator::numberOfTreasuresValidator, Double::parseDouble);
        DragonCave dragonCave = new DragonCave(depth, numberOfTreasures);
        return new Dragon(name, coordinates, age, wingspan, color, type, dragonCave);

    }

    protected void errorHandler(String message) {
        errorPanel.removeAll();
        JLabel errorLabel = new JLabel("CHECK THE CURRENCY OF THE DATA");
        errorLabel.setFont(Constants.mainFont);
        errorPanel.add(errorLabel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    protected void setListenerForSubmitButton() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Dragon dragon = parseDragonFromData();
                    commandExecutor.addCommand(dragon);
                    mainFrame.dispose();

                } catch (WrongArgumentException wrongArgumentException) {
                    errorHandler("CHECK THE CURRENCY OF THE DATA");
                }
            }
        });
    }
    private void setElements(JPanel mainPanel) {
        mainPanel.add(northPanel);
        mainPanel.add(centralPanel);
        mainPanel.add(southPanel);
        mainPanel.add(errorPanel);
        centralPanel.add(fieldText);
        centralPanel.add(valueText);
        centralPanel.add(requirementText);
        centralPanel.add(nameText);
        centralPanel.add(nameField);
        centralPanel.add(nameReq);
        centralPanel.add(xText);
        centralPanel.add(xField);
        centralPanel.add(xReq);
        centralPanel.add(yText);
        centralPanel.add(yField);
        centralPanel.add(yReq);
        centralPanel.add(ageText);
        centralPanel.add(ageField);
        centralPanel.add(ageReq);
        centralPanel.add(wingspanText);
        centralPanel.add(wingspanField);
        centralPanel.add(wingspanReq);
        centralPanel.add(colorText);
        centralPanel.add(colorField);
        centralPanel.add(colorReq);
        centralPanel.add(typeText);
        centralPanel.add(typeField);
        centralPanel.add(typeReq);
        centralPanel.add(depthText);
        centralPanel.add(depthField);
        centralPanel.add(depthReq);
        centralPanel.add(numberOfTreasuresText);
        centralPanel.add(numberOfTreasuresField);
        centralPanel.add(numberOfTreasuresReq);
    }
    public void drawPanel() {
        initElemets();
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setElements(mainPanel);
        mainFrame.setContentPane(mainPanel);
        southPanel.add(submitButton);
        mainFrame.setVisible(true);
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public ResourceBundle getCurrentBundle() {
        return currentBundle;
    }

    public void initTextFields(Dragon dragon) {
        nameField.setText(dragon.getName());
        xField.setText(Double.toString(dragon.getCoordinates().getX()));
        yField.setText(Integer.toString(dragon.getCoordinates().getY()));
        ageField.setText(Long.toString(dragon.getAge()));
        wingspanField.setText(Float.toString(dragon.getWingspan()));
        colorField.setSelectedItem(dragon.getColor().toString());
        typeField.setSelectedItem(dragon.getType().toString());
        depthField.setText(Float.toString(dragon.getCave().getDepth()));
        numberOfTreasuresField.setText(Double.toString(dragon.getCave().getNumberOfTreasures()));
    }

    public void addDeleteButton(int id) {
        JButton deleteButton =  BasicGUIElementsFabric.createBasicButton("Delete");
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandExecutor.removeByIdCommand(id);
                mainFrame.dispose();
            }
        });
    }
}
