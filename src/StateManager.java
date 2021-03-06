import java.util.*;

public class StateManager
{
    public static State curr;
    private static final int ballsSize = 10;

    StateManager()
    {
        curr = State.getInitialState();
    }

    void setState(State newState)
    {
        curr = newState;
    }

    State getState()
    {
        return curr;
    }

    static class Move {
        int x1, y1, x2, y2;
        Move(int x1, int y1, int x2, int y2)
        {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        public void print(){
            System.out.println("Possible Move : ("+ x1 + "," + y1 + ") --> (" +  x2 + "," +y2 + ")");
        }
    }

    public static ArrayList<State> getChildren(State state, boolean isAi)
    {
        State newState = new State(state);
        ArrayList<State> result = new ArrayList<>();

        for(int i=0;i<ballsSize;i++) {

            int startingNode;
            if(isAi) {
                startingNode = newState.blueMarbles[i];
            }else{
                startingNode = newState.redMarbles[i];
            }

            ArrayList<Integer> possibleCells = new ArrayList<Integer>();
            if (Graph.adjList[startingNode] != null) {
                for (int neighbor : Graph.adjList[startingNode]) {
                    //make sure if it's a valid state
                    if (!(Utilities.find(state.blueMarbles, neighbor) || Utilities.find(state.redMarbles, neighbor))) {
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

                int currX = Graph.getRow(currNode);
                int currY = Graph.getCol(currNode);

                for (int neighbor : Graph.adjList[currNode]) {
                    if (!(Utilities.find(state.blueMarbles, neighbor) || Utilities.find(state.redMarbles, neighbor)))
                        continue;

                    int x = Graph.getRow(neighbor);
                    int y = Graph.getCol(neighbor);
                    int xDiff = x - currX;
                    int yDiff = y - currY;
                    int newCell = Graph.map(currX + 2*xDiff, currY + 2*yDiff);
                    if(!Graph.valid(newCell) || (Utilities.find(state.blueMarbles, newCell) || Utilities.find(state.redMarbles, newCell))  || Graph.nodes[newCell] == null ||  visitedCells[newCell])
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
                    newState.blueMarbles[i] = cell;
                }else{
                    newState.redMarbles[i] = cell;
                }
                result.add(new State(newState));
            }


        }
        return result;
    }

    public static ArrayList<Move> getValidMoves(State state, boolean isAi)
    {
        State newState = new State(state);
        ArrayList<Move> result = new ArrayList<>();

        for(int i=0;i<ballsSize;i++) {

            int startingNode;
            if(isAi) {
                startingNode = newState.blueMarbles[i];
            }else{
                startingNode = newState.redMarbles[i];
            }

            if (Graph.adjList[startingNode] != null) {
                int sourceRow = Graph.getRow(startingNode);
                int sourceCol = Graph.getCol(startingNode);
                for (int neighbor : Graph.adjList[startingNode]) {
                    int destRow = Graph.getRow(neighbor);
                    int destCol = Graph.getCol(neighbor);
                    cpy(newState, state);
                    //make sure if it's a valid state
                    if (!(Utilities.find(state.blueMarbles, neighbor) || Utilities.find(state.redMarbles, neighbor))) {

                        result.add(new Move(sourceRow, sourceCol, destRow, destCol));
                    }
                }
            }

            //BFS to find all possible moves
            boolean[] visitedCells = new boolean[426];
            Queue<Integer> queue = new LinkedList<Integer>();
            queue.add(startingNode);
            visitedCells[startingNode] = true;
            int startRow = Graph.getRow(startingNode);
            int startCol = Graph.getCol(startingNode);

            while(!queue.isEmpty()) {
                int currNode = queue.poll();

                int currRow = Graph.getRow(currNode);
                int currCol = Graph.getCol(currNode);

                for (int neighbor : Graph.adjList[currNode]) {
                    if (!(Utilities.find(state.blueMarbles, neighbor) || Utilities.find(state.redMarbles, neighbor)))
                        continue;

                    int x = Graph.getRow(neighbor);
                    int y = Graph.getCol(neighbor);
                    int xDiff = x - currRow;
                    int yDiff = y - currCol;
                    int destRow = currRow + 2*xDiff;
                    int destCol = currCol + 2*yDiff;

                    int newCell = Graph.map(destRow, destCol);
                    if(!Graph.valid(newCell) || (Utilities.find(state.blueMarbles, newCell) || Utilities.find(state.redMarbles, newCell))  || Graph.nodes[newCell] == null ||  visitedCells[newCell])
                        continue;

                    visitedCells[newCell] = true;
                    queue.add(newCell);
                    result.add(new Move(startRow, startCol, destRow, destCol));
                }
            }

        }
        return result;
    }

    public static void cpy(State dest, State src){
        for (int i = 0; i<dest.redMarbles.length; i++) {
            dest.redMarbles[i] = src.redMarbles[i];
            dest.blueMarbles[i] = src.blueMarbles[i];
        }
        dest.setUtillity(src.getUtility());
    }

    /*
    public static void main(String[] args)

    {
        new Graph();
        StateManager mg = new StateManager();

        mg.curr.redMarbles =
                new int[]{
                        Graph.map(0, 12),
                        Graph.map(1, 11),
                        Graph.map(1, 13),
                        Graph.map(2, 10),
                        Graph.map(2, 12),
                        Graph.map(2, 14),
                        Graph.map(3, 13),
                        Graph.map(3, 15),
                        Graph.map(4, 8),
                        Graph.map(4, 10)
                };

        ArrayList<Move> moves = mg.getValidMoves(mg.curr, true);
        for(Move move:moves)
        {
            System.out.println("("+ move.x1 + "," + move.y1 + ") --> (" +  move.x2 + "," + move.y2 + ")");
        }
    }

     */

}
