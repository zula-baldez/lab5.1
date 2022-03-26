package zula.server;

import zula.common.commands.ReadDataFromFile;
import zula.common.data.ResponseCode;
import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.WrongArgumentException;

import zula.common.util.InputManager;
import zula.common.util.IoManager;
import zula.common.util.ListManager;
import zula.common.util.ServerOutputManager;
import zula.server.util.XmlManager;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;


public final class Server {
    private static final Logger SERVERLOGGER = Logger.getLogger("Server logger");
    private static XmlManager xmlManager = null;
    private static ObjectInputStream in;
    private static ObjectOutputStream out = null;
    private static final int PORT = 4004;
    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }


    public static void main(String[] args) throws ParserConfigurationException {
        ListManager listManager = new ListManager();
        try {
            ServerSocket server = new ServerSocket(PORT);
            Socket clientSocket = server.accept();
            try  {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                IoManager ioManager = new IoManager(new InputManager(new InputStreamReader(in)), new ServerOutputManager(out));
                SERVERLOGGER.info("успешное соединение");
                while (ioManager.isProcessStillWorks()) {
                    ServerMessage serverMessage = (ServerMessage) in.readObject();
                    if (serverMessage.getCommand() instanceof ReadDataFromFile) {
                        xmlManager = new XmlManager(listManager, ioManager);
                        listManager.setPath(serverMessage.getArguments().toString());
                        xmlManager.fromXML(serverMessage.getArguments().toString());
                        out.writeObject(new ServerMessage("", ResponseCode.OK));
                    } else {
                        serverMessage.getCommand().execute(ioManager, listManager, serverMessage.getArguments());
                        SERVERLOGGER.info("Успешное выполнение команды");
                    }
                }
            }  catch (ClassNotFoundException | PrintException e) {
                xmlManager.toXML(listManager.getCopyOfList(), listManager.getPath());
                return;
            } catch (WrongArgumentException e) {
                out.writeObject(new ServerMessage("В данных XML - ошибка",  ResponseCode.ERROR));
                return;
            }
        } catch (IOException e) {
            if (xmlManager != null) {
                xmlManager.toXML(listManager.getCopyOfList(), listManager.getPath());
            }
            return;
        }
        xmlManager.toXML(listManager.getCopyOfList(), listManager.getPath());
    }
}
