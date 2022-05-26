package zula.gui.views;


import zula.client.ConnectionManager;
import zula.common.data.Dragon;
import zula.common.exceptions.WrongArgumentException;
import zula.gui.controllers.AddPanelController;
import zula.util.CommandExecutor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class UpdateIdPanel extends AddPanel {
    private final int dragonId;
    private final CommandExecutor commandExecutor;
    private final AddPanelController addPanelController = new AddPanelController();
    public UpdateIdPanel(ConnectionManager connectionManager, ResourceBundle resourceBundle, int dragonId) {
        super(connectionManager, resourceBundle);
        this.dragonId = dragonId;
        commandExecutor = new CommandExecutor(connectionManager, getMainFrame());
    }
    @Override
    protected void setListenerForSubmitButton() {
        getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Dragon dragon = parseDragonFromData();
                    dragon.setId(dragonId);
                    addPanelController.updateAndClose(commandExecutor, getCurrentBundle(), getMainFrame(), dragon);
                } catch (WrongArgumentException wrongArgumentException) {
                    errorHandler();
                }
            }
        });
    }
}
