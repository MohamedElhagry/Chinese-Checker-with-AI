import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class StateManager
{
    State curr;
    static final int ballsSize = 10;
    static Set<State> open = new TreeSet<>();
    static Set<State> closed  = new TreeSet<>();

    StateManager()
    {
        curr = new State();
        int[] x = new int[]{0, 1, 1, 2, 2, 2, 3, 3, 3, 3};
        int[] y = new int[]{12,11,13,10,12,14,9,11,13,15};
        for(int i=0;i<10;i++) {
            curr.redBalls[i] = GraphForGame.map(x[i], y[i]);
        }
        x = new int[]{16, 15, 15, 14, 14, 14, 13, 13, 13, 13};
        for(int i=0;i<10;i++) {
            curr.blueBalls[i] = GraphForGame.map(x[i], y[i]);
        }
    }

    ArrayList<State> getChildren(State state, boolean isAi)
    {
        State newState = new State();
        ArrayList<State> result = new ArrayList<>();
        cpy(newState, state);
        for(int i=0;i<ballsSize;i++) {
            int nodeNum;
            if(isAi) {
                nodeNum = newState.blueBalls[i];
            }else{
                nodeNum = newState.redBalls[i];
            }
            if(GraphForGame.adjList[nodeNum] != null){
                for (int neighbor : GraphForGame.adjList[nodeNum]) {
                    cpy(newState, state);
                    //make sure if it's a valid state
                    if (!(find(state.blueBalls, neighbor) || find(state.redBalls, neighbor))) {
                        //if (open.contains(state) || closed.contains(state)) continue;
                        if (isAi) {
                            newState.blueBalls[i] = neighbor;
                        } else {
                            newState.redBalls[i] = neighbor;
                        }
                        result.add(new State(newState));
                    }
                }
            }
        }
        return result;
    }

    boolean find(int[] arr, int target)
    {
        for(int i=0;i<arr.length;i++){
            if(arr[i] == target){
                return true;
            }
        }
        return false;
    }

    void cpy(State dest, State src){
        for (int i=0;i<dest.redBalls.length;i++) {
            dest.redBalls[i] = src.redBalls[i];
            dest.blueBalls[i] = src.blueBalls[i];
        }
    }

    public static void main(String[] args)
    {
        new GraphForGame();
        StateManager mg = new StateManager();

        ArrayList<State> allStates = mg.getChildren(mg.curr, false);

        for(State s:allStates){
            System.out.println("red values");
            for(int nodeNum:s.redBalls){
                int x = GraphForGame.getRow(nodeNum);
                int y = GraphForGame.getCol(nodeNum);
                System.out.print("("+x+", "+y+") ");
            }
            System.out.println();
        }

    }

}
