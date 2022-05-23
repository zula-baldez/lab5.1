package zula.gui;

import zula.client.ConnectionManager;
import zula.util.Constants;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.ResourceBundle;

public class VisualStyleMain {
    private JFrame mainFrame;
    private final JButton backButton = new JButton("Back to normal");
    private final JPanel northPanel = new JPanel();
    private final JPanel centralPanel = new JPanel();
    private final JPanel southPanel = new JPanel();
    private final ConnectionManager connectionManager;
    private ResourceBundle currentBundle;
    public VisualStyleMain(JFrame mainFrame, ConnectionManager connectionManager, ResourceBundle currentBundle) {
        this.currentBundle = currentBundle;
        this.mainFrame = mainFrame;
        this.connectionManager = connectionManager;
    }
    public void setSettingsForPanels() {
        northPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight/10));
        centralPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight*8/10));
        southPanel.setPreferredSize(new Dimension(Constants.screenWidth, Constants.screenHeight/10));
        northPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        centralPanel.setLayout(new GridLayout());
        southPanel.setLayout(new GridBagLayout());



    }

    public static class CoordinatesDemo extends JPanel {

        private HashMap <Integer, Color> colorsOfUsers = new HashMap<>();

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(Constants.screenWidth / 2, 0, Constants.screenWidth / 2, 100000);
            g2.drawLine(50, Constants.screenHeight * 8 / 10 / 2, 1900, Constants.screenHeight * 8 / 10 / 2);
/*
            if(colorsOfUsers.containsKey())
*/ //todo
            drawDragon(g2, 0, 0, 100, Color.BLACK);





        }
        private void drawDragon(Graphics2D g2, int x, int y, int wingspan, Color color) {
            g2.translate(Constants.screenWidth/2, Constants.screenHeight * 4 / 10);
            g2.drawOval(xCoordinatesFunc(x, wingspan), yCoordinatesFunc(y, wingspan * 2), wingspan, wingspan * 2);
            g2.drawOval(xCoordinatesFunc(x, wingspan/5), yCoordinatesFunc(wingspan/5, wingspan/5), wingspan/5, wingspan/5);
            int[] left = new int[]{x - wingspan * 2, x - wingspan * 3, x - wingspan * 4, x - 0};

            g2.setPaint(color);
            g2.setFont(Constants.mainFont);


            /*g2.drawArc(x, y+wingspan/2, wingspan, wingspan, 90, 180);
            g2.drawArc(x, y-wingspan/2, wingspan, wingspan, 135, -225);
            g2.setStroke(new BasicStroke(2));
            g2.drawArc(x, y-wingspan/2, wingspan/5, wingspan/5, 90, 90);*/

        }
        private int xCoordinatesFunc(int x, int wingspan) {
            return x - wingspan / 2;
        }
        private int yCoordinatesFunc(int y, int wingspan) {
            return -wingspan / 2 - y;
        }
    }

    public void printGraphics() {
        JPanel mainPanel = new JPanel();
        mainPanel.add(northPanel);
        mainPanel.add(centralPanel);
        mainPanel.add(southPanel);
        southPanel.add(backButton);
        backButton.setFont(Constants.mainFont);
        setSettingsForPanels();
        JPanel jPanel = new CoordinatesDemo();
        centralPanel.add(jPanel);

        mainFrame.setContentPane(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreen mainScreen = new MainScreen(connectionManager, mainFrame, currentBundle);
                mainScreen.startMain();
            }
        });

    }







}
