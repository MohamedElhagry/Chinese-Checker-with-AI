import java.lang.reflect.GenericArrayType;
import java.util.*;

public class StateManager
{
    private static State curr;
    private static final int ballsSize = 10;
    public static Set<State> open = new TreeSet<>();
    public static Set<State> closed  = new TreeSet<>();

    StateManager()
    {
        int[] x = new int[]{0, 1, 1, 2, 2, 2, 3, 3, 3, 3};
        int[] y = new int[]{12,11,13,10,12,14,9,11,13,15};
        int[] red = new int[10];
        int[] blue = new int[10];
        for(int i=0;i<10;i++) {
            red[i] = GraphForGame.map(x[i], y[i]);
        }
        x = new int[]{16, 15, 15, 14, 14, 14, 13, 13, 13, 13};
        for(int i=0;i<10;i++) {
            blue[i] = GraphForGame.map(x[i], y[i]);
        }
        curr = new State(red, blue);
    }

    void setState(State newState)
    {
        curr = newState;
    }

    State getState()
    {
        return curr;
    }

    public static ArrayList<State> getChildren(State state, boolean isAi)
    {
        State newState = new State(state);
        ArrayList<State> result = new ArrayList<>();
        for(int i=0;i<ballsSize;i++) {

            int startingNode;
            if(isAi) {
                startingNode = newState.blueBalls[i];
            }else{
                startingNode = newState.redBalls[i];
            }

            ArrayList<Integer> possibleCells = new ArrayList<Integer>();
            if (GraphForGame.adjList[startingNode] != null) {
                for (int neighbor : GraphForGame.adjList[startingNode]) {
                    cpy(newState, state);
                    //make sure if it's a valid state
                    if (!(find(state.blueBalls, neighbor) || find(state.redBalls, neighbor))) {
                        //if (open.contains(state) || closed.contains(state)) continue;
                        if (isAi) {
                            newState.blueBalls[i] = neighbor;
                        } else {
                            newState.redBalls[i] = neighbor;
                        }
                        possibleCells.add(neighbor);
                    }
                }
            }

            //BFS to find all possible moves
            boolean[] visitedCells = new boolean[426];
            Queue<Integer> queue = new LinkedList<Integer>();
            queue.add(startingNode);
            visitedCells[startingNode] = true;

            while(!queue.isEmpty()) {
                int currNode = queue.poll();

                int currX = GraphForGame.getRow(currNode);
                int currY = GraphForGame.getCol(currNode);

                for (int neighbor : GraphForGame.adjList[currNode]) {
                    if (!(find(state.blueBalls, neighbor) || find(state.redBalls, neighbor)))
                        continue;

                    int x = GraphForGame.getRow(neighbor);
                    int y = GraphForGame.getCol(neighbor);
                    int xDiff = x - currX;
                    int yDiff = y - currY;
                    int newCell = GraphForGame.map(currX + 2*xDiff, currY + 2*yDiff);
                    if(!GraphForGame.valid(newCell) || (find(state.blueBalls, newCell) || find(state.redBalls, newCell))  || GraphForGame.nodes[newCell] == null ||  visitedCells[newCell])
                        continue;

                    visitedCells[newCell] = true;
                    queue.add(newCell);
                    possibleCells.add(newCell);
                }
            }

            for(int cell:possibleCells)
            {
                cpy(newState, state);
                if(isAi) {
                    newState.blueBalls[i] = cell;
                }else{
                    newState.redBalls[i] = cell;
                }
                result.add(new State(newState));
            }


        }
        return result;
    }

    public static boolean find(int[] arr, int target)
    {
        for(int i=0;i<arr.length;i++){
            if(arr[i] == target){
                return true;
            }
        }
        return false;
    }

    public static void cpy(State dest, State src){
        for (int i=0;i<dest.redBalls.length;i++) {
            dest.redBalls[i] = src.redBalls[i];
            dest.blueBalls[i] = src.blueBalls[i];
        }
        dest.setUtillity(src.getUtility());
    }

    public static void main(String[] args)
    {
        new GraphForGame();
        StateManager mg = new StateManager();

        mg.curr.setRedBalls(4, GraphForGame.map(5,5));
        mg.curr.setRedBalls(6, GraphForGame.map(4,18));

        mg.curr.setBlueBalls(4, GraphForGame.map(5,7));
        mg.curr.setBlueBalls(6, GraphForGame.map(6,10));
        mg.curr.setBlueBalls(7, GraphForGame.map(7,17));

        System.out.println(mg.curr.getUtility());

        //ArrayList<State> allStates = mg.getChildren(mg.curr, true);



















        /*

        for(int nodeNum:mg.curr.blueBalls){
            int x = GraphForGame.getRow(nodeNum);
            int y = GraphForGame.getCol(nodeNum);
            System.out.print("("+x+", "+y+") ");
        }
        System.out.println();


        for(State s:allStates){
            System.out.println("red values");
            for(int nodeNum:s.blueBalls){
                int x = GraphForGame.getRow(nodeNum);
                int y = GraphForGame.getCol(nodeNum);
                System.out.print("("+x+", "+y+") ");
            }
            System.out.println();
        }
         */

    }

}
