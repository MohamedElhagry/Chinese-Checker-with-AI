import java.util.Arrays;

public class State implements Comparable<State> {

    public int[] redMarbles;
    public int[] blueMarbles;
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
            Graph.map(13, 9),
            Graph.map(13, 11),
            Graph.map(13, 13),
            Graph.map(13, 15),
            Graph.map(14, 10),
            Graph.map(14, 12),
            Graph.map(14, 14),
            Graph.map(15, 11),
            Graph.map(15, 13),
            Graph.map(16, 12)
    };
    private int utility;
    State(int[] red, int[] blue) {
        this.redMarbles = new int[red.length];
        this.blueMarbles = new int[blue.length];
        for (int i = 0; i < red.length; i++) this.redMarbles[i] = red[i];
        for (int i = 0; i < blue.length; i++) this.blueMarbles[i] = blue[i];
        this.calcUtility();
    }

    State(State s) {
        this.redMarbles = new int[10];
        this.blueMarbles = new int[10];
        StateManager.cpy(this, s);
    }

    public static State getInitialState() {
        int[] x = new int[]{0, 1, 1, 2, 2, 2, 3, 3, 3, 3};
        int[] y = new int[]{12, 11, 13, 10, 12, 14, 9, 11, 13, 15};
        int[] red = new int[10];
        int[] blue = new int[10];
        for (int i = 0; i < 10; i++) {
            red[i] = Graph.map(x[i], y[i]);
        }
        x = new int[]{16, 15, 15, 14, 14, 14, 13, 13, 13, 13};
        for (int i = 0; i < 10; i++) {
            blue[i] = Graph.map(x[i], y[i]);
        }

        return new State(red, blue);
    }

    public int isWin() {
        //AI wins
        if (Utilities.equal(topTriangle, this.blueMarbles))
            return 1;

        //Player wins
        if (Utilities.equal(downTriangle, this.redMarbles))
            return -1;

        //game still in progress
        return 0;

    }
    int getUtility() {
        return utility;
    }

    void setUtillity(int val) {
        utility = val;
    }


    void setRedMarbles(int ind, int val) {
        this.redMarbles[ind] = val;
        this.utility = this.calcUtility();
    }

    void setBlueMarbles(int ind, int val) {
        this.blueMarbles[ind] = val;
        this.utility = this.calcUtility();
    }

    void doMove(int x1, int y1, int x2, int y2) {
        int targetNum = Graph.map(x1, y1);
        for (int i = 0; i < 10; i++) {
            if (targetNum == redMarbles[i]) {
                redMarbles[i] = Graph.map(x2, y2);
                break;
            }
        }
    }

    public void printState() {
        System.out.println("The player's marbles are in cells:");
        for (int i = 0; i < 10; i++)
            System.out.println("(" + Graph.getRow(redMarbles[i]) + "," + Graph.getCol(redMarbles[i]) + ")");
        System.out.println("The AI marbles are in cells:");
        for (int i = 0; i < 10; i++)
            System.out.println("(" + Graph.getRow(blueMarbles[i]) + "," + Graph.getCol(blueMarbles[i]) + ")");
    }

    public void print() {
        for (int i = 0; i < 10; i++)
            System.out.println("(" + Graph.getRow(blueMarbles[i]) + "," + Graph.getCol(blueMarbles[i]) + ")");
    }

    public int calcUtility() {
        int blueUtility = 0;
        //destination for AI
        int destRow = 0;
        int destCol = 12;


        for (int i = 0; i < 10; i++) {
            if (!Utilities.find(blueMarbles, topTriangle[i]) && !Utilities.find(redMarbles, topTriangle[i])) {
                destRow = Graph.getRow(topTriangle[i]);
                destCol = Graph.getCol(topTriangle[i]);
                break;
            }
        }



        for (int num : this.blueMarbles) {
            int row = Graph.getRow(num);
            int col = Graph.getCol(num);
            if(Utilities.find(topTriangle,num))
                blueUtility -= 100;
            blueUtility += Utilities.manhattanDistance(row,col,destRow,destCol) + Math.abs(row-destRow);
        }


        int redUtility = 0;
        destRow = 16;
        destCol = 12;

        for (int i = 9; i >= 0; i--) {
            if (!Utilities.find(blueMarbles, downTriangle[i]) && !Utilities.find(redMarbles, downTriangle[i])) {
                destRow = Graph.getRow(downTriangle[i]);
                destCol = Graph.getCol(downTriangle[i]);
                break;
            }
        }
        for (int num : this.redMarbles) {
            int row = Graph.getRow(num);
            int col = Graph.getCol(num);
            if(Utilities.find(downTriangle,num))
                redUtility -= 100;
            redUtility += Utilities.manhattanDistance(row,col,destRow,destCol) + Math.abs(row-destRow);
        }
        //the more positive the more ability to win
        // we subtract the blueUtility because the less distance between the AI and the target the better
        // we add the redUtility because the more distance between the opponent and the target the better

        int utility = redUtility-blueUtility;
        this.setUtillity(utility);
        return utility;
    }

    @Override
    public int compareTo(State b) {

        Arrays.sort(this.redMarbles);
        Arrays.sort(b.blueMarbles);
        for (int i = 0; i < this.redMarbles.length; i++) {
            if (this.redMarbles[i] < b.redMarbles[i]) {
                return -1;
            } else if (this.redMarbles[i] > b.redMarbles[i]) {
                return 1;
            }
        }
        for (int i = 0; i < this.blueMarbles.length; i++) {
            if (this.blueMarbles[i] < b.blueMarbles[i]) {
                return -1;
            } else if (this.blueMarbles[i] > b.blueMarbles[i]) {
                return 1;
            }
        }
        return 0;
    }
}
