package zula.gui;

import zula.client.ConnectionManager;
import zula.common.data.Dragon;
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
    private JComboBox<String> typesComboBox = new JComboBox<>(Constants.types);
    private JComboBox<String> colorsComboBox = new JComboBox<>(Constants.colors);
    private String[] types = new String[]{"More than", "Less than", "Equals"};
    private final String[] columnsNames = {"id", "name", "x", "y", "creationDate", "age", "wingspan", "color", "type", "depth", "Number ot Treasures", "owner_id"};
    private JButton filterButton;
    private JTextField fieldForValue = new JTextField(AMOUNT_OF_COLUMNS);
    public FilterFrame(JFrame mainFrame, ConnectionManager connectionManager, ResourceBundle currentBundle) {
        this.connectionManager = connectionManager;
        this.mainFrame = mainFrame;
        this.currentBundle = currentBundle;
    }

    private void setFilterButtonListener() {
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeOfFiltering = (String) typeOfFilter.getSelectedItem(); //TODO null point
                String columnToFilter = (String) columns.getSelectedItem();
                String value;
                if ("type".equals(columnToFilter)) {
                    value = typesComboBox.getSelectedItem().toString();
                } else if ("type".equals(columnToFilter)) {
                    value = colorsComboBox.getSelectedItem().toString();
                } else {
                    value = fieldForValue.getText();
                }

                CommandExecutor commandExecutor = new CommandExecutor(connectionManager, mainFrame);
                List<Dragon> dragons = commandExecutor.showWithoutParsingToMassive();
                String[][] result = TableFilterer.filterList(dragons, columnToFilter, value, typeOfFiltering);
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
                if ("type".equals(field)) {
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
                if ("color".equals(field)) {
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

    public void drawSortPanel() {
        subFrame.setSize(Constants.screenWidth / PART_OF_SCREEN, Constants.screenHeight / PART_OF_SCREEN);
        subFrame.setVisible(true);
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        typeOfFilter = new JComboBox<>(types);
        columns = new JComboBox<>(columnsNames);
        filterButton = BasicGUIElementsFabric.createBasicButton("Filter!");
        typeOfFilter.setFont(Constants.mainFont);
        columns.setFont(Constants.mainFont);
        fieldForValue.setFont(Constants.mainFont);
        typesComboBox.setFont(Constants.mainFont);
        colorsComboBox.setFont(Constants.mainFont);
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
