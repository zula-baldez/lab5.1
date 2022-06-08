package zula.gui.controllers;

import zula.common.data.Dragon;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.gui.views.AddPanel;
import zula.gui.views.FilterFrame;
import zula.gui.views.MainScreen;
import zula.gui.views.SortFrame;
import zula.gui.views.UpdateIdPanel;
import zula.gui.views.VisualStyleMain;
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

    public void updateAndAdd(ResourceBundle currentBundle, JComboBox<String> commands, JTextField argumentTextField, CommandExecutor commandExecutor, JTextArea textResult, MainScreen mainScreen) {
        if (currentBundle.getString(("update_id")).equals(commands.getSelectedItem().toString())) {
            try {
                int id = Integer.parseInt(argumentTextField.getText());
                ServerMessage serverMessage = commandExecutor.getDragonById(id, currentBundle);
                if (serverMessage.getResponseStatus() == ResponseCode.OK) {
                    UpdateIdPanel changeFieldsOfDragonPanel = new UpdateIdPanel(commandExecutor.getConnectionManager(), currentBundle, (Dragon) serverMessage.getArguments()[0], mainScreen);
                    changeFieldsOfDragonPanel.drawPanel();
                } else {
                    textResult.setText(currentBundle.getString("Either this id does not exist, or you are not its creator:("));
                }
            } catch (NumberFormatException ee) {
                textResult.setText(currentBundle.getString("Id must be a number!"));
            }
        }
        if (commands.getSelectedItem().toString().equals(currentBundle.getString("add"))) {
            AddPanel addPanel = new AddPanel(commandExecutor.getConnectionManager(), currentBundle, mainScreen);
            addPanel.drawPanel();
        }
    }
}
