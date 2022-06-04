import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GUI extends JFrame implements MouseListener {
    public static int clickCounter = 0;
    public static int[] clicks = {0, 0, 0, 0};
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 680;
    public static final int CIRCLE_SIZE = 40;
    ArrayList<Integer> moves;

    GUI() {
        this.setResizable(false);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        clickCounter = 0;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Point lastClick = new Point(mouseEvent.getX(), mouseEvent.getY());
                Point actualPoint = new Point(invMap(lastClick));
                System.out.println("Clicked on " + actualPoint.x + ", " + actualPoint.y);
                if (Utilities.find(StateManager.curr.redBalls, Graph.map(actualPoint.x, actualPoint.y))) {

                    clicks[0] = actualPoint.x;
                    clicks[1] = actualPoint.y;
                    clickCounter = 1;

                    ArrayList<StateManager.Move> allmoves = GameManager.getPlayerMove();
                    moves = new ArrayList<>();

                    for (StateManager.Move m : allmoves) {
                        if (m.x1 == clicks[0] && m.y1 == clicks[1]) moves.add(Graph.map(m.x2, m.y2));
                    }

                    reDraw();
                } else if (find(moves, Graph.map(actualPoint.x, actualPoint.y))) {

                    clicks[2] = actualPoint.x;
                    clicks[3] = actualPoint.y;
                    clickCounter = 0;

                    StateManager.curr.doMove(clicks[0], clicks[1], clicks[2], clicks[3]);
                    moves = new ArrayList<>();
                    reDraw();


                    // AI plays
                    GameManager.checkwinning();
                    GameManager.AIPlay();
                    reDraw();
                }
            }
        });


    }


    public void reDraw() {
        this.repaint();
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;


        //Draw board
        for (Graph.Node node : Graph.nodes) {
            g.setColor(Color.WHITE);
            if (node == null) continue;
            Point position = new Point(map(Graph.getCol(node.num), Graph.getRow(node.num)));

//            if (Utilities.find(StateManager.curr.redBalls, node.num)) {
//                g.setColor(Color.RED);
//            } else if (Utilities.find(StateManager.curr.blueBalls, node.num)) {
//                g.setColor(Color.BLUE);
//            } else if (find(moves, node.num)) g.setColor(Color.YELLOW);

            g.fillOval(position.x, position.y, CIRCLE_SIZE, CIRCLE_SIZE);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < 10; i++) {
            Point position = new Point(map(Graph.getCol(StateManager.curr.redBalls[i]), Graph.getRow(StateManager.curr.redBalls[i])));
            g.fillOval(position.x, position.y, CIRCLE_SIZE, CIRCLE_SIZE);
        }

        g.setColor(Color.BLUE);
        for (int i = 0; i < 10; i++) {
            Point position = new Point(map(Graph.getCol(StateManager.curr.blueBalls[i]), Graph.getRow(StateManager.curr.blueBalls[i])));
            g.fillOval(position.x, position.y, CIRCLE_SIZE, CIRCLE_SIZE);
        }

        if (moves == null)
            return;

        g.setColor(Color.YELLOW);
        for (Integer m : moves) {
            Point position = new Point(map(Graph.getCol(m), Graph.getRow(m)));
            g.fillOval(position.x, position.y, CIRCLE_SIZE, CIRCLE_SIZE);
        }

    }

    boolean find(ArrayList<Integer> moves, int num) {
        if (moves == null)
            return false;
        for (Integer m : moves) {
            if (m == num)
                return true;
        }
        return false;
    }

    public Point invMap(Point point) {
        int newX = ((point.x / (SCREEN_WIDTH / 25)));
        int newY = (int) (((point.y - 35.0) / 0.95) * (17.0 / SCREEN_HEIGHT));
        return new Point(newY, newX);
    }


    public Point map(int x, int y) {
        int newX = (int) (((SCREEN_WIDTH / 25) * x));
        int newY = (int) (((SCREEN_HEIGHT / 17) * y) * (0.95) + 35);
        return new Point(newX, newY);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {


    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
