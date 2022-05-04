package zula.server.util;

import zula.common.util.AbstractClient;
import zula.common.util.CollectionManager;
import zula.common.util.IoManager;
import zula.common.util.SQLManager;

import java.io.ObjectInputStream;
import java.net.Socket;

public class Client extends AbstractClient {
    private int id = -1;
    private Socket socket;
    private  ObjectInputStream objectInputStream = null;
    private IoManager ioManager = null;


    public Client(Socket socket, IoManager ioManager, SQLManager sqlManager, CollectionManager collectionManager) {
        super(sqlManager, collectionManager);
        this.socket = socket;
        this.ioManager = ioManager;

    }

    public Socket getSocket() {
        return socket;
    }


    public IoManager getIoManager() {
        return ioManager;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }
}
