package zula.gui;

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
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.GetServerMessageException;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.SendException;
import zula.common.util.InputManager;
import zula.common.util.IoManager;
import zula.util.GraphicOutputManager;
import zula.util.Parcers;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {
    private ConnectionManager connectionManager;
    private JFrame mainFrame;
    public CommandExecutor(ConnectionManager connectionManager, JFrame mainFrame) {
        this.connectionManager = connectionManager;
        this.mainFrame = mainFrame;
    }
    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    private synchronized String sendCommandReturningString(Command command, Serializable[] args) {
        try {
            connectionManager.sendToServer(command, args);
            return connectionManager.getMessage().getArguments()[0].toString();
        } catch (SendException | GetServerMessageException e) {
            e.printStackTrace();
            return null;
        }
    }
    private synchronized ServerMessage sendCommandReturningServerMessage(Command command, Serializable[] args) {
        try {
            connectionManager.sendToServer(command, args);
            return connectionManager.getMessage();
        } catch (SendException | GetServerMessageException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String[][] showCommand() {
        Show show = new Show();
        try {
            connectionManager.sendToServer(show, new Serializable[]{""});
            List<Dragon> result = new ArrayList<>();
            ServerMessage serverMessage = connectionManager.getMessage();

            for (int i = 0; i < serverMessage.getArguments().length; i++) {
                result.add((Dragon) serverMessage.getArguments()[i]);
            }
            Parcers parcers = new Parcers();
            return parcers.parseTableFromDragons(result);
        } catch (SendException | GetServerMessageException e) {
            e.printStackTrace();
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
    public String helpCommand() {
        Help help = new Help();
        return sendCommandReturningString(help, new Serializable[]{""});

    }
    public String infoCommand() {
        Info info = new Info();
        return sendCommandReturningString(info, new Serializable[]{""});
    }


    public String printAscendingCommand() {
        PrintAscending printAscending = new PrintAscending();
        return sendCommandReturningString(printAscending, new Serializable[]{""});
    }
    public String printFieldAscendingWingspanCommand() {
        PrintFieldAscendingWingspan printFieldAscendingWingspan = new PrintFieldAscendingWingspan();
        return sendCommandReturningString(printFieldAscendingWingspan, new Serializable[]{""});
    }
    public String averageOfWingspan() {
        AverageOfWingspan averageOfWingspan = new AverageOfWingspan();
        return sendCommandReturningString(averageOfWingspan, new Serializable[]{""});
    }
    public String removeLastCommand() {
        RemoveLast removeLast = new RemoveLast();
        return sendCommandReturningString(removeLast, new Serializable[]{""});
    }
    public String removeByIdCommand(int id) {
        RemoveById removeById = new RemoveById();
        return sendCommandReturningString(removeById, new Serializable[]{id});
    }
    public String exitCommand() {
        Exit exit = new Exit();
        mainFrame.dispose();
        return sendCommandReturningString(exit, new Serializable[]{""});
    }
    public void executeScript(JTextArea jTextArea, File file) {
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
        AppForExecuteScript appForExecuteScript = new AppForExecuteScript(ioManager, connectionManager);
        try {
            appForExecuteScript.startApp();
        } catch (PrintException ex) {
            jTextArea.setText("Не удалось закончить работу");
        }
    }
    public String removeLowerCommand(int id) {
        RemoveLower removeLower = new RemoveLower();
        return sendCommandReturningString(removeLower, new Serializable[]{id});
    }
    public String clearCommand() {
        return sendCommandReturningString(new Clear(), new Serializable[]{""});
    }
    public String reorder() {
        return sendCommandReturningString(new Reorder(), new Serializable[]{""});
    }
    public ResponseCode getDragonById(int id) {
        return sendCommandReturningServerMessage(new DragonByIdCommand(), new Serializable[]{id}).getResponseStatus();
    }
    public void addCommand(Dragon dragon) {
        sendCommandReturningString(new Add(), new Serializable[]{dragon});
    }
    public void updateId(Dragon dragon) {
        sendCommandReturningString(new UpdateId(), new Serializable[]{dragon});
    }
}
