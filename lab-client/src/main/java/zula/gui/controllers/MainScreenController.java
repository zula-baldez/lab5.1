package zula.gui.controllers;

import zula.common.data.ResponseCode;
import zula.gui.views.*;
import zula.util.CommandExecutor;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.File;
import java.util.ResourceBundle;

public class MainScreenController { //Теперь MainScreen управляет только собственным состоянием, но не управляет переходом на другие экраны
    public void createFilterFrame(JFrame mainFrame, CommandExecutor commandExecutor, ResourceBundle currentBundle) {
        FilterFrame filterFrame = new FilterFrame(mainFrame, commandExecutor.getConnectionManager(), currentBundle);
        mainFrame.setEnabled(false);
        filterFrame.drawFilterPanel();
    }
    public void createSortFrame(JFrame mainFrame, CommandExecutor commandExecutor, ResourceBundle currentBundle) {
        SortFrame sortFrame = new SortFrame(mainFrame, commandExecutor.getConnectionManager(), currentBundle);
        mainFrame.setEnabled(false);
        sortFrame.drawSortPanel();
    }
    public void createVisualStyleFrame(JFrame mainFrame, CommandExecutor commandExecutor, ResourceBundle currentBundle) {
        VisualStyleMain visualStyleMain = new VisualStyleMain(mainFrame, commandExecutor.getConnectionManager(), currentBundle);
        visualStyleMain.printGraphics();
    }
    public void executeScript(JFileChooser executeScriptFileChooser, CommandExecutor commandExecutor, JTextArea textResult, ResourceBundle currentBundle) {
        File file = executeScriptFileChooser.getSelectedFile();
        commandExecutor.executeScript(textResult, file, currentBundle);
    }
    public void updateAndAdd(ResourceBundle currentBundle, JComboBox<String> commands, JTextField argumentTextField, CommandExecutor commandExecutor, JTextArea textResult) {
        if (currentBundle.getString(("update_id")).equals(commands.getSelectedItem().toString())) {
            try {
                int id = Integer.parseInt(argumentTextField.getText());
                if (ResponseCode.OK == commandExecutor.getDragonById(id, currentBundle)) {
                    UpdateIdPanel changeFieldsOfDragonPanel = new UpdateIdPanel(commandExecutor.getConnectionManager(), currentBundle, id);
                    changeFieldsOfDragonPanel.drawPanel();
                } else {
                    textResult.setText(currentBundle.getString("Вы не его владелец("));
                }
            } catch (NumberFormatException ee) {
                textResult.setText(currentBundle.getString("Id must be a number!"));
            }
        }
        if (commands.getSelectedItem().toString().equals(currentBundle.getString("add"))) {
            AddPanel addPanel = new AddPanel(commandExecutor.getConnectionManager(), currentBundle);
            addPanel.drawPanel();
        }
    }
}
