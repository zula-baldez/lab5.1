package zula.gui;

import zula.client.ConnectionManager;
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ResourceBundle;

//отрисовщик??
public class MainScreen {
    private final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final MainScreenLogics mainScreenLogics;
    private final ConnectionAndLoginScreenFabric connectionAndLoginScreenFabric = new ConnectionAndLoginScreenFabric(Constants.ruBundle);
    private JFrame mainFrame;
    private JPanel northPanel;
    private JButton visualStyleButton;
    private JComboBox<String> sortBy;
    private JComboBox<String> filter;
    private JComboBox<String> languages = new JComboBox<>(Constants.languages);
    private JPanel centerPanel;
    private JPanel leftOfCenter;
    private JPanel rightOfCenter;
    private JPanel commandPanel;
    private JPanel argumentPanel;
    private JLabel commandText;
    private JPanel southPanel;
    private JTextField argumentTextField;
    private JPanel northOfCommandPanel;
    private JPanel resultPanel;
    private JTextArea textResult;
    private JPanel southOfCommandPanel;
    private JTable jTable;
    private JLabel argumentPanelText;
    private JButton submitButton;
    private JPanel northOfArgumentPanel;
    private JPanel centerOfArgumentPanel;
    private JPanel southOfArgumentPanel;
    private ResourceBundle currentBundle;
    private JFileChooser executeScriptFileChooser = new JFileChooser();
    private final String[] tableHeader = {"id", "name", "x", "y", "creationDate", "age", "wingspan", "color", "type", "depth", "Number ot Treasures", "owner_id"};
    private final String[] commandNames = {"help", "info", "show", "update_id", "remove_by_id", "clear", "execute_script", "exit", "remove_last",
            "remove_lower", "reorder", "average_of_wingspan", "print_ascending", "print_field_ascending_wingspan", "add"};
    private JComboBox<String> commands;

    public MainScreen(ConnectionManager connectionManager, JFrame mainFrame, ResourceBundle resourceBundle) {
        this.currentBundle = resourceBundle;
        this.mainScreenLogics = new MainScreenLogics(connectionManager, mainFrame);
        mainFrame.getContentPane().removeAll();
        this.mainFrame = mainFrame;
    }

    public void initElements() {
        northPanel = new JPanel();
        visualStyleButton = new JButton();
        sortBy = new JComboBox<String>(tableHeader);
        filter = new JComboBox<>();
        centerPanel = new JPanel();
        leftOfCenter = new JPanel();
        rightOfCenter = new JPanel();
        commandPanel = new JPanel();
        argumentPanel = new JPanel();
        commandText = new JLabel();
        argumentTextField = new JTextField();
        northOfCommandPanel = new JPanel();
        resultPanel = new JPanel();
        textResult = new JTextArea();
        southOfCommandPanel = new JPanel();
        argumentPanelText = new JLabel();
        submitButton = new JButton();
        commands = new JComboBox<>(commandNames);
        southPanel = new JPanel();
        northOfArgumentPanel = new JPanel();
        centerOfArgumentPanel = new JPanel();
        southOfArgumentPanel = new JPanel();
        submitButton = new JButton(currentBundle.getString("SUBMIT"));
        languages = new JComboBox<>(Constants.languages);

    }


    private void reprintArgumentPanel(JFileChooser jFileChooser) {
        argumentPanel.removeAll();
        argumentPanel.add(jFileChooser);
        jFileChooser.setPreferredSize(new Dimension(screenWidth, screenHeight));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

/*    private void printArgumentPanel(JTextField argumentField) {
        argumentPanel.removeAll();
        argumentPanel.add(argumentPanelText);
        argumentPanelText.setPreferredSize(new Dimension(screenWidth, screenHeight));

        argumentPanel.add(argumentField);
        argumentField.setPreferredSize(new Dimension(screenWidth, screenHeight));

        argumentPanel.add(submitButton);
        submitButton.setPreferredSize(new Dimension(screenWidth, screenHeight));

        mainFrame.revalidate();
        mainFrame.repaint();
    }*/

    private void setListenerForVisualStyleButton() {
        visualStyleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisualStyleMain visualStyleMain = new VisualStyleMain(mainFrame, mainScreenLogics.connectionManager, currentBundle);
                visualStyleMain.printGraphics();
            }
        });
    }
    private void setListenerForFileChoose() {
        executeScriptFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = executeScriptFileChooser.getSelectedFile();
                mainScreenLogics.executeScript(textResult, file);
            }
        });
    }
    private void setListenerForCommandsList(JComboBox<String> commands) {
        commands.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = commands.getSelectedItem().toString();
                if (command.equals("help") || command.equals("info") || command.equals("show") || command.equals("clear") || command.equals("exit") || command.equals("remove_last") ||
                        command.equals("reorder") || command.equals("average_of_wingspan") || command.equals("print_ascending") || command.equals("print_field_ascending_wingspan") || command.equals("add")) {
                    setSettingForArgumentPanel("No arg needed", false);
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
                if (command.equals("update_id") || command.equals("remove_by_id") || command.equals("remove_lower")) {
                    setSettingForArgumentPanel("ID(int) needed", true);
                    mainFrame.revalidate();
                    mainFrame.repaint();
                }
                if (command.equals("execute_script")) {

                    executeScriptFileChooser.setPreferredSize(new Dimension(screenWidth / 7, screenHeight / 20));
                    reprintArgumentPanel(executeScriptFileChooser);
                }

            }
        });
    }

    private void setSettingsForMainFrame() {
        mainFrame.setSize(screenWidth, screenHeight);
/*
        mainFrame.setResizable(false);
*/ 
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
    }

    private void setSettingsForNorthPanel() {
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, screenWidth / 25, screenHeight / 30));
        northPanel.setPreferredSize(new Dimension(screenWidth, screenHeight / 7));
        visualStyleButton.setText(currentBundle.getString("Visual style"));
        visualStyleButton.setFont(Constants.mainFont);
        visualStyleButton.setPreferredSize(new Dimension(screenWidth / 5, screenHeight / 20));
        sortBy.setPreferredSize(new Dimension(screenWidth / 5, screenHeight / 20));
        filter.setPreferredSize(new Dimension(screenWidth / 5, screenHeight / 20));
        northPanel.add(visualStyleButton);
        northPanel.add(sortBy);
        northPanel.add(filter);
        northPanel.add(languages);

    }

    private void divideCentralPanelIntoSeveralParts() {
        centerPanel.setPreferredSize(new Dimension(screenWidth, screenHeight * 4 / 7));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        leftOfCenter = new JPanel();
        rightOfCenter = new JPanel();
        centerPanel.add(leftOfCenter);
        centerPanel.add(rightOfCenter);
        leftOfCenter.setPreferredSize(new Dimension(screenWidth / 5, screenHeight));
        rightOfCenter.setPreferredSize(new Dimension(screenWidth * 4 / 5, screenHeight));
        rightOfCenter.setLayout(new BorderLayout());
        leftOfCenter.setLayout(new BoxLayout(leftOfCenter, BoxLayout.Y_AXIS));
        commandPanel = new JPanel();
        argumentPanel = new JPanel();
        leftOfCenter.add(commandPanel);
        leftOfCenter.add(argumentPanel);
        commandPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        argumentPanel.setPreferredSize(new Dimension(screenWidth, screenHeight*2));
        argumentPanel.setLayout(new BoxLayout(argumentPanel, BoxLayout.Y_AXIS));
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
    }

    private void setSettingForCommandPanel() {
        commandText = new JLabel(currentBundle.getString("COMMAND"));
        commandText.setAlignmentX(Component.CENTER_ALIGNMENT);
        commandText.setAlignmentY(Component.CENTER_ALIGNMENT);
        commandText.setFont(Constants.mainFont);
        commands.setFont(Constants.subFont);
        setListenerForCommandsList(commands);


        northOfCommandPanel.setLayout(new GridBagLayout());
        northOfCommandPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        southOfCommandPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        commandPanel.add(northOfCommandPanel);
        commandPanel.add(southOfCommandPanel);
        commandPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        northOfCommandPanel.add(commandText);
        southOfCommandPanel.add(commands);


    }

    private void setSettingForArgumentPanel(String text, boolean isArgumentsNeeded) {
        argumentPanel.removeAll();
        centerOfArgumentPanel.setLayout(new GridBagLayout());
        centerOfArgumentPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        southOfArgumentPanel.setLayout(new GridBagLayout());
        argumentTextField.setText(text);
        argumentTextField.setEnabled(isArgumentsNeeded);
        argumentPanelText.setText(currentBundle.getString("ARGUMENT"));
        argumentPanelText.setFont(Constants.mainFont);
        argumentPanelText.setAlignmentX(Component.CENTER_ALIGNMENT);
        argumentPanelText.setAlignmentY(Component.CENTER_ALIGNMENT);
        northOfArgumentPanel.add(argumentPanelText);
        argumentTextField.setPreferredSize(new Dimension(screenWidth / 7, screenHeight / 20));
        argumentTextField.setFont(Constants.subFont);
        argumentTextField.setAlignmentY(Component.CENTER_ALIGNMENT);
        argumentTextField.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerOfArgumentPanel.add(argumentTextField);
        submitButton.setFont(Constants.mainFont);
        submitButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        southOfArgumentPanel.add(submitButton);
        argumentPanel.add(northOfArgumentPanel);
        argumentPanel.add(centerOfArgumentPanel);
        argumentPanel.add(southOfArgumentPanel);
    }

    private void setListenerForSubmitButton() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (commands.getSelectedItem().toString().equals("help")) {
                    textResult.setText(mainScreenLogics.helpCommand());
                }
                if (commands.getSelectedItem().toString().equals("info")) {
                    textResult.setText(mainScreenLogics.infoCommand());
                }
                if (commands.getSelectedItem().toString().equals("show")) {
                    setSettingsForTable();
                    textResult.setText("На таблице отображается корректный результат");
                }
                if (commands.getSelectedItem().toString().equals("print_ascending")) {

                    textResult.setText(mainScreenLogics.printAscendingCommand());
                }
                if (commands.getSelectedItem().toString().equals("print_field_ascending_wingspan")) {
                    textResult.setText(mainScreenLogics.printFieldAscendingWingspanCommand());
                }
                if (commands.getSelectedItem().toString().equals("average_of_wingspan")) {
                    textResult.setText(mainScreenLogics.averageOfWingspan());
                }
                if (commands.getSelectedItem().toString().equals("remove_last")) {
                    textResult.setText(mainScreenLogics.removeLastCommand());
                }
                if (commands.getSelectedItem().toString().equals("add")) {
                    AddPanel addPanel = new AddPanel(mainFrame, mainScreenLogics.connectionManager, currentBundle);
                    addPanel.drawAddPanel();

                }
                if (commands.getSelectedItem().toString().equals("remove_by_id")) {
                    try {
                        int id = Integer.parseInt(argumentTextField.getText());
                        textResult.setText(mainScreenLogics.removeByIdCommand(id));
                    } catch (NumberFormatException ee) {
                        System.out.println("TUT");
                        textResult.setText("Id must be a number!");
                    }
                }
                if (commands.getSelectedItem().toString().equals("exit")) {
                    textResult.setText(mainScreenLogics.exitCommand());


                }
                if("clear".equals(commands.getSelectedItem().toString())) {
                    textResult.setText(mainScreenLogics.clearCommand());
                }
                if("reorder".equals(commands.getSelectedItem().toString())) {
                    textResult.setText(mainScreenLogics.reorder());
                }

            }
        });
    }

    private void setSettingsForTable() {
        String[][] elements = mainScreenLogics.showCommand();
        jTable = new JTable(elements, tableHeader);
        jTable.setVisible(true);
        jTable.setPreferredSize(new Dimension(screenWidth * 4 / 5, screenHeight));
        jTable.setFont(Constants.subFont);
        jTable.setRowHeight(screenHeight/21);  //1080/50 //TODO
        jTable.getTableHeader().setFont(Constants.subFont);
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
                startMain();

            }
        });
        languages.setFont(Constants.mainFont);
        languages.setPreferredSize(new Dimension(screenWidth / 5, screenHeight / 20));

    }
    public void startMain() {
        initElements();
        //-------------------------
        setSettingsForMainFrame();
        //--------------------------
        setSettingsForNorthPanel();
        //--------------------------
        divideCentralPanelIntoSeveralParts();
        //--------------------------
        setSettingForCommandPanel();
        //--------------------------
        setSettingForArgumentPanel("", true);
        //--------------------------
        setListenerForSubmitButton();
        //--------------------------
        setListenerForVisualStyleButton();
        //--------------------------
        setListenerForFileChoose();

        setSettingForLanguagesList();


        southPanel.setPreferredSize(new Dimension(screenWidth, screenHeight * 2 / 7));
        mainFrame.add(northPanel);
        mainFrame.add(centerPanel);
        mainFrame.add(southPanel);

       /*TableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };*/

        setSettingsForTable();
        southPanel.setLayout(new GridLayout());
        textResult = new JTextArea();
        textResult.setPreferredSize(new Dimension(screenWidth, screenHeight));
        textResult.setLineWrap(true);
        textResult.setFont(Constants.subFont);
        textResult.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        JScrollPane scrollText = new JScrollPane(textResult);
        southPanel.add(scrollText);
        mainFrame.setVisible(true);
    }

}
