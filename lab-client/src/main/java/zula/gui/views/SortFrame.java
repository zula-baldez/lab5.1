package zula.gui.views;
import zula.client.ConnectionManager;
import zula.common.data.Dragon;
import zula.util.BasicGUIElementsFabric;
import zula.util.CommandExecutor;
import zula.util.Constants;
import zula.util.TableSorter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

public class SortFrame {
    private static final int SCREEN_WIDTH = Constants.SCREEN_WIDTH / 5;
    private static final int SCREEN_HEIGHT = Constants.SCREEN_HEIGHT / 5;
    private final JFrame mainFrame;
    private final ConnectionManager connectionManager;
    private final ResourceBundle currentBundle;
    private final JFrame subFrame = new JFrame();
    private JPanel mainPanel;

    private JComboBox<String> typesOfSorting;
    private JComboBox<String> columns;
    private String[] types;
    private String[] columnsNames;
    private JButton sortButton;
    public SortFrame(JFrame mainFrame, ConnectionManager connectionManager, ResourceBundle currentBundle) {
        this.connectionManager = connectionManager;
        this.mainFrame = mainFrame;
        this.currentBundle = currentBundle;
        types = new String[]{currentBundle.getString("From a to z"), currentBundle.getString("From z to a")};
        columnsNames = new String[]{currentBundle.getString("id"), currentBundle.getString("name"), currentBundle.getString("x"), currentBundle.getString("y"), currentBundle.getString("creationDate"), currentBundle.getString("age"), currentBundle.getString("wingspan"), currentBundle.getString("color"), currentBundle.getString("type"), currentBundle.getString("depth"), currentBundle.getString("Number ot Treasures")};

    }
    private void setSortButtonListener() {
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeOfSorting = (String) typesOfSorting.getSelectedItem();
                String columnToSort = (String) columns.getSelectedItem();
                CommandExecutor commandExecutor = new CommandExecutor(connectionManager, mainFrame);
                List<Dragon> dragons = commandExecutor.showWithoutParsingToMassive();
                String[][] result = TableSorter.sortList(dragons, columnToSort, typeOfSorting, currentBundle);
                MainScreen mainScreen = new MainScreen(connectionManager, mainFrame, currentBundle);
                subFrame.dispose();
                mainScreen.setTableValue(result);
                mainScreen.startMain(false);
                mainFrame.setEnabled(true);
            }
        });
    }
    public void drawSortPanel() {
        subFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        subFrame.setVisible(true);
        subFrame.setLocationRelativeTo(null);
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        typesOfSorting = BasicGUIElementsFabric.createBasicComboBox(types);
        columns = BasicGUIElementsFabric.createBasicComboBox(columnsNames);
        sortButton = BasicGUIElementsFabric.createBasicButton(currentBundle.getString("Sort!"));
        typesOfSorting.setFont(Constants.MAIN_FONT);
        columns.setFont(Constants.MAIN_FONT);
        sortButton.setFont(Constants.MAIN_FONT);
        mainPanel.add(typesOfSorting);
        mainPanel.add(columns);
        mainPanel.add(sortButton);
        subFrame.add(mainPanel);
        subFrame.pack();
        setSortButtonListener();

    }
}
