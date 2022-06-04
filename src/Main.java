import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        new Graph();
        GameManager gameManager = new GameManager();
        StateManager.curr = State.getInitialState();
        GUI gui = new GUI();
        gameManager.gui=gui;
        gameManager.startGame();
    }
}
