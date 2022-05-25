package zula.gui;
import zula.client.ConnectionManager;
import zula.common.data.Dragon;
import zula.util.Constants;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

public class SortFrame {
    private JFrame mainFrame;
    private ConnectionManager connectionManager;
    private ResourceBundle currentBundle;
    public SortFrame(JFrame mainFrame, ConnectionManager connectionManager, ResourceBundle currentBundle) {

        this.connectionManager = connectionManager;
        this.mainFrame = mainFrame;
        this.currentBundle = currentBundle;

    }
    JFrame subFrame = new JFrame();

    JPanel mainPanel;
    JComboBox<String> typesOfSorting;
    JComboBox<String> columns;
    String[] types = new String[]{"From a to z", "From z to a", "Random"};
    private final String[] columnsNames = {"id", "name", "x", "y", "creationDate", "age", "wingspan", "color", "type", "depth", "Number ot Treasures", "owner_id"};
    JButton sortButton;
    private void setSortButtonListener() {
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeOfSorting = (String) typesOfSorting.getSelectedItem(); //TODO null point
                String columnToSort = (String) columns.getSelectedItem();
                CommandExecutor commandExecutor = new CommandExecutor(connectionManager, mainFrame);
                List<Dragon> dragons = commandExecutor.showWithoutParsingToMassive();
                String[][] result = TableSorter.sortList(dragons, columnToSort, typeOfSorting);
                MainScreen mainScreen = new MainScreen(connectionManager, mainFrame, currentBundle);
                subFrame.dispose();
                mainScreen.setTableValue(result);
                mainScreen.startMain(false);
                mainFrame.setEnabled(true);
            }
        });
    }
    public void drawSortPanel() {
        subFrame.setSize(Constants.screenWidth / 5, Constants.screenHeight / 5);
        subFrame.setVisible(true);
        subFrame.setLocationRelativeTo(null);
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        typesOfSorting = new JComboBox<>(types);
        columns = new JComboBox<>(columnsNames);
        sortButton = new JButton("Sort!");
        typesOfSorting.setFont(Constants.mainFont);
        columns.setFont(Constants.mainFont);
        sortButton.setFont(Constants.mainFont);
        mainPanel.add(typesOfSorting);
        mainPanel.add(columns);
        mainPanel.add(sortButton);
        subFrame.add(mainPanel);
        subFrame.pack();
        setSortButtonListener();

    }
}
