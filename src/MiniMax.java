import java.util.ArrayList;

public class MiniMax
{
    int manhatinDistance(int x1, int y1, int x2, int y2)
    {
        return Math.abs(x1-x2)+Math.abs(y1-y2);
    }

    int getH(State state)
    {
        int bh = 0;
        int destX = 0, destY = 12;
        for(int num:state.blueBalls)
        {
            int x = GraphForGame.getRow(num);
            int y = GraphForGame.getCol(num);
            bh+=manhatinDistance(x, y, destX, destY);
        }
        int rh = 0;
        destX = 16;
        for(int num:state.redBalls)
        {
            int x = GraphForGame.getRow(num);
            int y = GraphForGame.getCol(num);
            rh+=manhatinDistance(x, y, destX, destY);
        }

        //the more positive the more ability to win
        System.out.println("bh = "+bh);
        System.out.println("rh = "+rh);
        int h = rh-bh;
        state.setH(h);
        return h;
    }


    //assume AI always plays first

    public int miniMax(State curr, boolean isAi, int depth)
    {
        //todo associate with open and close list to prevent infinite behaviour
        if(depth == 0) return curr.getH();
        ArrayList<State> children = StateManager.getChildren(curr, isAi);
        State best;
        if(isAi)  best = getMaxChild(children);
        else best = getMinChild(children);
        int ret;
        if(isAi){
            ret = Integer.MIN_VALUE;
            for(State child:children){
                ret = Math.max(ret, best.getH()+miniMax(child, !isAi, depth-1));
            }
        }else{
            ret = Integer.MAX_VALUE;
            for(State child:children){
                ret = Math.min(ret, best.getH()+miniMax(child, !isAi, depth-1));
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
            int hVal = getH(state);
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
            int hVal = getH(state);
            if(hVal < curr)
            {
                curr = hVal;
                best = state;
            }
        }
        return best;
    }

}
