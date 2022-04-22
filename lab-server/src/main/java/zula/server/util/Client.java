package zula.server.util;

import zula.common.util.IoManager;

import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private Integer expectedBytes = null;
    private  ObjectInputStream objectInputStream = null;
    private IoManager ioManager = null;
    public Client(Socket socket1, IoManager ioManager1) {
        socket = socket1;
        ioManager = ioManager1;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setExpectedBytes(Integer expectedBytes) {
        this.expectedBytes = expectedBytes;
    }

    public Integer getExpectedBytes() {
        return expectedBytes;
    }

    public IoManager getIoManager() {
        return ioManager;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }
}
