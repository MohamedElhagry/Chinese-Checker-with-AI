import java.util.ArrayList;

enum nodeState {
    Player, AI, Empty
}

public class Graph {
    public class Node {
        nodeState state;
        int x, y;
        int num;
        Node(int x, int y, nodeState state) {
            this.x = x;
            this.y = y;
            num = map(x, y);
        }
        void print() {
            System.out.println(state.toString() + " " + x + " " + y);
        }
    }

    static int numOfCells = 426;
    static int rowSize = 25;
    static int numOfBalls = 10;
    static ArrayList<Integer>[] adjList;
    static Node[] nodes;

    Graph()
    {
        adjList = new ArrayList[numOfCells];
        nodes = new Node[numOfCells];
        InitializeNodes();
        InitializeLinks();
    }

    void link(int a, int b) {
        if(adjList[a] == null)
        {
            adjList[a] = new ArrayList<>();
        }
        adjList[a].add(b);
    }

    public static int map(int x, int y) {
        return x * rowSize + y;
    }

    public static boolean valid(int num)
    {
        return num >= 0 && num < numOfCells;
    }

    public static int getRow(int num) {
        return num / rowSize;
    }

    public static int getCol(int num) {
        return num % rowSize;
    }

    void InitializeNodes() {
        int numOfNodes;
        int start;


        //Initialization of player triangle
        numOfNodes = 1;
        start = 12;

        for (int r = 0; r <= 3; r++) {
            for (int node = 0; node < numOfNodes; node++) {
                Node temp = new Node(r, start + node * 2, nodeState.Player);
                nodes[temp.num] = temp;
            }
            numOfNodes++;
            start--;
        }

        //Initialization of top empty cells
        numOfNodes = 13;
        start = 0;
        for (int r = 4; r <= 8; r++) {
            for (int node = 0; node < numOfNodes; node++) {
                Node temp = new Node(r, start + node * 2, nodeState.Empty);
                nodes[temp.num] = temp;
            }
            numOfNodes--;
            start++;
        }

        //Initialization of bottom empty cells
        numOfNodes = 10;
        start = 3;
        for (int r = 9; r <= 12; r++) {
            for (int node = 0; node < numOfNodes; node++) {
                Node temp = new Node(r, start + node * 2, nodeState.Empty);
                nodes[temp.num] = temp;
            }
            numOfNodes++;
            start--;
        }

        //Initialization of AI triangle
        numOfNodes = 4;
        start = 9;
        for (int r = 13; r <= 16; r++) {
            for (int node = 0; node < numOfNodes; node++) {
                Node temp = new Node(r, start + node * 2, nodeState.AI);
                nodes[temp.num] = temp;
            }
            numOfNodes--;
            start++;
        }
    }

    void InitializeLinks() {
        int dirX[] = {-1, -1, 1, 1, 0, 0};
        int dirY[] = {1, -1, 1, -1, 2, -2};
        for (Node node : nodes) {
            if (node == null)
                continue;

            int x = node.x;
            int y = node.y;
            for (int dir = 0; dir < 6; dir++) {
                int index = map(x + dirX[dir], y + dirY[dir]);
                if (!valid(index) || nodes[map(x + dirX[dir], y + dirY[dir])] == null)
                    continue;

                link(map(x, y), map(x + dirX[dir], y + dirY[dir]));
            }
        }
    }

    void printNodes() {
        for (Node node : nodes) {
            if (node == null)
                continue;
            node.print();

            for( int neighbour: adjList[node.num])
                System.out.print(getRow(neighbour) + " " + getCol(neighbour) + " -- ");
            System.out.println();
        }
    }

    /*
    public static void main(String[] args)
    {
        Graph graph = new Graph();
    }
     */


}
