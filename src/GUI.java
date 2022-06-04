import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI extends JFrame implements MouseListener
{
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



    }

    public void paint (Graphics g)
    {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;


        //Draw board
        for (GraphForGame.Node node: GraphForGame.nodes)
        {
            g.setColor(Color.WHITE);
            if(node == null) continue;
            Point position = new Point(map(GraphForGame.getCol(node.num), GraphForGame.getRow(node.num)));
            if(StateManager.find(StateManager.curr.redBalls, node.num))
                g.setColor(Color.RED);
            else if(StateManager.find(StateManager.curr.blueBalls, node.num))
                g.setColor(Color.BLUE);

            g.fillOval(position.x,position.y,CIRCLE_SIZE,CIRCLE_SIZE);
        }
    }


    public Point invMap(Point point)
    {
        return new Point(0,0);
    }


    public Point map(int x, int y)
    {
        int newX = (int) (((SCREEN_WIDTH / 25) * x) * (3.0/2.0));
        int newY = (int) (((SCREEN_HEIGHT / 17) * y) * (1.8/3.0)) + 40;

        System.out.println("("+x+", "+y+") ==> " + "("+newX+", "+newY+")" );

        return new Point(newX, newY);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {

        Point lastClick = new Point(mouseEvent.getX(), mouseEvent.getY());
        Point actualPoint = new Point(invMap(lastClick));
        //TODO validate move with actual point
        //TODO call move function

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
