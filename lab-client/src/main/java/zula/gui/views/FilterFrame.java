package zula.gui.views;

import zula.client.ConnectionManager;
import zula.common.data.Dragon;
import zula.util.BasicGUIElementsFabric;
import zula.util.CommandExecutor;
import zula.util.Constants;
import zula.util.TableFilterer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

public class FilterFrame {
    private static final int PART_OF_SCREEN = 5; // 1 / 5
    private static final int AMOUNT_OF_COLUMNS = 15;
    private final JFrame mainFrame;
    private final ConnectionManager connectionManager;
    private final ResourceBundle currentBundle;

    private final JFrame subFrame = new JFrame();

    private JPanel mainPanel;
    private JComboBox<String> typeOfFilter;
    private JComboBox<String> columns;
    private JComboBox<String> typesComboBox = new JComboBox<>(Constants.TYPES);
    private JComboBox<String> colorsComboBox = new JComboBox<>(Constants.COLORS);
    private String[] types;
    private String[] columnsNames;
    private JButton filterButton;
    private JTextField fieldForValue = new JTextField(AMOUNT_OF_COLUMNS);
    public FilterFrame(JFrame mainFrame, ConnectionManager connectionManager, ResourceBundle currentBundle) {
        this.connectionManager = connectionManager;
        this.mainFrame = mainFrame;
        this.currentBundle = currentBundle;
            types = new String[]{currentBundle.getString("More than"), currentBundle.getString("Less than"), currentBundle.getString("Equals")};
        columnsNames = new String[]{currentBundle.getString("id"), currentBundle.getString("name"), currentBundle.getString("x"), currentBundle.getString("y"), currentBundle.getString("creationDate"), currentBundle.getString("age"), currentBundle.getString("wingspan"), currentBundle.getString("color"), currentBundle.getString("type"), currentBundle.getString("depth"), currentBundle.getString("Number ot Treasures")};
    }

    private void setFilterButtonListener() {
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeOfFiltering = (String) typeOfFilter.getSelectedItem();
                String columnToFilter = (String) columns.getSelectedItem();
                String value;
                if (currentBundle.getString("type").equals(columnToFilter)) {
                    value = typesComboBox.getSelectedItem().toString();
                } else if (currentBundle.getString("color").equals(columnToFilter)) {
                    value = colorsComboBox.getSelectedItem().toString();
                } else {
                    value = fieldForValue.getText();
                }

                CommandExecutor commandExecutor = new CommandExecutor(connectionManager, mainFrame);
                List<Dragon> dragons = commandExecutor.showWithoutParsingToMassive();
                String[][] result = TableFilterer.filterList(dragons, columnToFilter, value, typeOfFiltering, currentBundle);
                MainScreen mainScreen = new MainScreen(connectionManager, mainFrame, currentBundle);
                subFrame.dispose();
                mainScreen.setTableValue(result);
                mainScreen.startMain(false);
                mainFrame.setEnabled(true);
            }
        });
    }

    private void setFilterFieldListener() {
        columns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String field = columns.getSelectedItem().toString();
                if (currentBundle.getString("type").equals(field)) {
                    mainPanel.removeAll();
                    mainPanel.add(typeOfFilter);
                    mainPanel.add(columns);
                    mainPanel.add(typesComboBox);
                    mainPanel.add(filterButton);
                    subFrame.setContentPane(mainPanel);
                    subFrame.pack();
                    subFrame.revalidate();
                    subFrame.repaint();
                }
                if (currentBundle.getString("color").equals(field)) {
                    mainPanel.removeAll();
                    mainPanel.add(typeOfFilter);
                    mainPanel.add(columns);
                    mainPanel.add(colorsComboBox);
                    mainPanel.add(filterButton);
                    subFrame.setContentPane(mainPanel);
                    subFrame.pack();
                    subFrame.revalidate();
                    subFrame.repaint();
                }
            }
        });
    }

    public void drawFilterPanel() {
        subFrame.setSize(Constants.SCREEN_WIDTH / PART_OF_SCREEN, Constants.SCREEN_HEIGHT / PART_OF_SCREEN);
        subFrame.setVisible(true);
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        typeOfFilter = new JComboBox<>(types);
        columns = new JComboBox<>(columnsNames);
        filterButton = BasicGUIElementsFabric.createBasicButton(currentBundle.getString("Filter!"));
        typeOfFilter.setFont(Constants.MAIN_FONT);
        columns.setFont(Constants.MAIN_FONT);
        fieldForValue.setFont(Constants.MAIN_FONT);
        typesComboBox.setFont(Constants.MAIN_FONT);
        colorsComboBox.setFont(Constants.MAIN_FONT);
        subFrame.setLocationRelativeTo(null);
        mainPanel.add(typeOfFilter);
        mainPanel.add(columns);
        mainPanel.add(fieldForValue);
        mainPanel.add(filterButton);
        subFrame.add(mainPanel);
        subFrame.pack();
        setFilterButtonListener();
        setFilterFieldListener();

    }
}
