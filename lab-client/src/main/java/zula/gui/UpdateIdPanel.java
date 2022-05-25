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
    private int dragonId;
    public UpdateIdPanel(ConnectionManager connectionManager, ResourceBundle resourceBundle, int dragonId) {
        super(connectionManager, resourceBundle);
        this.dragonId = dragonId;
    }
    protected void setListenerForSubmitButton() {
        getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Dragon dragon = parseDragonFromData();
                    dragon.setId(dragonId);
                    try {
                        getConnectionManager().sendToServer(new UpdateId(), new Serializable[]{dragon});
                        errorHandler(getConnectionManager().getMessage().getArguments()[0].toString());
                    } catch (SendException ex) {
                        ex.printStackTrace();
                    } catch (GetServerMessageException getServerMessageException) {
                        getServerMessageException.printStackTrace();
                    }
                    getMainFrame().dispose();

                } catch (WrongArgumentException wrongArgumentException) {
                    errorHandler("CHECK THE CURRENCY OF THE DATA");
                }
            }
        });
    }
}
