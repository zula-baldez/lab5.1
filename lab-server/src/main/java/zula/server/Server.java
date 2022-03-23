package zula.server;

import org.w3c.dom.ls.LSOutput;
import zula.common.commands.Command;
import zula.common.commands.GetListOfCommands;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.WrongArgumentException;
import zula.common.util.*;
import zula.server.util.Save;
import zula.server.util.XmlManager;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;




public final class Server {
    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }


    public static void main(String[] args) throws ParserConfigurationException {
        XmlManager xmlManager = null;
        ObjectInputStream in;
        ObjectOutputStream out = null;
        ListManager listManager = new ListManager();
        try {
            ServerSocket server = new ServerSocket(4004);

            Socket clientSocket = server.accept();
            try  {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                IoManager ioManager = new IoManager(new InputManager(new InputStreamReader(in)), new ServerOutputManager(out));
                xmlManager = new XmlManager(listManager, ioManager);
                xmlManager.fromXML(listManager.getPath());
                while (ioManager.isProcessStillWorks()) {
                    ServerMessage serverMessage = (ServerMessage) in.readObject();
                    if (serverMessage.getCommand() instanceof GetListOfCommands) {
                        out.writeObject(new ServerMessage(listManager.getCloneOfCommands(), ResponseCode.OK));
                        out.flush();
                    } else {

                        serverMessage.getCommand().execute(ioManager, listManager, serverMessage.getArguments());
                    }

                }
            }  catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (PrintException e) {
                e.printStackTrace();
            } catch (WrongArgumentException e) {
                out.writeObject(new ServerMessage("В данных XML - ошибка",  ResponseCode.ERROR));
                return;
            }
        } catch (IOException e) {

           e.printStackTrace();
        }
        xmlManager.toXML(listManager.getCopyOfList(), listManager.getPath());

    }
}
