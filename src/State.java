import java.util.Arrays;
import java.util.Comparator;

public class State implements Comparator<State> {

    public int[] redBalls;
    public int[] blueBalls;

    private int utility;

    State(int[] red, int[] blue)
    {
        this.redBalls = new int[red.length];
        this.blueBalls = new int[blue.length];
        for(int i=0;i<red.length;i++) this.redBalls[i] = red[i];
        for(int i=0;i<blue.length;i++) this.blueBalls[i] = blue[i];
        this.calcUtility();
    }

    int getUtility(){
        return utility;
    }

    void setUtillity(int val){
        utility = val;
    }

    State(State s)
    {
        this.redBalls = new int[10];
        this.blueBalls = new int[10];
        StateManager.cpy(this, s);
    }

    void setRedBalls(int ind, int val){
        this.redBalls[ind] = val;
        this.utility = this.calcUtility();
    }

    void setBlueBalls(int ind, int val){
        this.blueBalls[ind] = val;
        this.utility = this.calcUtility();
    }

    public static int manhattanDistance(int x1, int y1, int x2, int y2)
    {
        return Math.abs(x1-x2)+Math.abs(y1-y2);
    }

    public int calcUtility()
    {
        int blueUtility = 0;
        //destination for AI
        int destX = 0, destY = 12;
        for(int num:this.blueBalls)
        {
            int x = GraphForGame.getRow(num);
            int y = GraphForGame.getCol(num);
            blueUtility += manhattanDistance(x, y, destX, destY);
        }
        int redUtility = 0;
        destX = 16;
        for(int num:this.redBalls)
        {
            int x = GraphForGame.getRow(num);
            int y = GraphForGame.getCol(num);
            redUtility += manhattanDistance(x, y, destX, destY);
        }

        //System.out.println("blue ut = "+blueUtility);
        //System.out.println("red ut = "+redUtility);
        //the more positive the more ability to win
        int utility = redUtility-blueUtility;
        this.setUtillity(utility);
        return utility;
    }

    @Override
    public int compare(State a, State b)
    {
        Arrays.sort(a.redBalls);
        Arrays.sort(b.blueBalls);
        for(int i = 0; i< a.redBalls.length; i++)
        {
            if(a.redBalls[i] < b.redBalls[i])
            {
                return -1;
            }else if(a.redBalls[i] > b.redBalls[i]){
                return 1;
            }
        }
        for(int i=0;i<a.blueBalls.length;i++)
        {
            if(a.blueBalls[i] < b.blueBalls[i])
            {
                return -1;
            }else if(a.blueBalls[i] > b.blueBalls[i]){
                return 1;
            }
        }
        return 0;
    }



}
