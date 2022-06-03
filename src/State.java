import java.security.PublicKey;
import java.util.Arrays;
import java.util.Comparator;

public class State implements Comparator<State> {
    public int[] redBalls;
    public int[] blueBalls;
    private int heuristic;

    State()
    {
        redBalls = new int[10];
        blueBalls = new int[10];
        heuristic = Integer.MIN_VALUE;
    }

    int getH(){
        return heuristic;
    }

    void setH(int val){
        heuristic = val;
    }

    State(State s)
    {
        this.redBalls = new int[10];
        this.blueBalls = new int[10];
        for(int i=0;i<10;i++){
            this.redBalls[i] = s.redBalls[i];
            this.blueBalls[i] = s.blueBalls[i];
        }
        this.heuristic = s.heuristic;
    }

    @Override
    public int compare(State a, State b)
    {
        Arrays.sort(a.redBalls);
        Arrays.sort(b.blueBalls);
        for(int i = 0; i< a.redBalls.length; i++)
        {
            if(a.redBalls[i] < b.redBalls[i])
            {
                return -1;
            }else if(a.redBalls[i] > b.redBalls[i]){
                return 1;
            }
        }
        for(int i=0;i<a.blueBalls.length;i++)
        {
            if(a.blueBalls[i] < b.blueBalls[i])
            {
                return -1;
            }else if(a.blueBalls[i] > b.blueBalls[i]){
                return 1;
            }
        }
        return 0;
    }



}
