import java.util.Arrays;

public class State implements Comparable<State> {

    public int[] redBalls;
    public int[] blueBalls;
    private static final int[] topTriangle = {
            Graph.map(0, 12),
            Graph.map(1, 11),
            Graph.map(1, 13),
            Graph.map(2, 10),
            Graph.map(2, 12),
            Graph.map(2, 14),
            Graph.map(3, 9),
            Graph.map(3, 11),
            Graph.map(3, 13),
            Graph.map(3, 15)
    };

    private static final int[] downTriangle = {
            Graph.map( 13, 9),
            Graph.map(13,11),
            Graph.map(13,13),
            Graph.map(13,15),
            Graph.map(14,10),
            Graph.map(14,12),
            Graph.map(14,14),
            Graph.map(15,11),
            Graph.map(15,13),
            Graph.map(16,12)
    };

    private int utility;

    State(int[] red, int[] blue)
    {
        this.redBalls = new int[red.length];
        this.blueBalls = new int[blue.length];
        for(int i=0;i<red.length;i++) this.redBalls[i] = red[i];
        for(int i=0;i<blue.length;i++) this.blueBalls[i] = blue[i];
        this.calcUtility();
    }

    State(State s)
    {
        this.redBalls = new int[10];
        this.blueBalls = new int[10];
        StateManager.cpy(this, s);
    }

    public static State getInitialState()
    {
        int[] x = new int[]{0, 1, 1, 2, 2, 2, 3, 3, 3, 3};
        int[] y = new int[]{12,11,13,10,12,14,9,11,13,15};
        int[] red = new int[10];
        int[] blue = new int[10];
        for(int i=0;i<10;i++) {
            red[i] = Graph.map(x[i], y[i]);
        }
        x = new int[]{16, 15, 15, 14, 14, 14, 13, 13, 13, 13};
        for(int i=0;i<10;i++) {
            blue[i] = Graph.map(x[i], y[i]);
        }

        return new State(red, blue);
    }

    public int isWin()
    {
        //AI wins
        if(Utilities.equal(topTriangle, this.blueBalls))
            return 1;

        //Player wins
        if(Utilities.equal(downTriangle, this.redBalls))
            return -1;

        //game still in progress
        return 0;

    }

    int getUtility(){
        return utility;
    }

    void setUtillity(int val){
        utility = val;
    }


    void setRedBalls(int ind, int val){
        this.redBalls[ind] = val;
        this.utility = this.calcUtility();
    }

    void setBlueBalls(int ind, int val){
        this.blueBalls[ind] = val;
        this.utility = this.calcUtility();
    }

    void doMove(int x1,int y1,int x2, int y2)
    {
        int targetNum = Graph.map(x1,y1);
        for(int i=0; i<10; i++)
        {
            if(targetNum == redBalls[i]){
                redBalls[i] = Graph.map(x2,y2);
                break;
            }
        }
    }

    public void printState()
    {
        System.out.println("The player's marbles are in cells:");
        for(int i=0; i<10; i++)
            System.out.println("(" + Graph.getRow(redBalls[i]) + "," + Graph.getCol(redBalls[i]) + ")");
        System.out.println("The AI marbles are in cells:");
        for(int i=0; i<10; i++)
            System.out.println("(" + Graph.getRow(blueBalls[i]) + "," + Graph.getCol(blueBalls[i]) + ")");
    }

    public void print()
    {
        for(int i=0; i<10; i++)
            System.out.println("(" + Graph.getRow(blueBalls[i]) + "," + Graph.getCol(blueBalls[i]) + ")");
    }


    public static int manhattanDistance(int x1, int y1, int x2, int y2)
    {
        return Math.abs(x1-x2)+Math.abs(y1-y2);
    }

    public int calcUtility()
    {
        int blueUtility = 0;
        //destination for AI
        int destRow = 2;
        int destCol = 12;

        for(int i=0; i<10; i++)
        {
            if(!Utilities.find(blueBalls, topTriangle[i]) && !Utilities.find(redBalls, topTriangle[i])){
                destRow = Graph.getRow(topTriangle[i]);
                destCol = Graph.getCol(topTriangle[i]);
                break;
            }
        }

        for(int num:this.blueBalls)
        {
            int row = Graph.getRow(num);
            int col = Graph.getCol(num);
            blueUtility += manhattanDistance(row, col, destRow, destCol);
        }


        int redUtility = 0;
        destRow = 14;
        destCol = 12;

        for(int i=0; i<10; i++)
        {
            if(!Utilities.find(blueBalls, downTriangle[i]) && !Utilities.find(redBalls, downTriangle[i])){
                destRow = Graph.getRow(downTriangle[i]);
                destCol = Graph.getCol(downTriangle[i]);
                break;
            }
        }

        for(int num:this.redBalls)
        {
            int row = Graph.getRow(num);
            int col = Graph.getCol(num);
            redUtility += manhattanDistance(row, col, destRow, destCol);
        }
        return blueUtility;
        //the more positive the more ability to win
        // we subtract the blueUtility because the less distance between the AI and the target the better
        // we add the redUtility because the more distance between the opponent and the target the better
        /*
        int utility = redUtility-blueUtility;

        this.setUtillity(utility);
        return utility;

         */
    }

    @Override
    public int compareTo(State b) {

        Arrays.sort(this.redBalls);
        Arrays.sort(b.blueBalls);
        for(int i = 0; i< this.redBalls.length; i++)
        {
            if(this.redBalls[i] < b.redBalls[i])
            {
                return -1;
            }else if(this.redBalls[i] > b.redBalls[i]){
                return 1;
            }
        }
        for(int i=0;i<this.blueBalls.length;i++)
        {
            if(this.blueBalls[i] < b.blueBalls[i])
            {
                return -1;
            }else if(this.blueBalls[i] > b.blueBalls[i]){
                return 1;
            }
        }
        return 0;
    }
}
