package zula.server;


import zula.common.data.Dragon;
import zula.common.exceptions.PrintException;
import zula.common.util.InputManager;
import zula.common.util.IoManager;
import zula.server.util.Client;
import zula.server.util.ListManager;
import zula.server.util.SQLCollectionManager;
import zula.server.util.ServerOutputManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;


public final class Server {
    private static final int TIMEOUT = 10;
    private static final Logger SERVERLOGGER = Logger.getLogger("Server logger");
    private static ObjectInputStream in;
    private static OutputStream out = null;
    private static int port;
    private static ListManager listManager;
    private static IoManager ioManager = null;
    private static boolean serverStillWorks = true;
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ServerSocket server;
    private static String filePath;
    private static SQLCollectionManager sqlCollectionManager;

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }


    public static void main(String[] args) {

        if (args.length != 5 || args[0] == null) {
            return;
        }
        filePath = args[0];
        String name = args[1];
        String user = args[2];
        String password = args[3];
        port = Integer.parseInt(args[4]);
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + name, user, password)) {
            sqlCollectionManager = new SQLCollectionManager(connection);
            server = new ServerSocket(port);
            while (serverStillWorks) {
                try {
                    checkForConsoleCommands();
                    checkForNewClients();
                    ServerApp serverApp = new ServerApp();
                    serverApp.startApp(listManager, clients, sqlCollectionManager);
                } catch (IOException e) {
                    e.printStackTrace();
/*
                    save.execute(xmlManager, listManager, filePath);
*/
                } catch (PrintException e) {
                    SERVERLOGGER.severe("Запись невозможна");
                } catch (NoSuchElementException e) {
                    return;
                }
            }
            for (Dragon e : listManager.getCopyOfList()) {

                sqlCollectionManager.add(e);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            SERVERLOGGER.severe("Неверные аргументы");
        } catch (IOException e) {
            e.printStackTrace();

            SERVERLOGGER.severe("Не удалось запустить сервер");
        }
    }


    public static void checkForNewClients() throws IOException {
        while (true) {
            try {
                server.setSoTimeout(TIMEOUT);
                Socket clientSocket = server.accept();
                IoManager ioManager1 = new IoManager(new InputManager(new InputStreamReader(clientSocket.getInputStream())), new ServerOutputManager(clientSocket.getOutputStream()));
                ListManager listManager1 = new ListManager();
                sqlCollectionManager.start(listManager1);
                Client client = new Client(clientSocket, ioManager1, sqlCollectionManager, listManager1);
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
/*
                save.execute(xmlManager, listManager, filePath);
*/
                SERVERLOGGER.info("До свидания!");
                serverStillWorks = false;
                return false;
            }
            if ("save".equals(command)) {
/*
                save.execute(xmlManager, listManager, filePath);
*/
                SERVERLOGGER.info("Команда выполнена!");
            }
        }
        return true;
    }
}
