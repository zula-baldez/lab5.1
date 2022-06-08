package zula.util;

import zula.app.AppForExecuteScript;
import zula.client.ConnectionManager;
import zula.common.commands.Add;
import zula.common.commands.AverageOfWingspan;
import zula.common.commands.Clear;
import zula.common.commands.Command;
import zula.common.commands.DragonByIdCommand;
import zula.common.commands.Exit;
import zula.common.commands.Help;
import zula.common.commands.Info;
import zula.common.commands.PrintAscending;
import zula.common.commands.PrintFieldAscendingWingspan;
import zula.common.commands.RemoveById;
import zula.common.commands.RemoveLast;
import zula.common.commands.RemoveLower;
import zula.common.commands.Reorder;
import zula.common.commands.Show;
import zula.common.commands.UpdateId;
import zula.common.data.Dragon;
import zula.common.data.ServerMessage;
import zula.common.exceptions.GetServerMessageException;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.SendException;
import zula.common.util.InputManager;
import zula.common.util.IoManager;


import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CommandExecutor {
    private final ConnectionManager connectionManager;
    private final JFrame mainFrame;
    public CommandExecutor(ConnectionManager connectionManager, JFrame mainFrame) {
        this.connectionManager = connectionManager;
        this.mainFrame = mainFrame;
    }
    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    private synchronized String sendCommandReturningString(Command command, Serializable[] args, ResourceBundle resourceBundle) {
        try {
            connectionManager.sendToServer(command, args);
            return resourceBundle.getString(connectionManager.getMessage().getArguments()[0].toString());
        } catch (SendException | GetServerMessageException e) {
            e.printStackTrace();
            return resourceBundle.getString("SERVER UMER");
        }
    }
    private synchronized String sendCommandReturningStringWithoutTranslate(Command command, Serializable[] args, ResourceBundle resourceBundle) {
        try {
            connectionManager.sendToServer(command, args);
            return (connectionManager.getMessage().getArguments()[0].toString());
        } catch (SendException | GetServerMessageException e) {
            e.printStackTrace();
            return resourceBundle.getString("SERVER UMER");
        }
    }
    private synchronized ServerMessage sendCommandReturningServerMessage(Command command, Serializable[] args, ResourceBundle resourceBundle) {
        try {
            connectionManager.sendToServer(command, args);
            return connectionManager.getMessage();
        } catch (SendException | GetServerMessageException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String[][] showCommand(Locale locale) {
        List<Dragon> result = showWithoutParsingToMassive();
        if (result != null) {
            Parcers parcers = new Parcers();
            return parcers.parseTableFromDragons(result, locale);
        } else {
            return null;
        }
    }
    public List<Dragon> showWithoutParsingToMassive() {
        Show show = new Show();
        try {
            connectionManager.sendToServer(show, new Serializable[]{""});
            List<Dragon> result = new ArrayList<>();
            ServerMessage serverMessage = connectionManager.getMessage();

            for (int i = 0; i < serverMessage.getArguments().length; i++) {
                result.add((Dragon) serverMessage.getArguments()[i]);
            }
            return result;
        } catch (SendException | GetServerMessageException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String helpCommand(ResourceBundle resourceBundle) {
        Help help = new Help();
        return sendCommandReturningString(help, new Serializable[]{""}, resourceBundle);

    }
    public String infoCommand(ResourceBundle resourceBundle) {
        Info info = new Info();
        ServerMessage serverMessage = sendCommandReturningServerMessage(info, new Serializable[]{}, resourceBundle);
        return resourceBundle.getString("size - ") + serverMessage.getArguments()[0].toString() + ' ' + resourceBundle.getString("date of creation - ") + serverMessage.getArguments()[1].toString() + " " + resourceBundle.getString("type - ") + serverMessage.getArguments()[2];

    }


    public String printAscendingCommand(ResourceBundle resourceBundle) {
        PrintAscending printAscending = new PrintAscending();
        return sendCommandReturningStringWithoutTranslate(printAscending, new Serializable[]{""}, resourceBundle);
    }
    public String printFieldAscendingWingspanCommand(ResourceBundle resourceBundle) {
        PrintFieldAscendingWingspan printFieldAscendingWingspan = new PrintFieldAscendingWingspan();
        return sendCommandReturningStringWithoutTranslate(printFieldAscendingWingspan, new Serializable[]{""}, resourceBundle);
    }
    public String averageOfWingspan(ResourceBundle resourceBundle) {
        AverageOfWingspan averageOfWingspan = new AverageOfWingspan();
        return sendCommandReturningStringWithoutTranslate(averageOfWingspan, new Serializable[]{""}, resourceBundle);
    }
    public String removeLastCommand(ResourceBundle resourceBundle) {
        RemoveLast removeLast = new RemoveLast();
        return sendCommandReturningString(removeLast, new Serializable[]{""}, resourceBundle);
    }
    public String removeByIdCommand(int id, ResourceBundle resourceBundle) {
        RemoveById removeById = new RemoveById();
        return sendCommandReturningString(removeById, new Serializable[]{id}, resourceBundle);
    }
    public String exitCommand(ResourceBundle resourceBundle) {
        Exit exit = new Exit();
        mainFrame.dispose();
        return sendCommandReturningString(exit, new Serializable[]{""}, resourceBundle);
    }
    public void executeScript(JTextArea jTextArea, File file, ResourceBundle resourceBundle) {
        InputManager inputManager = new InputManager(new InputStreamReader(System.in));
        GraphicOutputManager outputManager = new GraphicOutputManager();
        IoManager ioManager = new IoManager(inputManager, outputManager);
        try {
            inputManager.setFileReading(true, file.getAbsolutePath());
        } catch (FileNotFoundException ex) {
            jTextArea.setText("Проверьте, что выбран корректный файл");
            return;
        }
        outputManager.setJTextField(jTextArea);
        AppForExecuteScript appForExecuteScript = new AppForExecuteScript(ioManager, connectionManager, resourceBundle);
        try {
            appForExecuteScript.startApp();
        } catch (PrintException ex) {
            jTextArea.setText("Не удалось закончить работу");
        }
    }
    public String removeLowerCommand(int id, ResourceBundle resourceBundle) {
        RemoveLower removeLower = new RemoveLower();
        return sendCommandReturningString(removeLower, new Serializable[]{id}, resourceBundle);
    }
    public String clearCommand(ResourceBundle resourceBundle) {
        return sendCommandReturningString(new Clear(), new Serializable[]{""}, resourceBundle);
    }
    public String reorder(ResourceBundle resourceBundle) {
        return sendCommandReturningString(new Reorder(), new Serializable[]{""}, resourceBundle);
    }
    public ServerMessage getDragonById(int id, ResourceBundle resourceBundle) {
        return sendCommandReturningServerMessage(new DragonByIdCommand(), new Serializable[]{id}, resourceBundle);

    }
    public void addCommand(Dragon dragon, ResourceBundle resourceBundle) {
        sendCommandReturningString(new Add(), new Serializable[]{dragon}, resourceBundle);
    }
    public void updateId(Dragon dragon, ResourceBundle resourceBundle) {
        sendCommandReturningStringWithoutTranslate(new UpdateId(), new Serializable[]{dragon}, resourceBundle);
    }
}
