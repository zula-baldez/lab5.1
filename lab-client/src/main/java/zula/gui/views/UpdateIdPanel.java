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
    private final Dragon oldDragon;
    private final CommandExecutor commandExecutor;
    private final MainScreen mainScreen;
    private final AddPanelController addPanelController = new AddPanelController();
    public UpdateIdPanel(ConnectionManager connectionManager, ResourceBundle resourceBundle, Dragon dragon, MainScreen mainScreen) {
        super(connectionManager, resourceBundle, mainScreen);
        this.oldDragon = dragon;
        initTextFields(oldDragon);
        this.mainScreen = mainScreen;
        commandExecutor = new CommandExecutor(connectionManager, getMainFrame());

    }
    @Override
    protected void setListenerForSubmitButton() {
        getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Dragon dragon = parseDragonFromData();
                    dragon.setId(oldDragon.getId());

                    addPanelController.updateAndClose(commandExecutor, getCurrentBundle(), getMainFrame(), dragon);
                    mainScreen.setSettingsForTable(true);

                } catch (WrongArgumentException wrongArgumentException) {
                    errorHandler();
                }
            }
        });
    }
}
