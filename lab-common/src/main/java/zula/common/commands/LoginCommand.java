package zula.common.commands;

import zula.common.exceptions.PrintException;
import zula.common.util.AbstractClient;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;
import zula.common.util.SQLManager;

import java.io.Serializable;

public class LoginCommand extends Command{
    @Override
    public void doInstructions(IoManager ioManager, AbstractClient client, Serializable[] arguments) throws PrintException {
        String login = arguments[0].toString();
        String password = arguments[1].toString();

        ioManager.getOutputManager().writeServerMessage(client.getSqlManager().login(login, password, client));


    }
}