import java.util.ArrayList;

public class MiniMax
{


    //assume AI always plays first

    public int miniMax(State curr, boolean isAi, int depth)
    {
        //todo associate with open and close list to prevent infinite behaviour
        if(depth == 0) return curr.getUtility();
        ArrayList<State> children = StateManager.getChildren(curr, isAi);
        State best;
        if(isAi)  best = getMaxChild(children);
        else best = getMinChild(children);
        int ret;
        if(isAi){
            ret = Integer.MIN_VALUE;
            for(State child:children){
                ret = Math.max(ret, best.getUtility()+miniMax(child, !isAi, depth-1));
            }
        }else{
            ret = Integer.MAX_VALUE;
            for(State child:children){
                ret = Math.min(ret, best.getUtility()+miniMax(child, !isAi, depth-1));
            }
        }
        return ret;
    }

    public State getMaxChild(ArrayList<State> children)
    {
        int curr = Integer.MIN_VALUE;
        State best = null;
        for(State state:children)
        {
            int hVal = state.getUtility();
            if(hVal > curr)
            {
                curr = hVal;
                best = state;
            }
        }
        return best;
    }

    public State getMinChild(ArrayList<State> children)
    {
        int curr = Integer.MAX_VALUE;
        State best = null;
        for(State state:children)
        {
            int hVal = state.getUtility();
            if(hVal < curr)
            {
                curr = hVal;
                best = state;
            }
        }
        return best;
    }

}
