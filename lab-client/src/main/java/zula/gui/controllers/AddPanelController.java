package zula.gui.controllers;

import zula.common.data.Dragon;
import zula.util.CommandExecutor;

import javax.swing.JFrame;
import java.util.ResourceBundle;

public class AddPanelController {
    public void addAndClose(CommandExecutor commandExecutor, ResourceBundle currentBundle, JFrame mainFrame, Dragon dragon) {
        commandExecutor.addCommand(dragon, currentBundle);
        mainFrame.dispose();

    }
    public void deleteAndClose(CommandExecutor commandExecutor, JFrame mainFrame, ResourceBundle currentBundle, int id) {
        commandExecutor.removeByIdCommand(id, currentBundle);
        mainFrame.dispose();
    }
    public void updateAndClose(CommandExecutor commandExecutor, ResourceBundle currentBundle, JFrame mainFrame, Dragon dragon) {
        commandExecutor.updateId(dragon, currentBundle);
        mainFrame.dispose();
    }
}
