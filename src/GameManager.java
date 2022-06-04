import java.util.ArrayList;
import java.util.Scanner;

public class GameManager {

    void startGame()
    {
        State state = State.getInitialState();
        play(state,3);
    }

    void play(State state, int depth)
    {
        boolean AITurn = false;
        MiniMax intelligence = new MiniMax();

        System.out.println("Please enter your move like so : ");
        System.out.println("enter the row num and col num of the piece you want to move");
        System.out.println("then enter the row num and col num of the piece you want to move to");

        while(state.isWin() == 0)
        {
            if(AITurn)
            {
                state = intelligence.miniMax(state, true, depth);
                AITurn = false;
            }
            else{
                state.printState();
                System.out.println("Please enter your move");


                int x1,y1,x2,y2;
                Scanner sc = new Scanner(System.in);
                x1 = sc.nextInt();
                y1 = sc.nextInt();
                x2 = sc.nextInt();
                y2 = sc.nextInt();

                ArrayList<StateManager.Move> possibleMoves = StateManager.getValidMoves(state, false);
                boolean validMove = false;
                for(StateManager.Move move : possibleMoves)
                {
                    if(x1 == move.x1 && y1 == move.y1 && x2 == move.x2 && y2 == move.y2)
                    {
                        validMove = true;
                        break;
                    }
                }
                for(StateManager.Move move : possibleMoves)
                {
                    move.print();
                }


                if(validMove){
                    state.doMove(x1,y1,x2,y2);
                    AITurn = true;
                }
                else{
                    System.out.println("Invalid move, please enter another move");
                }

                //make player move
            }
        }

        if(state.isWin() == -1)
            System.out.println("Congratulations, the player won!");
        else
            System.out.println("The AI won this time.");

    }


    public static void main(String[] args) {
        new Graph();
        GameManager gameManager = new GameManager();
        gameManager.startGame();

    }
}
