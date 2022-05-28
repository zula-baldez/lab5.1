package zula.gui.views;

import zula.client.ConnectionManager;
import zula.gui.controllers.MainScreenController;
import zula.util.BasicGUIElementsFabric;
import zula.util.CommandExecutor;
import zula.util.Constants;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;


public class MainScreen {
    private static final int SCREEN_HEIGHT = Constants.SCREEN_HEIGHT;
    private static final int SCREEN_WIDTH = Constants.SCREEN_WIDTH;
    private static final int BUTTON_WIDTH = SCREEN_WIDTH / 5;
    private static final int BUTTON_HEIGHT = SCREEN_HEIGHT / 20;
    private static final int NORTH_PANEL_HEIGHT = SCREEN_HEIGHT / 7;
    private static final int CENTER_PANEL_HEIGHT = SCREEN_HEIGHT * 4 / 7;
    private static final int SOUTH_PANEL_HEIGHT = SCREEN_HEIGHT * 2 / 7;
    private static final int LEFT_OF_CENTER_SIZE = SCREEN_WIDTH / 5;
    private static final int RIGHT_OF_CENTER_SIZE = SCREEN_WIDTH * 4 / 5;
    private static final int ROW_HEIGHT = SCREEN_HEIGHT / 21;
    private static final int ARGUMENT_WIDTH = SCREEN_WIDTH / 7;
    private static final int VGAP = 30;
    private static final int HGAP = 25;
    private final int currentVgap;
    private final int currentHgap;
    private final CommandExecutor commandExecutor;
    private final JFrame mainFrame;
    private JPanel northPanel;
    private JButton visualStyleButton;


    private JComboBox<String> languages = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);
    private JPanel centerPanel;
    private JPanel leftOfCenter;
    private JPanel rightOfCenter;
    private JPanel commandPanel;
    private JPanel argumentPanel;
    private JLabel commandText;
    private JPanel southPanel;
    private JTextField argumentTextField;
    private JPanel northOfCommandPanel;
    private JTextArea textResult;
    private JPanel southOfCommandPanel;
    private JTable jTable;
    private JLabel argumentPanelText;
    private JButton submitButton;
    private JPanel northOfArgumentPanel;
    private JPanel centerOfArgumentPanel;
    private JPanel southOfArgumentPanel;
    private ResourceBundle currentBundle;
    private final JFileChooser executeScriptFileChooser = new JFileChooser();
    private String[] tableHeader;
    private String[] commandNames;
    private JComboBox<String> commands;
    private JButton sortBy;
    private JButton filterButton;
    private String[][] tableElements = {{}};
    private final MainScreenController mainScreenController = new MainScreenController();

    public MainScreen(ConnectionManager connectionManager, JFrame mainFrame, ResourceBundle resourceBundle) {
        this.currentBundle = resourceBundle;
        this.commandExecutor = new CommandExecutor(connectionManager, mainFrame);
        mainFrame.getContentPane().removeAll();
        this.mainFrame = mainFrame;
        currentHgap = mainFrame.getSize().width / HGAP;
        currentVgap = mainFrame.getSize().height / VGAP;
    }

    public void setTableValue(String[][] tableValue) {
        tableElements = tableValue;
    }

    public void initElements() {
        commandNames = new String[]{currentBundle.getString("help"), currentBundle.getString("info"), currentBundle.getString("show"), currentBundle.getString("update_id"), currentBundle.getString("remove_by_id"), currentBundle.getString("clear"), currentBundle.getString("execute_script"), currentBundle.getString("exit"), currentBundle.getString("remove_last"), currentBundle.getString("remove_lower"), currentBundle.getString("reorder"), currentBundle.getString("average_of_wingspan"), currentBundle.getString("print_ascending"), currentBundle.getString("print_field_ascending_wingspan"), currentBundle.getString("add")};
        tableHeader = new String[]{currentBundle.getString("id"), currentBundle.getString("name"), currentBundle.getString("x"), currentBundle.getString("y"), currentBundle.getString("creationDate"), currentBundle.getString("age"), currentBundle.getString("wingspan"), currentBundle.getString("color"), currentBundle.getString("type"), currentBundle.getString("depth"), currentBundle.getString("Number ot Treasures"), currentBundle.getString("owner_id")};
        sortBy = BasicGUIElementsFabric.createBasicButton(currentBundle.getString("Sort!"));
        filterButton = BasicGUIElementsFabric.createBasicButton(currentBundle.getString("Filter!"));
        northPanel = new JPanel();
        visualStyleButton = BasicGUIElementsFabric.createBasicButton(currentBundle.getString("Visual style"));
        centerPanel = new JPanel();
        leftOfCenter = new JPanel();
        rightOfCenter = new JPanel();
        commandPanel = new JPanel();
        argumentPanel = new JPanel();
        commandText = new JLabel();
        argumentTextField = new JTextField();
        northOfCommandPanel = new JPanel();
        textResult = new JTextArea();
        southOfCommandPanel = new JPanel();
        argumentPanelText = new JLabel();
        submitButton = new JButton();
        commands = BasicGUIElementsFabric.createBasicComboBox(commandNames);
        southPanel = new JPanel();
        northOfArgumentPanel = new JPanel();
        centerOfArgumentPanel = new JPanel();
        southOfArgumentPanel = new JPanel();
        submitButton = BasicGUIElementsFabric.createBasicButton(currentBundle.getString("SUBMIT"));
        languages = BasicGUIElementsFabric.createBasicComboBox(Constants.LANGUAGES);

    }

    private void setSettingsForSortPanel() {
        sortBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainScreenController.createSortFrame(mainFrame, commandExecutor, currentBundle);
            }
        });
    }

    private void setSettingsForFilterPanel() {
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainScreenController.createFilterFrame(mainFrame, commandExecutor, currentBundle);
            }
        });
    }


    private void reprintArgumentPanel(JFileChooser jFileChooser) {
        argumentPanel.removeAll();
        argumentPanel.add(jFileChooser);
        jFileChooser.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void setListenerForVisualStyleButton() {
        visualStyleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               mainScreenController.createVisualStyleFrame(mainFrame, commandExecutor, currentBundle);
            }
        });
    }

    private void setListenerForFileChooser() {
        executeScriptFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainScreenController.executeScript(executeScriptFileChooser, commandExecutor, textResult, currentBundle);
            }
        });
    }

    private void setListenerForCommandsList() {
        commands.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = commands.getSelectedItem().toString();
                if (currentBundle.getString("help").equals(command) || currentBundle.getString("info").equals(command) || currentBundle.getString("show").equals(command) || currentBundle.getString("clear").equals(command) || currentBundle.getString("exit").equals(command) || currentBundle.getString("remove_last").equals(command)
                        || currentBundle.getString("reorder").equals(command) || currentBundle.getString("average_of_wingspan").equals(command) || currentBundle.getString("print_ascending").equals(command) || currentBundle.getString("print_field_ascending_wingspan").equals(command) || currentBundle.getString("add").equals(command)) {
                    setSettingForArgumentPanel(currentBundle.getString("No arg needed"), false);
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
                if (currentBundle.getString("update_id").equals(command) || currentBundle.getString("remove_by_id").equals(command) || currentBundle.getString("remove_lower").equals(command)) {
                    setSettingForArgumentPanel(currentBundle.getString("ID(int) needed"), true);
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
                checkExecuteScript(command);

            }
        });
    }

    private void checkExecuteScript(String command) {
        if (currentBundle.getString("execute_script").equals(command)) {
            executeScriptFileChooser.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            reprintArgumentPanel(executeScriptFileChooser);
        }
    } //не влез из-за чекстайла

    private void setSettingsForMainFrame() {

        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
    }

    private void setSettingsForNorthPanel() {
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, currentHgap, currentVgap));

        northPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, NORTH_PANEL_HEIGHT));
        visualStyleButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        sortBy.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        filterButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        northPanel.add(visualStyleButton);
        northPanel.add(sortBy);
        northPanel.add(filterButton);
        northPanel.add(languages);

    }

    private void divideCentralPanelIntoSeveralParts() {
        centerPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, CENTER_PANEL_HEIGHT));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        leftOfCenter = new JPanel();
        rightOfCenter = new JPanel();
        centerPanel.add(leftOfCenter);
        centerPanel.add(rightOfCenter);
        leftOfCenter.setPreferredSize(new Dimension(LEFT_OF_CENTER_SIZE, SCREEN_HEIGHT));
        rightOfCenter.setPreferredSize(new Dimension(RIGHT_OF_CENTER_SIZE, SCREEN_HEIGHT));
        rightOfCenter.setLayout(new BorderLayout());
        leftOfCenter.setLayout(new BoxLayout(leftOfCenter, BoxLayout.Y_AXIS));
        commandPanel = new JPanel();
        argumentPanel = new JPanel();
        leftOfCenter.add(commandPanel);
        leftOfCenter.add(argumentPanel);
        commandPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        argumentPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT * 2));
        argumentPanel.setLayout(new BoxLayout(argumentPanel, BoxLayout.Y_AXIS));
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
    }

    private void setSettingForCommandPanel() {
        commandText = BasicGUIElementsFabric.createBasicLabel(currentBundle.getString("COMMAND"));
        commandText.setAlignmentX(Component.CENTER_ALIGNMENT);
        commandText.setAlignmentY(Component.CENTER_ALIGNMENT);
        commands.setFont(Constants.SUB_FONT);
        setListenerForCommandsList();


        northOfCommandPanel.setLayout(new GridBagLayout());
        northOfCommandPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        southOfCommandPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        commandPanel.add(northOfCommandPanel);
        commandPanel.add(southOfCommandPanel);
        commandPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        northOfCommandPanel.add(commandText);
        southOfCommandPanel.add(commands);


    }

    private void setSettingForArgumentPanel(String text, boolean isArgumentsNeeded) {
        argumentPanel.removeAll();
        centerOfArgumentPanel.setLayout(new GridBagLayout());
        centerOfArgumentPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        southOfArgumentPanel.setLayout(new GridBagLayout());
        argumentTextField.setText(text);
        argumentTextField.setEnabled(isArgumentsNeeded);
        argumentPanelText.setText(currentBundle.getString("ARGUMENT"));
        argumentPanelText.setFont(Constants.MAIN_FONT);
        argumentPanelText.setAlignmentX(Component.CENTER_ALIGNMENT);
        argumentPanelText.setAlignmentY(Component.CENTER_ALIGNMENT);
        northOfArgumentPanel.add(argumentPanelText);
        argumentTextField.setPreferredSize(new Dimension(ARGUMENT_WIDTH, BUTTON_HEIGHT));
        argumentTextField.setFont(Constants.SUB_FONT);
        argumentTextField.setAlignmentY(Component.CENTER_ALIGNMENT);
        argumentTextField.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerOfArgumentPanel.add(argumentTextField);
        submitButton.setFont(Constants.MAIN_FONT);
        submitButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        southOfArgumentPanel.add(submitButton);
        argumentPanel.add(northOfArgumentPanel);
        argumentPanel.add(centerOfArgumentPanel);
        argumentPanel.add(southOfArgumentPanel);
        argumentPanel.setBorder(new LineBorder(Color.BLACK, 1));
    }

    private void setListenerForSubmitButton() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (commands.getSelectedItem().toString().equals(currentBundle.getString("info"))) {
                    textResult.setText(commandExecutor.infoCommand(currentBundle));
                }
                if (commands.getSelectedItem().toString().equals(currentBundle.getString("show"))) {
                    setSettingsForTable(true);
                    textResult.setText(currentBundle.getString("Table is correct"));
                }
                if (commands.getSelectedItem().toString().equals(currentBundle.getString("print_ascending"))) {

                    textResult.setText(commandExecutor.printAscendingCommand(currentBundle));
                }
                if (commands.getSelectedItem().toString().equals(currentBundle.getString("print_field_ascending_wingspan"))) {
                    textResult.setText(commandExecutor.printFieldAscendingWingspanCommand(currentBundle));
                }
                if (commands.getSelectedItem().toString().equals(currentBundle.getString("average_of_wingspan"))) {
                    textResult.setText(commandExecutor.averageOfWingspan(currentBundle));
                }
                if (commands.getSelectedItem().toString().equals(currentBundle.getString("remove_last"))) {
                    textResult.setText(commandExecutor.removeLastCommand(currentBundle));
                }
                if (currentBundle.getString("clear").equals(commands.getSelectedItem().toString())) {
                    textResult.setText(commandExecutor.clearCommand(currentBundle));
                }
                if (currentBundle.getString("reorder").equals(commands.getSelectedItem().toString())) {
                    textResult.setText(commandExecutor.reorder(currentBundle));
                }
                checkOthersCommands();
            }
        });
    }

    private void checkOthersCommands() { //to pass checkstyle
        if (commands.getSelectedItem().toString().equals(currentBundle.getString("help"))) {
            textResult.setText(commandExecutor.helpCommand(currentBundle));
        }
        if (commands.getSelectedItem().toString().equals(currentBundle.getString("remove_by_id"))) {
            try {
                int id = Integer.parseInt(argumentTextField.getText());
                textResult.setText(commandExecutor.removeByIdCommand(id, currentBundle));
            } catch (NumberFormatException ee) {
                textResult.setText(currentBundle.getString("Id must be a number!"));
            }
        }
        if (commands.getSelectedItem().toString().equals(currentBundle.getString("exit"))) {
            textResult.setText(commandExecutor.exitCommand(currentBundle));
        }
        if (currentBundle.getString("remove_lower").equals(commands.getSelectedItem().toString())) {
            try {
                int id = Integer.parseInt(argumentTextField.getText());
                textResult.setText(commandExecutor.removeLowerCommand(id, currentBundle));
            } catch (NumberFormatException ee) {
                textResult.setText(currentBundle.getString("Id must be a number!"));
            }
        }
        mainScreenController.updateAndAdd(currentBundle, commands, argumentTextField, commandExecutor, textResult);

    }

    private void setSettingsForTable(boolean isNeedsToInitTable) {
        if (isNeedsToInitTable) {
            jTable = new JTable(commandExecutor.showCommand(currentBundle.getLocale()), tableHeader);
        } else {
            jTable = new JTable(tableElements, tableHeader);
        }
        jTable.setVisible(true);
        jTable.setPreferredSize(new Dimension(RIGHT_OF_CENTER_SIZE, SCREEN_HEIGHT));
        jTable.setFont(Constants.SUB_FONT);
        jTable.setRowHeight(ROW_HEIGHT);
        jTable.getTableHeader().setFont(Constants.SUB_FONT);
        rightOfCenter.removeAll();
        rightOfCenter.add(new JScrollPane(jTable));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void setSettingForLanguagesList() {
        languages.setSelectedItem(Constants.getNameByBundle(currentBundle));
        languages.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                currentBundle = Constants.getBundleFromLanguageName(languages.getSelectedItem().toString());
                mainFrame.getContentPane().removeAll();
                startMain(true);

            }
        });
        languages.setFont(Constants.MAIN_FONT);
        languages.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

    }

    public void startMain(boolean isNeedsToInitTableValue) {
        initElements();
        setSettingsForMainFrame();
        setSettingsForNorthPanel();
        divideCentralPanelIntoSeveralParts();
        setSettingForCommandPanel();
        setSettingForArgumentPanel("", false);
        setListenerForSubmitButton();
        setListenerForVisualStyleButton();
        setListenerForFileChooser();
        setSettingForLanguagesList();
        setSettingsForSortPanel();
        setSettingsForFilterPanel();
        southPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SOUTH_PANEL_HEIGHT));
        mainFrame.add(northPanel);
        mainFrame.add(centerPanel);
        mainFrame.add(southPanel);

        setSettingsForTable(isNeedsToInitTableValue);
        southPanel.setLayout(new GridLayout());
        textResult = new JTextArea();
        textResult.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        textResult.setLineWrap(true);
        textResult.setFont(Constants.SUB_FONT);
        textResult.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        JScrollPane scrollText = new JScrollPane(textResult);
        southPanel.add(scrollText);
        mainFrame.setVisible(true);
    }
}
