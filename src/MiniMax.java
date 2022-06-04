import java.util.ArrayList;
import java.util.TreeSet;

public class MiniMax
{
    TreeSet<State> visitedList;

    MiniMax()
    {
        visitedList =  new TreeSet<>();
    }
    public State miniMax(State curr, boolean isAi, int depth)
    {
        if(depth == 0) return curr;
        ArrayList<State> children = StateManager.getChildren(curr, isAi);
        for(State state:children)
            state.calcUtility();
        State best = curr;
        int bestUtility;
        if(isAi)
        {
            bestUtility = Integer.MIN_VALUE;
            for(State child:children){

                State temp = miniMax(child, !isAi, depth-1);
                if(temp.getUtility() > bestUtility){
                    bestUtility = temp.getUtility();
                    best = child;
                }
            }
        }
        else
        {
            bestUtility = Integer.MAX_VALUE;
            for(State child:children){
                State temp = miniMax(child, !isAi, depth-1);
                if(temp.getUtility() < bestUtility){
                    bestUtility = temp.getUtility();
                    best = child;
                }
            }
        }
        return best;
    }
    /*
    public static void main(String[] args) {
        new Graph();

        int []red = {
                Graph.map(8, 4),
                Graph.map(8, 6),
                Graph.map(8, 8),
                Graph.map(8, 10),
                Graph.map(8, 12),
                Graph.map(9, 15),
                Graph.map(9, 3),
                Graph.map(9, 5),
                Graph.map(9, 7),
                Graph.map(9, 9)
        };
        int []blue = {
                Graph.map(4, 12),
                Graph.map(1, 11),
                Graph.map(1, 13),
                Graph.map(4, 8),
                Graph.map(2, 12),
                Graph.map(2, 14),
                Graph.map(3, 9),
                Graph.map(3, 11),
                Graph.map(3, 13),
                Graph.map(3, 15)
        };

        State state = new State(red, blue);
        StateManager.getChildren(state, true);
        MiniMax miniMax = new MiniMax();

        State bestChild = miniMax.miniMax(state, true, 1);
        Utilities.tellMe(state.blueMarbles, bestChild.blueMarbles);
        System.out.println();

        State winnn = miniMax.miniMax(new State(bestChild), true, 1);
        Utilities.tellMe(bestChild.blueMarbles, winnn.blueMarbles);


        System.out.println();
        winnn.print();
    }
     */

}
