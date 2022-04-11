package zula.server;


import zula.common.exceptions.WrongArgumentException;
import zula.common.util.InputManager;
import zula.common.util.IoManager;
import zula.server.commands.Save;
import zula.server.util.ListManager;
import zula.server.util.ServerOutputManager;
import zula.server.util.XmlManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;


public final class Server {
    private static final Logger SERVERLOGGER = Logger.getLogger("Server logger");
    private static XmlManager xmlManager = null;
    private static ObjectInputStream in;
    private static OutputStream out = null;
    private static int port;
    private static ListManager listManager = new ListManager();
    private static IoManager ioManager = null;

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }


    public static void main(String[] args) {
        try {
            if (args.length != 2 || args[0] == null) {
                return;
            }
            xmlManager = new XmlManager(listManager, ioManager);
            listManager.setPath(args[0]);
            port = Integer.parseInt(args[1]);
            xmlManager.fromXML(args[0]);
            ServerSocket server = new ServerSocket(port);
            Socket clientSocket = server.accept();
            out = clientSocket.getOutputStream();
            in = new ObjectInputStream(clientSocket.getInputStream());
            ioManager = new IoManager(new InputManager(new InputStreamReader(in)), new ServerOutputManager(out));
            SERVERLOGGER.info("успешное соединение");
            ServerApp serverApp = new ServerApp();
            serverApp.startApp(ioManager, in, listManager);
            Save save = new Save();
            save.execute(ioManager, listManager);
        } catch (WrongArgumentException e) {
            SERVERLOGGER.severe("В пути к файлу или в его содержимом - ошибка");
        } catch (IOException e) {
            Save save = new Save();
            save.execute(ioManager, listManager);
            SERVERLOGGER.severe("Проблема с соединением");
        } catch (NumberFormatException e) {
            SERVERLOGGER.severe("Неверные аргументы");

        }

    }
}
