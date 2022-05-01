package zula.server;


import zula.common.exceptions.PrintException;
import zula.common.util.InputManager;
import zula.common.util.IoManager;
import zula.server.util.Client;
import zula.server.util.ClientThread;
import zula.server.util.ListManager;
import zula.server.util.SQLCollectionManager;
import zula.server.util.ServerOutputManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private static final int FIVE = 5;
    private static final int FOUR = 4;
    private static final int THREE = 3;
    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }



    public static void main(String[] args) {
        listManager = new ListManager();
        if (args.length != FIVE || args[0] == null) {
            return;
        }
        filePath = args[0];
        String name = args[1];
        String user = args[2];
        String password = args[THREE];
        port = Integer.parseInt(args[FOUR]);
        try (Connection connection = DriverManager.getConnection(filePath + name, user, password)) {
            sqlCollectionManager = new SQLCollectionManager(connection);
            sqlCollectionManager.start(listManager);
            server = new ServerSocket(port);
            while (serverStillWorks) {
                checkForConsoleCommands();
                server.setSoTimeout(TIMEOUT);
                Socket clientSocket;
                try {
                    clientSocket = server.accept();
                } catch (IOException e) {
                    continue;
                }
                IoManager ioManager1 = new IoManager(new InputManager(new InputStreamReader(clientSocket.getInputStream())), new ServerOutputManager(clientSocket.getOutputStream()));
                Client client = new Client(clientSocket, ioManager1, sqlCollectionManager, listManager);
                clientSocket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                client.setObjectInputStream(objectInputStream);
                ClientThread clientThread = new ClientThread(client);
                clientThread.start();
            }
        } catch (SQLException | PrintException e) {
            SERVERLOGGER.severe("Не удалось начать работу");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            SERVERLOGGER.severe("Неверные аргументы");
        } catch (IOException e) {
            SERVERLOGGER.severe("Не удалось запустить сервер");
        }
    }



    public static boolean checkForConsoleCommands() throws IOException, PrintException {
        if (System.in.available() > 0) {
            String command = scanner.nextLine();
            if ("exit".equals(command)) {
                SERVERLOGGER.info("До свидания!");
                serverStillWorks = false;
                return false;
            }
        }
        return true;
    }
}
