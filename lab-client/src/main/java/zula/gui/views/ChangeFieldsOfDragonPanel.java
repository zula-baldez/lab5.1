package zula.gui.views;

import zula.client.ConnectionManager;
import zula.common.data.Dragon;
import zula.common.exceptions.WrongArgumentException;
import zula.gui.controllers.AddPanelController;
import zula.util.CommandExecutor;

import java.util.ResourceBundle;
/*
панель, которая вылезает при нажатии на дракона в визуальном стиле
 */
public class ChangeFieldsOfDragonPanel extends AddPanel {
    private final AddPanelController addPanelController = new AddPanelController();
    private final int dragonId;
    private final CommandExecutor commandExecutor;
    public ChangeFieldsOfDragonPanel(ConnectionManager connectionManager, ResourceBundle resourceBundle, Dragon dragon, MainScreen mainScreen) {
        super(connectionManager, resourceBundle, mainScreen);
        this.dragonId = dragon.getId();
        initTextFields(dragon);
        addDeleteButton(dragonId);
        commandExecutor = new CommandExecutor(connectionManager, getMainFrame());
    }

    protected void setListenerForSubmitButton() {
        getSubmitButton().addActionListener(e -> {
            try {
                Dragon dragon = parseDragonFromData();
                dragon.setId(dragonId);
                addPanelController.updateAndClose(commandExecutor, getCurrentBundle(), getMainFrame(), dragon);
            } catch (WrongArgumentException wrongArgumentException) {
                errorHandler();
            }
        });
}
}
