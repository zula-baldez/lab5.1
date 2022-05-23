package zula.gui;

import zula.app.AppForExecuteScript;
import zula.client.ConnectionManager;
import zula.common.commands.*;
import zula.common.data.Dragon;
import zula.common.exceptions.GetServerMessageException;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.SendException;
import zula.common.util.InputManager;
import zula.common.util.IoManager;
import zula.util.GraphicOutputManager;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Serializable;

public class MainScreenLogics {
    ConnectionManager connectionManager;
    JFrame mainFrame;
    private final int idIndex = 0;
    private final int nameIndex = 1;
    private final int xIndex = 2;
    private final int yIndex = 3;
    private final int creationDateIndex = 4;
    private final int ageIndex = 5;
    private final int wingspanIndex = 6;
    private final int colorIndex = 7;
    private final int typeIndex = 8;
    private final int depthIndex = 9;
    private final int numOfTresIndex = 10;
    private final int ownerIdIndex = 11;
    public MainScreenLogics(ConnectionManager connectionManager, JFrame mainFrame) {
        this.connectionManager = connectionManager;
        this.mainFrame = mainFrame;
    }

    private String sendCommandReturningString(Command command, Serializable[] args) {
        try {
            connectionManager.sendToServer(command, new Serializable[]{""});
            return connectionManager.getMessage().getArguments()[0].toString();
        } catch (SendException | GetServerMessageException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String[][] parseTableFromDragons(Serializable[] result) {
        String[][] table = new String[result.length][12];
        for(int i = 0; i < result.length; i++) {
            Dragon dragon = (Dragon) result[i];

            table[i][idIndex] = Integer.toString(dragon.getId());
            table[i][nameIndex] = dragon.getName();
            table[i][xIndex] = Double.toString(dragon.getCoordinates().getX());
            table[i][yIndex] = Integer.toString(dragon.getCoordinates().getY());
            table[i][creationDateIndex] = dragon.getCreationDate().toString();
            table[i][ageIndex] = Long.toString(dragon.getAge());
            table[i][wingspanIndex] = Float.toString(dragon.getWingspan());
            if(dragon.getColor() != null) {
                table[i][colorIndex] = dragon.getColor().toString();
            } else {
                table[i][colorIndex] = "null";
            }
            table[i][typeIndex] = dragon.getType().toString();
            if(dragon.getCave().getDepth() != null) {
                table[i][depthIndex] = dragon.getCave().getDepth().toString();
            } else {
                table[i][depthIndex] = "null";
            }
            if(dragon.getCave().getNumberOfTreasures() != null) {
                table[i][numOfTresIndex] = dragon.getCave().getNumberOfTreasures().toString();
            } else {
                table[i][numOfTresIndex] = "null";
            }
            table[i][ownerIdIndex] = Integer.toString(dragon.getOwnerId());

        }
        return table;
    }


    public String[][] showCommand() {
        Show show = new Show();
        try {
            connectionManager.sendToServer(show, new Serializable[]{""});
            Serializable[] result = connectionManager.getMessage().getArguments();
            return parseTableFromDragons(result);
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
        RemoveLast removeLast = new RemoveLast();
        return sendCommandReturningString(removeLast, new Serializable[]{""});
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
}
