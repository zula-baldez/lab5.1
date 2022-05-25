package zula.gui;

import zula.client.ConnectionManager;
import zula.common.commands.UpdateId;
import zula.common.data.Dragon;
import zula.common.exceptions.GetServerMessageException;
import zula.common.exceptions.SendException;
import zula.common.exceptions.WrongArgumentException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ResourceBundle;

public class ChangeFieldsOfDragonPanel extends AddPanel {

    int dragonId;
    VisualStyleMain visualStyleMain;
    public ChangeFieldsOfDragonPanel(ConnectionManager connectionManager, ResourceBundle resourceBundle, Dragon dragon, VisualStyleMain visualStyleMain) {
        super(connectionManager, resourceBundle);
        this.dragonId = dragon.getId();
        this.visualStyleMain = visualStyleMain;
        initTextFields(dragon);
        addDeleteButton(dragonId);
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
                        getMainFrame().dispose();
                    } catch (SendException ex) {
                        ex.printStackTrace();
                    } catch (GetServerMessageException getServerMessageException) {
                        getServerMessageException.printStackTrace();
                    }

                } catch (WrongArgumentException wrongArgumentException) {
                    errorHandler("CHECK THE CURRENCY OF THE DATA");
                }
            }
        });
}
}
