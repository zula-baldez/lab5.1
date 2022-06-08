package zula.gui.views;

import zula.common.data.Dragon;
import zula.util.BasicGUIElementsFabric;
import zula.util.CommandExecutor;
import zula.util.Constants;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
отрисовщик координатной плоскости
 */
public class CoordinatesDemo extends JComponent implements MouseListener, ActionListener {
    private static final int MAX_ALPHA = 255;
    private static final int DELTA_ALPHA_PER_TIC = 5;
    private static final int COUNTER_MAX = 30;
    private static final int AMOUNT_OF_PARTS = 10; //делим panel на 10 частей
    private static final int HEIGHT_OF_LEG_TO_WINGSPAN = 10; //размер лапы 1/10 wingspan
    private static final int WIDTH_OF_LEG_TO_WINGSPAN = 5;
    private static final int AMOUNT_OD_POINTS = 5;
    private static final int DELTA_X_TO_WINGSPAN = 4;
    private static final double HEAD_HEIGHT_TO_WINGSPAN = 5 / 4.0;
    private static final int MAX_COLOR_VALUE = 0xFFFFFF;
    private static final int BASIC_STROKE = 4;
    private static final int AMOUNT_OF_PART_TO_CENTER_HALF = 4;
    private static final int Y_FIRST_POINT_NUMERATOR = 3;
    private static final double HITBOX_LOW_POINT = 6 / 5.0; //Я попытался структурировать константы, но большинство из них это просто значения, которые я получил из расчетов
    private final CommandExecutor commandExecutor;
    private final HashMap<Integer, Color> usersAndColors = new HashMap<>();
    private final Set<Color> colors = new HashSet<>();
    private final List<Dragon> dragonsNeedsToBeShowed; //Для анимации появления
    private final List<RemovingDragon> dragonsNeedsToBeRemoved = new ArrayList<>(); //Для анимации удаления
    private final List<MovingDragon> dragonsNeedsToBeMoved = new ArrayList<>(); //Для анимации перемещения
    private final List<Dragon> showedDragons = new ArrayList<>(); //Просто чтобы отрисовывать уже появившихся драконов
    private List<Dragon> currentList;
    private final VisualStyleMain visualStyleMain;
    private int alpha = 0;
    private final int frequencyOfUpdateConst = 300;
    private int frequencyOfUpdate = frequencyOfUpdateConst; //Можно создать новый поток, но тогда придется создавать новый ConnectionManager, так как нельзя работать с PipedStream'ами с двух потоков
    private final Timer timer = new Timer(15, this);
    private final Set<Integer> ids = new HashSet<>();

    public CoordinatesDemo(VisualStyleMain visualStyleMain) throws IOException {
        this.visualStyleMain = visualStyleMain;
        commandExecutor = new CommandExecutor(visualStyleMain.getConnectionManager(), visualStyleMain.getMainFrame());
        addMouseListener(this);
        dragonsNeedsToBeShowed = commandExecutor.showWithoutParsingToMassive();
        if (dragonsNeedsToBeShowed == null) {
            throw new IOException();
        }
        currentList = new ArrayList<>(dragonsNeedsToBeShowed);
        for (Dragon dragon : currentList) {
            ids.add(dragon.getId());
        }

    }


    public void checkUpdates() throws IOException {

        List<Dragon> dragons = commandExecutor.showWithoutParsingToMassive();
        if (dragons == null) {
            visualStyleMain.errorHandler("SERVER UMER");
            throw new IOException();
        }
        for (Dragon oldDragon : currentList) {
            boolean needsToRemove = true;
            for (Dragon dragon : dragons) {
                if (oldDragon.getId() == dragon.getId()) {
                    needsToRemove = false;
                    if (oldDragon.getWingspan() == dragon.getWingspan()
                            && oldDragon.getCoordinates().getX() == dragon.getCoordinates().getX()
                            && oldDragon.getCoordinates().getY() == dragon.getCoordinates().getY()) {
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


    public void startTimer() {
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT * AMOUNT_OF_PART_TO_CENTER_HALF / AMOUNT_OF_PARTS);
        g2.setStroke(new BasicStroke(BASIC_STROKE));
        g2.setStroke(new BasicStroke(BASIC_STROKE));
        g2.drawLine(0, -Constants.SCREEN_HEIGHT, 0, Constants.SCREEN_HEIGHT);
        g2.drawLine(-Constants.SCREEN_WIDTH, 0, Constants.SCREEN_WIDTH, 0);
        frequencyOfUpdate--;
        if (frequencyOfUpdate == 0) {
            try {
                checkUpdates();
            } catch (IOException e) {
                timer.stop();
            }
            frequencyOfUpdate = frequencyOfUpdateConst;
        }
        moveDragon(g2);
        removeDragons(g2);
        showDragons(g2);
        showShowed(g2);

    }


    private synchronized void showShowed(Graphics2D g2) {
        for (Dragon showedDragon : showedDragons) {
            drawDragon(g2, (int) showedDragon.getCoordinates().getX(), showedDragon.getCoordinates().getY(), (int) showedDragon.getWingspan(), usersAndColors.get(showedDragon.getOwnerId()), MAX_ALPHA);
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
                    color = new Color((int) (Math.random() * MAX_COLOR_VALUE));
                    if (!colors.contains(color)) {
                        usersAndColors.put(dragon.getOwnerId(), color);
                        break;
                    }
                }
            }
            alpha += DELTA_ALPHA_PER_TIC;
            if (alpha > MAX_ALPHA) {
                Dragon showed = dragonsNeedsToBeShowed.remove(0);
                showedDragons.add(showed);
                alpha = 0;
            }
            if (dragonsNeedsToBeShowed.size() == 0) {
                drawDragon(g2, (int) dragon.getCoordinates().getX(), dragon.getCoordinates().getY(), (int) dragon.getWingspan(), color, MAX_ALPHA);

            } else {
                dragon = dragonsNeedsToBeShowed.get(0);
                drawDragon(g2, (int) dragon.getCoordinates().getX(), dragon.getCoordinates().getY(), (int) dragon.getWingspan(), color, alpha);
            }
        }
    }

    private void removeDragons(Graphics2D g2) {
        for (RemovingDragon removingDragon : dragonsNeedsToBeRemoved) {
            Color color;
            removingDragon.tic--;
            if (removingDragon.tic == 0) {
                dragonsNeedsToBeRemoved.remove(removingDragon);
                return;
            }

            color = usersAndColors.get(removingDragon.dragon.getOwnerId());
            int teta = MAX_ALPHA - MAX_ALPHA * (COUNTER_MAX - removingDragon.tic) / COUNTER_MAX;

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
            movingDragon.tic--;
            movingDragon.x += movingDragon.deltaX;
            movingDragon.y += movingDragon.deltaY;
            movingDragon.wingspan += movingDragon.deltaWingspan;
            drawDragon(g2, (int) movingDragon.x, (int) movingDragon.y, (int) movingDragon.wingspan, usersAndColors.get(movingDragon.dragon.getOwnerId()), MAX_ALPHA);
        }
    }

    private void drawDragon(Graphics2D g2, int x, int y, int wingspan, Color ownerColor, int alph) {


        Color color = new Color(ownerColor.getRed(), ownerColor.getGreen(), ownerColor.getBlue(), alph);
        g2.setPaint(color);
        Ellipse2D body = new Ellipse2D.Double(xCoordinatesFunc(x, wingspan), yCoordinatesFunc(y, wingspan * 2), wingspan, wingspan * 2);
        g2.draw(body);
        Ellipse2D head = new Ellipse2D.Double(xCoordinatesFunc(x, wingspan), yCoordinatesFunc(y, wingspan * 2), wingspan, wingspan * 2);
        g2.draw(head);
        g2.drawPolyline(new int[]{x + wingspan / DELTA_X_TO_WINGSPAN, x + wingspan / 2, x + wingspan, x + wingspan, x + wingspan / DELTA_X_TO_WINGSPAN}, new int[]{(int) -(Math.sqrt(Y_FIRST_POINT_NUMERATOR) * wingspan / 2) - y, -wingspan - y, -wingspan / 2 - y, -y, (int) (Math.sqrt(Y_FIRST_POINT_NUMERATOR) * wingspan / 2 - y)}, AMOUNT_OD_POINTS);
        g2.drawPolyline(new int[]{x - wingspan / DELTA_X_TO_WINGSPAN, x - wingspan / 2, x - wingspan, x - wingspan, x - wingspan / DELTA_X_TO_WINGSPAN}, new int[]{(int) -(Math.sqrt(Y_FIRST_POINT_NUMERATOR) * wingspan / 2) - y, -wingspan - y, -wingspan / 2 - y, -y, (int) (Math.sqrt(Y_FIRST_POINT_NUMERATOR) * wingspan / 2 - y)}, AMOUNT_OD_POINTS);
        g2.drawOval(xCoordinatesFunc(x, wingspan / 2), yCoordinatesFunc((int) (y + wingspan * HEAD_HEIGHT_TO_WINGSPAN), wingspan / 2), wingspan / 2, wingspan / 2);
        g2.drawOval(xCoordinatesFunc(x + wingspan / DELTA_X_TO_WINGSPAN, wingspan / 2), yCoordinatesFunc(y - wingspan - wingspan / HEIGHT_OF_LEG_TO_WINGSPAN, wingspan / WIDTH_OF_LEG_TO_WINGSPAN), wingspan / 2, wingspan / WIDTH_OF_LEG_TO_WINGSPAN);
        g2.drawOval(xCoordinatesFunc(x - wingspan / DELTA_X_TO_WINGSPAN, wingspan / 2), yCoordinatesFunc(y - wingspan - wingspan / HEIGHT_OF_LEG_TO_WINGSPAN, wingspan / WIDTH_OF_LEG_TO_WINGSPAN), wingspan / 2, wingspan / WIDTH_OF_LEG_TO_WINGSPAN);


    }

    private int xCoordinatesFunc(int x, int width) {
        return x - width / 2;
    }

    private int yCoordinatesFunc(int y, int height) {
        return -height / 2 - y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {


        for (int i = currentList.size() - 1; i >= 0; i--) {
            int x = e.getX() - Constants.SCREEN_WIDTH / 2;
            int y = -e.getY() + Constants.SCREEN_HEIGHT * AMOUNT_OF_PART_TO_CENTER_HALF / AMOUNT_OF_PARTS;
            Dragon dragon = currentList.get(i);
            if (x <= dragon.getCoordinates().getX() + dragon.getWingspan() && x >= dragon.getCoordinates().getX() - dragon.getWingspan()
                    && y >= dragon.getCoordinates().getY() - dragon.getWingspan() * HITBOX_LOW_POINT && y <= dragon.getCoordinates().getY() + dragon.getWingspan() * Y_FIRST_POINT_NUMERATOR / 2) {

                if (commandExecutor.getConnectionManager().getUserId() == dragon.getOwnerId()) {
                    ChangeFieldsOfDragonPanel changeFieldsOfDragonPanel = new ChangeFieldsOfDragonPanel(visualStyleMain.getConnectionManager(), visualStyleMain.getCurrentBundle(), dragon, null);
                    changeFieldsOfDragonPanel.drawPanel();
                } else {
                    JFrame subFrame = new JFrame();
                    JPanel mainPanel = new JPanel();
                    JLabel jLabel = BasicGUIElementsFabric.createBasicLabel(visualStyleMain.getCurrentBundle().getString("It is not your dragon"));
                    subFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
                    mainPanel.add(jLabel);
                    JButton exitButton = BasicGUIElementsFabric.createBasicButton(visualStyleMain.getCurrentBundle().getString("OK"));
                    exitButton.setAlignmentX(CENTER_ALIGNMENT);
                    JPanel subPanel = new JPanel();
                    subPanel.setLayout(new GridBagLayout());
                    subPanel.add(exitButton);
                    mainPanel.add(subPanel);
                    exitButton.addActionListener(e1 -> subFrame.dispose());
                    subFrame.setContentPane(mainPanel);
                    subFrame.revalidate();
                    subFrame.pack();
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

    private static class RemovingDragon {
        private Dragon dragon;
        private int tic = COUNTER_MAX;

        RemovingDragon(Dragon dragon) {
            this.dragon = dragon;
        }
    }

    private static class MovingDragon {
        private double x;
        private double y;
        private final Dragon dragon;
        private double wingspan;
        private int tic = COUNTER_MAX;
        private final double deltaX;
        private final double deltaY;
        private final double deltaWingspan;

        MovingDragon(int x, int y, int oldWingspan, Dragon dragon) {
            this.x = x;
            this.y = y;
            this.dragon = dragon;
            this.wingspan = oldWingspan;
            this.deltaX = ((dragon.getCoordinates().getX() - x) / COUNTER_MAX);
            this.deltaY = (dragon.getCoordinates().getY() - y) * 1.0 / COUNTER_MAX;
            this.deltaWingspan = ((dragon.getWingspan() - wingspan) / COUNTER_MAX);
        }
    }

}
