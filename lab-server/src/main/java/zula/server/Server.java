package zula.server;


import zula.common.exceptions.PrintException;
import zula.common.exceptions.WrongArgumentException;
import zula.common.util.InputManager;
import zula.common.util.IoManager;
import zula.server.commands.Save;
import zula.server.util.Client;
import zula.server.util.ListManager;
import zula.server.util.ServerOutputManager;
import zula.server.util.XmlManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;


public final class Server {
    private static final int TIMEOUT = 10;
    private static final Logger SERVERLOGGER = Logger.getLogger("Server logger");
    private static XmlManager xmlManager = null;
    private static ObjectInputStream in;
    private static OutputStream out = null;
    private static int port;
    private static ListManager listManager = new ListManager();
    private static IoManager ioManager = null;
    private static boolean serverStillWorks = true;
    private static Scanner scanner  = new Scanner(System.in);
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ServerSocket server;
    private static String filePath;

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }


    public static void main(String[] args) {
        try {
            if (args.length != 2 || args[0] == null) {
                return;
            }
            filePath = args[0];
            xmlManager = new XmlManager(listManager, ioManager);
            listManager.setPath(args[0]);
            port = Integer.parseInt(args[1]);
            xmlManager.fromXML(args[0]);
            server = new ServerSocket(port);
            while (serverStillWorks) {
                try {
                    checkForConsoleCommands();
                    checkForNewClients();
                    ServerApp serverApp = new ServerApp();
                    serverApp.startApp(listManager, clients);
                } catch (IOException e) {
                    e.printStackTrace();
                    Save save = new Save();
                    save.execute(xmlManager, listManager, filePath);
                } catch (PrintException e) {
                    SERVERLOGGER.severe("Запись невозможна");
                } catch (NoSuchElementException e) {
                    return;
                }
            }
        } catch (WrongArgumentException e) {
            SERVERLOGGER.severe("В пути к файлу или в его содержимом - ошибка");
        } catch (NumberFormatException e) {
            SERVERLOGGER.severe("Неверные аргументы");
        } catch (IOException e) {
            SERVERLOGGER.severe("Не удалось запустить сервер");
        }
    }


    public static void checkForNewClients() throws IOException {
        while (true) {
            try {
                server.setSoTimeout(TIMEOUT);
                Socket clientSocket = server.accept();
                IoManager ioManager1 = new IoManager(new InputManager(new InputStreamReader(clientSocket.getInputStream())),
                        new ServerOutputManager(clientSocket.getOutputStream()));
                Client client = new Client(clientSocket, ioManager1);
                clients.add(client);
            } catch (SocketTimeoutException e) {
            return;
        }
    }
}
    public static boolean checkForConsoleCommands() throws IOException, PrintException {
        if (System.in.available() > 0) {
            String command = scanner.nextLine();
            if ("exit".equals(command)) {
                Save save = new Save();
                save.execute(xmlManager, listManager, filePath);
                SERVERLOGGER.info("До свидания!");
                serverStillWorks = false;
                return false;
            }
            if ("save".equals(command)) {
                Save save = new Save();
                save.execute(xmlManager, listManager, filePath);
                SERVERLOGGER.info("Команда выполнена!");
            }
        }
    return true;
    }
}
