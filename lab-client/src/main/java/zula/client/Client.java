package zula.client;

import zula.common.exceptions.PrintException;
import zula.common.util.InputManager;
import zula.common.util.IoManager;
import zula.common.util.OutputManager;
import zula.gui.ConnectionScreen;
import zula.gui.VisualStyleMain;

import javax.swing.*;
import java.awt.*;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

public final class Client {
   private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        ConnectionScreen connectionScreen = new ConnectionScreen();

        connectionScreen.printScreen();
     /*   JFrame jFrame = new JFrame();
        jFrame.setSize(new Dimension(1920, 1080));
        jFrame.setUndecorated(true);
        jFrame.setVisible(true);
        VisualStyleMain visualStyleMain = new VisualStyleMain(jFrame);
        visualStyleMain.printGraphics();*/
    }





}
