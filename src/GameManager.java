import java.util.ArrayList;

public class GameManager {
    GUI gui;
    static MiniMax intelligence;
    static int depth;

    void startGame() {
        StateManager.curr = State.getInitialState();

        play(3);
    }

    public static ArrayList<StateManager.Move> getPlayerMove() {
        ArrayList<StateManager.Move> possibleMoves = StateManager.getValidMoves(StateManager.curr);
        return possibleMoves;
    }

    void play(int d) {
        this.depth = d;
        intelligence = new MiniMax();

        System.out.println("Please enter your move like so : ");
        System.out.println("enter the row num and col num of the piece you want to move");
        System.out.println("then enter the row num and col num of the piece you want to move to");

    }

    static void switchTurn() {
        StateManager.curr = intelligence.miniMax(StateManager.curr, true, depth);
    }

    static void checkwinning() {
        int status = StateManager.curr.isWin();
        if (status == -1)
            System.out.println("Congratulations, the player won!");
        else if (status == 1)
            System.out.println("The AI won this time.");
    }

    public static void main(String[] args) {
        new Graph();
        GameManager gameManager = new GameManager();
        gameManager.startGame();

    }
}
