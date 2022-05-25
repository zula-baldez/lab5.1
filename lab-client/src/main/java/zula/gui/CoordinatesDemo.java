package zula.gui;

import zula.common.data.Dragon;
import zula.util.Constants;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CoordinatesDemo extends JComponent implements MouseListener, ActionListener {
    private final CommandExecutor commandExecutor;
    private final HashMap<Integer, Color> usersAndColors = new HashMap<>();
    private final Set<Color> colors = new HashSet<>();
    private List<Dragon> dragonsNeedsToBeShowed;
    private List<RemovingDragon> dragonsNeedsToBeRemoved = new ArrayList<>();
    private List<MovingDragon> dragonsNeedsToBeMoved = new ArrayList<>();
    private List<Dragon> showedDragons = new ArrayList<>();
    private List<Dragon> currentList;
    private final VisualStyleMain visualStyleMain;
    private int alpha = 0;
    private int beta = 0;
    private final Timer timer = new Timer(5, this);
    private final Set<Integer> ids = new HashSet<>();

    public CoordinatesDemo(VisualStyleMain visualStyleMain) {
        this.visualStyleMain = visualStyleMain;
        commandExecutor = new CommandExecutor(visualStyleMain.getConnectionManager(), visualStyleMain.getMainFrame());
        addMouseListener(this);
        dragonsNeedsToBeShowed = commandExecutor.showWithoutParsingToMassive();
        currentList = new ArrayList<>(dragonsNeedsToBeShowed);
        for(Dragon dragon : currentList) {
            ids.add(dragon.getId());
        }
        checkUpdates();

    }


    private static class RemovingDragon {
        Dragon dragon;
        int tic = 60;

        public RemovingDragon(Dragon dragon) {
            this.dragon = dragon;
        }
    }

    private static class MovingDragon {
        int x;
        int y;
        Dragon dragon;
        int wingspan;
        int tic = 60;

        public MovingDragon(int x, int y, int oldWingspan, Dragon dragon) {
            this.x = x;
            this.y = y;
            this.dragon = dragon;
            this.wingspan = oldWingspan;
        }
    }


    public void checkUpdates() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<Dragon> dragons = commandExecutor.showWithoutParsingToMassive();
                for (Dragon oldDragon : currentList) {
                    boolean needsToRemove = true;
                    for (Dragon dragon : dragons) {
                        if (oldDragon.getId() == dragon.getId()) {
                            needsToRemove = false;
                            if (oldDragon.getWingspan() == dragon.getWingspan() &&
                                    oldDragon.getCoordinates().getX() == dragon.getCoordinates().getX() &&
                                    oldDragon.getCoordinates().getY() == dragon.getCoordinates().getY()) {
                                oldDragon = dragon;
                            } else {
                                dragonsNeedsToBeMoved.add(new MovingDragon((int) oldDragon.getCoordinates().getX(), oldDragon.getCoordinates().getY(), (int) oldDragon.getWingspan(), dragon));
                                showedDragons.remove(oldDragon);

                            }
                        }

                    }
                    if (needsToRemove) {
                        dragonsNeedsToBeRemoved.add(new RemovingDragon(oldDragon));
                        showedDragons.remove(oldDragon);
                    }
                }
                for (Dragon newDragon : dragons) {
                    if (!ids.contains(newDragon.getId())) {
                        ids.add(newDragon.getId());
                        dragonsNeedsToBeShowed.add(newDragon);
                    }
                }
                currentList = new ArrayList<>(dragons);
            }
        }, 0, 10, TimeUnit.SECONDS);

    }

    public void startTimer() {
        timer.start();
    }

    @Override
    public void paint(Graphics g) {


        Graphics2D g2 = (Graphics2D) g;
        g2.translate(Constants.screenWidth / 2, Constants.screenHeight * 4 / 10);
        g2.setStroke(new BasicStroke(4));
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(0, -10000, 0, 100000);
        g2.drawLine(-950, 0, 950, 0);
        moveDragon(g2);
        removeDragons(g2);
        showDragons(g2);
        showShowed(g2);

    }

    private void showShowed(Graphics2D g2) {
        for (Dragon showedDragon : showedDragons) {
            drawDragon(g2, (int) showedDragon.getCoordinates().getX(), showedDragon.getCoordinates().getY(), (int) showedDragon.getWingspan(), usersAndColors.get(showedDragon.getOwnerId()), 255);
        }
    }
    private void showDragons(Graphics2D g2) {
        if (dragonsNeedsToBeShowed.size() > 0) {

            Color color;
            Dragon dragon = dragonsNeedsToBeShowed.get(0);
            if (usersAndColors.containsKey(dragon.getOwnerId())) {
                color = usersAndColors.get(dragon.getOwnerId());

            } else {
                while (true) {
                    color = new Color((int) (Math.random() * 0xFFFFFF));
                    if (!colors.contains(color)) {
                        usersAndColors.put(dragon.getOwnerId(), color);
                        break;
                    }
                }
            }
            alpha += 5;
            if (alpha > 255) {
                Dragon showed = dragonsNeedsToBeShowed.remove(0);
                showedDragons.add(showed);
                alpha = 0;
            }
            if (dragonsNeedsToBeShowed.size() == 0) {
                drawDragon(g2, (int) dragon.getCoordinates().getX(), dragon.getCoordinates().getY(), (int) dragon.getWingspan(), color, 255);

            } else {
                dragon = dragonsNeedsToBeShowed.get(0);
                drawDragon(g2, (int) dragon.getCoordinates().getX(), dragon.getCoordinates().getY(), (int) dragon.getWingspan(), color, alpha);
            }
        }
    }

    private void removeDragons(Graphics2D g2) {
        for (RemovingDragon removingDragon : dragonsNeedsToBeRemoved) {
            System.out.println("REMOVE");
            Color color;
            removingDragon.tic--;
            if (removingDragon.tic == 0) {
                dragonsNeedsToBeRemoved.remove(removingDragon);
                return;
            }

            color = usersAndColors.get(removingDragon.dragon.getOwnerId());
            int teta = 255 - 255 * (60 - removingDragon.tic) / 60;

            drawDragon(g2, (int) removingDragon.dragon.getCoordinates().getX(), removingDragon.dragon.getCoordinates().getY(), (int) removingDragon.dragon.getWingspan(), color, teta);

        }
    }
    private void moveDragon(Graphics2D g2) {
        for (MovingDragon movingDragon : dragonsNeedsToBeMoved) {
            if (movingDragon.tic < 0) {
                dragonsNeedsToBeMoved.remove(movingDragon);
                showedDragons.add(movingDragon.dragon);
                return;
            }
            int deltaX = (int) (movingDragon.dragon.getCoordinates().getX() - movingDragon.x) / 60;
            int deltaY = (int) (movingDragon.dragon.getCoordinates().getY() - movingDragon.y) / 60;
            int deltaWingspan = (int) ((movingDragon.dragon.getWingspan() - movingDragon.wingspan) / 60);
            movingDragon.tic--;
            movingDragon.x += deltaX;
            movingDragon.y += deltaY;
            movingDragon.wingspan += deltaWingspan;
            drawDragon(g2, movingDragon.x, movingDragon.y, (int) movingDragon.wingspan, usersAndColors.get(movingDragon.dragon.getOwnerId()), 255);
        }
    }

    private void drawDragon(Graphics2D g2, int x, int y, int wingspan, Color ownerColor, int alpha) {


        Color color = new Color(ownerColor.getRed(), ownerColor.getGreen(), ownerColor.getBlue(), alpha);
        g2.setPaint(color);
        Ellipse2D body = new Ellipse2D.Double(xCoordinatesFunc(x, wingspan), yCoordinatesFunc(y, wingspan * 2), wingspan, wingspan * 2);
        g2.draw(body);
        Ellipse2D head = new Ellipse2D.Double(xCoordinatesFunc(x, wingspan), yCoordinatesFunc(y, wingspan * 2), wingspan, wingspan * 2);
        g2.draw(head);
        g2.drawPolyline(new int[]{x + wingspan / 4, x + wingspan / 2, x + wingspan, x + wingspan, x + wingspan / 4}, new int[]{(int) -(Math.sqrt(3) * wingspan / 2) - y, -wingspan - y, -wingspan / 2 - y, 0 - y, (int) (Math.sqrt(3) * wingspan / 2 - y)}, 5);
        g2.drawPolyline(new int[]{x - wingspan / 4, x - wingspan / 2, x - wingspan, x - wingspan, x - wingspan / 4}, new int[]{(int) -(Math.sqrt(3) * wingspan / 2) - y, -wingspan - y, -wingspan / 2 - y, 0 - y, (int) (Math.sqrt(3) * wingspan / 2 - y)}, 5);
        g2.drawOval(xCoordinatesFunc(x, wingspan / 2), yCoordinatesFunc(y + wingspan * 5 / 4, wingspan / 2), wingspan / 2, wingspan / 2);
        g2.drawOval(xCoordinatesFunc(x + wingspan / 4, wingspan / 2), yCoordinatesFunc(y - wingspan - wingspan / 10, wingspan / 5), wingspan / 2, wingspan / 5);
        g2.drawOval(xCoordinatesFunc(x - wingspan / 4, wingspan / 2), yCoordinatesFunc(y - wingspan - wingspan / 10, wingspan / 5), wingspan / 2, wingspan / 5);


    }

    private int xCoordinatesFunc(int x, int width) {
        return x - width / 2;
    }

    private int yCoordinatesFunc(int y, int height) {
        return -height / 2 - y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("CLICK");


        for (int i = currentList.size() - 1; i >= 0; i--) {
            int x = e.getX() - Constants.screenWidth / 2;
            int y = -e.getY() + Constants.screenHeight * 4 / 10;
            Dragon dragon = currentList.get(i);
            if (x <= dragon.getCoordinates().getX() + dragon.getWingspan() && x >= dragon.getCoordinates().getX() - dragon.getWingspan()
                    && y >= dragon.getCoordinates().getY() - dragon.getWingspan() * 6 / 5 && y <= dragon.getCoordinates().getY() + dragon.getWingspan() * 3 / 2) {

                if (commandExecutor.getConnectionManager().getUserId() == dragon.getOwnerId()) {
                    ChangeFieldsOfDragonPanel changeFieldsOfDragonPanel = new ChangeFieldsOfDragonPanel(visualStyleMain.getConnectionManager(), visualStyleMain.getCurrentBundle(), dragon, visualStyleMain);
                    changeFieldsOfDragonPanel.drawPanel();
                } else {
                    JFrame subFrame = new JFrame();
                    JPanel mainPanel = new JPanel();
                    JLabel jLabel = BasicGUIElementsFabric.createBasicLabel("Это не ваш дракон(");
                    subFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
                    mainPanel.add(jLabel);
                    JButton exitButton = new JButton("OK");
                    exitButton.setFont(Constants.mainFont);
                    exitButton.setAlignmentX(CENTER_ALIGNMENT);
                    JPanel subPanel = new JPanel();
                    subPanel.setLayout(new GridBagLayout());
                    subPanel.add(exitButton);
                    mainPanel.add(subPanel);
                    exitButton.addActionListener(e1 -> subFrame.dispose());
                    subFrame.setContentPane(mainPanel);
                    subFrame.revalidate();
                    subFrame.pack();
                    subFrame.repaint();
                    subFrame.setLocationRelativeTo(null);
                    subFrame.setVisible(true);
                }
                break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //do nothing
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}