package zula.gui;


import zula.client.ConnectionManager;
import zula.common.commands.Add;
import zula.common.commands.UpdateId;
import zula.common.data.Dragon;
import zula.common.exceptions.GetServerMessageException;
import zula.common.exceptions.SendException;
import zula.common.exceptions.WrongArgumentException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ResourceBundle;

public class UpdateIdPanel extends AddPanel {
    private final int dragonId;
    private final CommandExecutor commandExecutor;
    public UpdateIdPanel(ConnectionManager connectionManager, ResourceBundle resourceBundle, int dragonId) {
        super(connectionManager, resourceBundle);
        this.dragonId = dragonId;
        commandExecutor = new CommandExecutor(connectionManager, getMainFrame());
    }
    protected void setListenerForSubmitButton() {
        getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Dragon dragon = parseDragonFromData();
                    dragon.setId(dragonId);
                    commandExecutor.updateId(dragon);
                    getMainFrame().dispose();

                } catch (WrongArgumentException wrongArgumentException) {
                    errorHandler("CHECK THE CURRENCY OF THE DATA");
                }
            }
        });
    }
}
