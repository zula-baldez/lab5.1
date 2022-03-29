package zula.server;


import zula.common.data.ServerMessage;
import zula.common.exceptions.PrintException;
import zula.common.exceptions.WrongArgumentException;

import zula.common.util.InputManager;
import zula.common.util.IoManager;
import zula.server.util.ListManager;
import zula.server.util.ServerOutputManager;
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
    private static ListManager listManager = new ListManager();

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }


    public static void main(String[] args) throws ParserConfigurationException {

        try {
            ServerSocket server = new ServerSocket(PORT);
            Socket clientSocket = server.accept();

                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                IoManager ioManager = new IoManager(new InputManager(new InputStreamReader(in)), new ServerOutputManager(out));
                if (args.length != 1 || args[0] == null) {
                    return;
                }
                xmlManager = new XmlManager(listManager, ioManager);
                listManager.setPath(args[0]);
                xmlManager.fromXML(args[0]);
                SERVERLOGGER.info("успешное соединение");
                while (ioManager.isProcessStillWorks()) {
                    ServerMessage serverMessage = (ServerMessage) in.readObject();
                    serverMessage.getCommand().execute(ioManager, listManager, serverMessage.getArguments());
                    SERVERLOGGER.info("Успешное выполнение команды");
                    }
            }  catch (ClassNotFoundException | PrintException | IOException e) {
                if (xmlManager != null) {
                    xmlManager.toXML(listManager.getCopyOfList(), listManager.getPath());
                }
                return;
            } catch (WrongArgumentException e) {
                SERVERLOGGER.severe("В пути к файлу или в его содержимом - ошибка");
            }
        xmlManager.toXML(listManager.getCopyOfList(), listManager.getPath());
    }
}
