import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class MiniMax
{
    TreeSet<State> visitedList;
    boolean AITurn;

    MiniMax()
    {
        visitedList =  new TreeSet<>();
    }

    //assume AI always plays first


    public State miniMax(State curr, boolean isAi, int depth)
    {
        //todo associate with open and close list to prevent infinite behaviour
        if(depth == 0) return curr;

        ArrayList<State> children = StateManager.getChildren(curr, isAi);
        for(State state:children)
            state.calcUtility();
        State best = curr;
        int bestUtility;
        if(isAi){
            bestUtility = Integer.MIN_VALUE;
            for(State child:children){

                State temp = miniMax(child, !isAi, depth-1);
                if(temp.getUtility() >= bestUtility){
                    bestUtility = temp.getUtility();
                    best = temp;
                }
            }
        }else{
            bestUtility = Integer.MAX_VALUE;
            for(State child:children){
                State temp = miniMax(child, !isAi, depth-1);
                if(temp.getUtility() <= bestUtility){
                    bestUtility = temp.getUtility();
                    best = temp;
                }
            }
        }

        return best;
    }

    public static void tellMe(int[] prev, int [] now)
    {
//        Arrays.sort(prev);
//        Arrays.sort(now);
        for(int i=0; i<10; i++)

        {
            if(prev[i] != now[i])
            {
                int x1 = Graph.getRow(prev[i]);
                int y1 = Graph.getCol(prev[i]);
                int x2 = Graph.getRow(now[i]);
                int y2 = Graph.getCol(now[i]);

                System.out.println("("+ x1 + "," + y1 + ") --> (" + x2 + "," + y2 + ")");

            }
        }
    }

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
        State bestChild = miniMax.miniMax(state, true, 3);
        tellMe(state.blueBalls, bestChild.blueBalls);
        System.out.println();



        State winnn = miniMax.miniMax(new State(bestChild), true, 1);
//        tellMe(bestChild.blueBalls, winnn.blueBalls);
        System.out.println();

    }

}
