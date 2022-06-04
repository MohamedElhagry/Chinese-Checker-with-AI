import java.util.ArrayList;

public class GameManager {
    GUI gui;
    static MiniMax intelligence;
    static int depth;

    void startGame() {
        depth = 3;
        StateManager.curr = State.getInitialState();
        play();
    }

    public static ArrayList<StateManager.Move> getPlayerMove() {
        ArrayList<StateManager.Move> possibleMoves = StateManager.getValidMoves(StateManager.curr, false);
        return possibleMoves;
    }

    void play() {
        intelligence = new MiniMax();

        System.out.println("Please enter your move like so : ");
        System.out.println("enter the row num and col num of the piece you want to move");
        System.out.println("then enter the row num and col num of the piece you want to move to");

    }

    static void AIPlay() {
        System.out.println("Applying depth= "+ depth);
        StateManager.curr = intelligence.miniMax(StateManager.curr, true, depth);
    }

    static int checkwinning() {
        int status = StateManager.curr.isWin();
        if (status == -1)
            System.out.println("Congratulations, the player won!");
        else if (status == 1)
            System.out.println("The AI won this time.");
        return status;
    }

    public static void main(String[] args) {
        new Graph();
        GameManager gameManager = new GameManager();
        gameManager.startGame();

    }
}
