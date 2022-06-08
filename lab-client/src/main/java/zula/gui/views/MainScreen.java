package zula.gui.views;

import zula.client.ConnectionManager;
import zula.common.data.Dragon;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.gui.controllers.MainScreenController;
import zula.util.BasicGUIElementsFabric;
import zula.util.CommandExecutor;
import zula.util.Constants;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
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
    private static final int AMOUNT_OF_COLS = 12;
    private final MainScreen mainScreen;

    private Integer currentVgap;
    private Integer currentHgap;
    private final int northButtonWidth = 5;
    private final int northButtonHeight = 20;
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
    private JDialog executer;
    public MainScreen(ConnectionManager connectionManager, JFrame mainFrame, ResourceBundle resourceBundle) {
        this.currentBundle = resourceBundle;
        this.commandExecutor = new CommandExecutor(connectionManager, mainFrame);
        mainFrame.getContentPane().removeAll();
        this.mainFrame = mainFrame;
        currentHgap = mainFrame.getSize().width / HGAP;
        currentVgap = mainFrame.getSize().height / VGAP;
        mainScreen = this;
        mainFrame.setTitle("Main");
        executer = executeDialog();
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
    private void setSettingsResized() {
        currentHgap = mainFrame.getSize().width / HGAP;
        currentVgap = mainFrame.getSize().height / VGAP;
        sortBy.setPreferredSize(new Dimension(mainFrame.getWidth() / northButtonWidth, mainFrame.getHeight() / northButtonHeight));
        visualStyleButton.setPreferredSize(new Dimension(mainFrame.getWidth() / northButtonWidth, mainFrame.getHeight() / northButtonHeight));
        filterButton.setPreferredSize(new Dimension(mainFrame.getWidth() / northButtonWidth, mainFrame.getHeight() / northButtonHeight));
        languages.setPreferredSize(new Dimension(mainFrame.getWidth() / northButtonWidth, mainFrame.getHeight() / northButtonHeight));
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, currentHgap, currentVgap));
        northPanel.removeAll();
        northPanel.add(visualStyleButton);
        northPanel.add(sortBy);
        northPanel.add(filterButton);
        northPanel.add(languages);

    }
    private void setSettingsForSortPanel() {
        sortBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainScreenController.createSortFrame(mainFrame, commandExecutor, currentBundle);
            }
        });
        mainFrame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                setSettingsResized();
            }
        });
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setSettingsResized();

                super.componentResized(e);


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

    private synchronized void setListenerForFileChooser() {
        executeScriptFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                executer.setVisible(true);

                mainFrame.revalidate();
                mainFrame.repaint();
                mainScreenController.executeScript(executeScriptFileChooser, commandExecutor, textResult, currentBundle);

                setSettingsForTable(true);
                executer.setVisible(false);

            }
        });
    }
    private JDialog executeDialog() {
        JDialog jDialog = new JDialog();
        jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jDialog.setLocationRelativeTo(null);
        jDialog.setTitle("Script is completing...");
        jDialog.add(BasicGUIElementsFabric.createBasicLabel("Script is completing..."));
        jDialog.pack();
        jDialog.revalidate();
        jDialog.repaint();
        return jDialog;
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
                    setSettingsForTable(true);
                }
                if (commands.getSelectedItem().toString().equals(currentBundle.getString("show"))) {
                    textResult.setText(currentBundle.getString("Table is correct"));
                    setSettingsForTable(true);
                }
                if (commands.getSelectedItem().toString().equals(currentBundle.getString("print_ascending"))) {
                    textResult.setText(commandExecutor.printAscendingCommand(currentBundle));
                    setSettingsForTable(true);
                }
                if (commands.getSelectedItem().toString().equals(currentBundle.getString("print_field_ascending_wingspan"))) {
                    textResult.setText(commandExecutor.printFieldAscendingWingspanCommand(currentBundle));
                    setSettingsForTable(true);
                }
                if (commands.getSelectedItem().toString().equals(currentBundle.getString("average_of_wingspan"))) {
                    textResult.setText(commandExecutor.averageOfWingspan(currentBundle));
                    setSettingsForTable(true);
                }
                if (commands.getSelectedItem().toString().equals(currentBundle.getString("remove_last"))) {
                    textResult.setText(commandExecutor.removeLastCommand(currentBundle));
                    setSettingsForTable(true);
                }
                if (currentBundle.getString("clear").equals(commands.getSelectedItem().toString())) {
                    textResult.setText(commandExecutor.clearCommand(currentBundle));
                    setSettingsForTable(true);
                }
                if (currentBundle.getString("reorder").equals(commands.getSelectedItem().toString())) {
                    textResult.setText(commandExecutor.reorder(currentBundle));
                    setSettingsForTable(true);
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
            setSettingsForTable(true);
        }
        if (commands.getSelectedItem().toString().equals(currentBundle.getString("exit"))) {
            textResult.setText(commandExecutor.exitCommand(currentBundle));
            setSettingsForTable(true);
        }
        if (currentBundle.getString("remove_lower").equals(commands.getSelectedItem().toString())) {
            try {
                int id = Integer.parseInt(argumentTextField.getText());
                textResult.setText(commandExecutor.removeLowerCommand(id, currentBundle));
                setSettingsForTable(true);
            } catch (NumberFormatException ee) {
                textResult.setText(currentBundle.getString("Id must be a number!"));
            }
        }
        mainScreenController.updateAndAdd(currentBundle, commands, argumentTextField, commandExecutor, textResult, this);

    }

    private void updateTable(int rowIndex) {
        int id = Integer.parseInt(tableElements[rowIndex][0]);
        ServerMessage serverMessage = commandExecutor.getDragonById(id, currentBundle);
        if (ResponseCode.OK == serverMessage.getResponseStatus()) {
            UpdateIdPanel changeFieldsOfDragonPanel = new UpdateIdPanel(commandExecutor.getConnectionManager(), currentBundle, ((Dragon) serverMessage.getArguments()[0]), mainScreen);
            changeFieldsOfDragonPanel.drawPanel();
        } else {
            textResult.setText(currentBundle.getString("Either this id does not exist, or you are not its creator:("));
        }
    }

    protected void setSettingsForTable(boolean isNeedsToInitTable) {
        if (isNeedsToInitTable) {
            tableElements = commandExecutor.showCommand(currentBundle.getLocale());
            if (tableElements == null) {
                textResult.setText(currentBundle.getString("SERVER UMER"));
                String[][] ta = new String[0][AMOUNT_OF_COLS];
                jTable = new JTable(ta, tableHeader);
            } else {
                jTable = new JTable(tableElements, tableHeader) {
                    @Override
                    public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                        updateTable(rowIndex);
                    }
                    @Override
                    public boolean isCellEditable(int i, int i1) {
                        return false;
                    }
                };

            }
        } else {
            jTable = new JTable(tableElements, tableHeader) {
                @Override
                public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                    updateTable(rowIndex);
                }
                @Override
                public boolean isCellEditable(int i, int i1) {
                    return false;
                }

            };
        }
       setSets();
    }
    private void setSets() {
        jTable.setVisible(true);
        jTable.setPreferredSize(new Dimension(RIGHT_OF_CENTER_SIZE, SCREEN_HEIGHT));
        jTable.setFont(Constants.SUB_FONT);
        jTable.getTableHeader().setFont(Constants.SUB_FONT);
        jTable.setRowHeight(ROW_HEIGHT);
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
