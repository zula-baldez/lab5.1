package zula.gui.views;


import com.toedter.calendar.JDateChooser;
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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

public class FilterFrame {
    private static final int PART_OF_SCREEN = 5; // 1 / 5
    private static final int AMOUNT_OF_COLUMNS = 15;
    private static final int DATE_CHOOSER_HEIGHT = Constants.SCREEN_HEIGHT / 20;
    private static final int DATE_CHOOSER_WIDTH = Constants.SCREEN_WIDTH / 10;
    private static final int CALENDAR_WIDTH = Constants.SCREEN_WIDTH / 5;

    private static final int CALENDAR_HEIGHT = Constants.SCREEN_HEIGHT / 5;
    private final JFrame mainFrame;
    private final ConnectionManager connectionManager;
    private final ResourceBundle currentBundle;

    private final JFrame subFrame = new JFrame();

    private JPanel mainPanel;
    private JComboBox<String> typeOfFilter;
    private JComboBox<String> columns;
    private final JComboBox<String> typesComboBox = BasicGUIElementsFabric.createBasicComboBox(Constants.TYPES);
    private final JComboBox<String> colorsComboBox = BasicGUIElementsFabric.createBasicComboBox(Constants.COLORS);
    private final String[] types;
    private final String[] columnsNames;
    private JButton filterButton;
    private final JTextField fieldForValue = BasicGUIElementsFabric.createBasicJTextField(AMOUNT_OF_COLUMNS);
    private final JTextField fieldForHours = BasicGUIElementsFabric.createBasicJTextField(2);
    private final JTextField fieldForMinutes = BasicGUIElementsFabric.createBasicJTextField(2);
    private final JTextField fieldForSeconds = BasicGUIElementsFabric.createBasicJTextField(2);
    private final JDateChooser jDateChooser = new JDateChooser();

    public FilterFrame(JFrame mainFrame, ConnectionManager connectionManager, ResourceBundle currentBundle) {
        this.connectionManager = connectionManager;
        this.mainFrame = mainFrame;
        this.currentBundle = currentBundle;
        types = new String[]{currentBundle.getString("More than"), currentBundle.getString("Less than"), currentBundle.getString("Equals")};
        columnsNames = new String[]{currentBundle.getString("id"), currentBundle.getString("name"), currentBundle.getString("x"), currentBundle.getString("y"), currentBundle.getString("creationDate"), currentBundle.getString("age"), currentBundle.getString("wingspan"), currentBundle.getString("color"), currentBundle.getString("type"), currentBundle.getString("depth"), currentBundle.getString("Number ot Treasures")};


    }

    private void setDateChooserSettings() {
        jDateChooser.setLocale(currentBundle.getLocale());
        jDateChooser.setDateFormatString("MMM dd HH:mm:ss yyyy");
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
                } else if (currentBundle.getString("creationDate").equals(columnToFilter)) {
                    Calendar calendar = jDateChooser.getCalendar();
                    try {
                        int hours = Integer.parseInt(fieldForHours.getText());
                        int minutes = Integer.parseInt(fieldForMinutes.getText());
                        int seconds = Integer.parseInt(fieldForSeconds.getText());
                        Calendar calendarResult = new GregorianCalendar();
                        calendarResult.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendarResult.get(Calendar.DAY_OF_MONTH), hours, minutes, seconds);
                        Date result = calendarResult.getTime();
                        value = result.toString();
                    } catch (NumberFormatException ee) {
                        mainFrame.setEnabled(true);
                        subFrame.dispose();
                        return;
                    }
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


    private void typeCase() {
        mainPanel.removeAll();
        mainPanel.add(typeOfFilter);
        mainPanel.add(columns);
        mainPanel.add(typesComboBox);
        mainPanel.add(filterButton);
    }
    private void colorCase() {
        mainPanel.removeAll();
        mainPanel.add(typeOfFilter);
        mainPanel.add(columns);
        mainPanel.add(colorsComboBox);
        mainPanel.add(filterButton);
    }
    private void dateCase() {
        mainPanel.removeAll();
        mainPanel.add(typeOfFilter);
        mainPanel.add(columns);
        mainPanel.add(jDateChooser);
        jDateChooser.setPreferredSize(new Dimension(DATE_CHOOSER_WIDTH, DATE_CHOOSER_HEIGHT));
        jDateChooser.getJCalendar().setPreferredSize(new Dimension(CALENDAR_WIDTH, CALENDAR_HEIGHT));
        fieldForHours.setText("HH");
        fieldForMinutes.setText("MM");
        fieldForSeconds.setText("SS");
        mainPanel.add(fieldForHours);
        mainPanel.add(fieldForMinutes);
        mainPanel.add(fieldForSeconds);
        mainPanel.add(filterButton);
    }
    private void setFilterFieldListener() { //изменение окна в зависимости от выбранного типа данных
        columns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String field = columns.getSelectedItem().toString();
                if (currentBundle.getString("type").equals(field)) {
                    typeCase();
                } else if (currentBundle.getString("color").equals(field)) {
                    colorCase();
                } else if (currentBundle.getString("creationDate").equals(field)) {
                    dateCase();
                } else {
                    mainPanel.removeAll();
                    mainPanel.add(typeOfFilter);
                    mainPanel.add(columns);
                    mainPanel.add(fieldForValue);
                    mainPanel.add(filterButton);
                }
                subFrame.setContentPane(mainPanel);
                subFrame.pack();
                subFrame.revalidate();
                subFrame.repaint();
            }
        });
    }

    public void drawFilterPanel() {
        subFrame.setSize(Constants.SCREEN_WIDTH / PART_OF_SCREEN, Constants.SCREEN_HEIGHT / PART_OF_SCREEN);
        subFrame.setVisible(true);

        typeOfFilter = BasicGUIElementsFabric.createBasicComboBox(types);
        columns = BasicGUIElementsFabric.createBasicComboBox(columnsNames);
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        filterButton = BasicGUIElementsFabric.createBasicButton(currentBundle.getString("Filter!"));
        subFrame.setLocationRelativeTo(null);
        mainPanel.add(typeOfFilter);
        mainPanel.add(columns);
        mainPanel.add(fieldForValue);
        mainPanel.add(filterButton);
        subFrame.add(mainPanel);
        subFrame.pack();
        setFilterButtonListener();
        setFilterFieldListener();
        setDateChooserSettings();
    }
}
