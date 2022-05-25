package zula.gui;

import org.jdatepicker.JDatePicker;
import zula.client.ConnectionManager;
import zula.common.data.Dragon;
import zula.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

public class FilterFrame {
    private final JFrame mainFrame;
    private final ConnectionManager connectionManager;
    private final ResourceBundle currentBundle;
    public FilterFrame(JFrame mainFrame, ConnectionManager connectionManager, ResourceBundle currentBundle) {

        this.connectionManager = connectionManager;
        this.mainFrame = mainFrame;
        this.currentBundle = currentBundle;

    }

    JFrame subFrame = new JFrame();
    JPanel mainPanel;
    JComboBox<String> typeOfFilter;
    JComboBox<String> columns;
    JComboBox<String> typesComboBox = new JComboBox<String>(Constants.types);
    JComboBox<String> colorsComboBox = new JComboBox<>(Constants.colors);
    String[] types = new String[]{"More than", "Less than", "Equals"};
    private final String[] columnsNames = {"id", "name", "x", "y", "creationDate", "age", "wingspan", "color", "type", "depth", "Number ot Treasures", "owner_id"};
    JButton filterButton;
    JTextField fieldForValue = new JTextField(15);
    private void setFilterButtonListener() {
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeOfFiltering = (String) typeOfFilter.getSelectedItem(); //TODO null point
                String columnToFilter = (String) columns.getSelectedItem();
                String value;
                if("type".equals(columnToFilter)) {
                    value = typesComboBox.getSelectedItem().toString();
                } else if("type".equals(columnToFilter)) {
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
                if("type".equals(field)) {
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
                if("color".equals(field)) {
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
        //TODO появление в центре
        subFrame.setSize(Constants.screenWidth / 5, Constants.screenHeight / 5);
        subFrame.setVisible(true);
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        typeOfFilter = new JComboBox<>(types);
        columns = new JComboBox<>(columnsNames);
        filterButton = new JButton("Filter!");
        typeOfFilter.setFont(Constants.mainFont);
        columns.setFont(Constants.mainFont);
        filterButton.setFont(Constants.mainFont);
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
