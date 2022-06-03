public class GameManager {

    void startGame()
    {
        State state = State.getInitialState();
        play(StateManager.curr);
    }

    void play(State state)
    {
        boolean AITurn = false;

        while(state.isWin() == 0)
        {
            if(AITurn)
            {


            }
            else{

                //make player move
            }


        }

    }

    public static void main(String[] args) {

    }
}
