import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CountDownLatch;

public class GUI extends JFrame implements MouseListener
{
    public static int clickCounter = 0;
    public static boolean guiPlayerFinished = false;
    public static CountDownLatch latch = new CountDownLatch(1);
    public static int[] clicks = {0,0,0,0};
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 680;
    public static final int CIRCLE_SIZE = 40;

    GUI()
    {
        this.setResizable(false);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        clickCounter = 0;
        guiPlayerFinished =false;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Point lastClick = new Point(mouseEvent.getX(), mouseEvent.getY());
                Point actualPoint = new Point(invMap(lastClick));
                System.out.println("Clicked on "+actualPoint.x+", "+actualPoint.y);
                if(clickCounter == 0)
                {
                    if (latch == null) latch = new CountDownLatch(1);
                    guiPlayerFinished = false;
                    clicks[0] = actualPoint.x;
                    clicks[1] = actualPoint.y;
                    clickCounter = 1;
                }
                else
                {
                    clicks[2] = actualPoint.x;
                    clicks[3] = actualPoint.y;
                    clickCounter = 0;
                }


            }
        });


    }

    public void reDraw()
    {
        this.repaint();
    }


    public void paint (Graphics g)
    {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;


        //Draw board
        for (Graph.Node node: Graph.nodes)
        {
            g.setColor(Color.WHITE);
            if(node == null) continue;
            Point position = new Point(map(Graph.getCol(node.num), Graph.getRow(node.num)));
            if(Utilities.find(StateManager.curr.redBalls, node.num))
                g.setColor(Color.RED);
            else if(Utilities.find(StateManager.curr.blueBalls, node.num))
                g.setColor(Color.BLUE);

            g.fillOval(position.x,position.y,CIRCLE_SIZE,CIRCLE_SIZE);
        }
    }


    public Point invMap(Point point)
    {
        int newX = ((point.x / (SCREEN_WIDTH / 25)) );
        int newY = (int) (((point.y - 35.0)/0.95) * (17.0/SCREEN_HEIGHT));
        return new Point(newY, newX);
    }


    public Point map(int x, int y)
    {
        int newX = (int) (((SCREEN_WIDTH / 25) * x) );
        int newY = (int) (((SCREEN_HEIGHT / 17) * y) * (0.95)+35);
        return new Point(newX, newY);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {



    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent)
    {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent)
    {

    }
}
