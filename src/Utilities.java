import java.util.Arrays;

public class Utilities {
    public static boolean equal(int []a, int []b)
    {
        Arrays.sort(b);
        for(int i=0; i<a.length; i++)
            if(a[i] != b[i])
                return false;

        return true;
    }

    public static boolean find(int[] arr, int target)
    {
        for(int i=0;i<arr.length;i++){
            if(arr[i] == target){
                return true;
            }
        }
        return false;
    }

    public static void tellMe(int[] prev, int [] now)
    {
        for(int i=0; i<10; i++)
        {
            if(prev[i] != now[i])
            {
                int x1 = Graph.getRow(prev[i]);
                int y1 = Graph.getCol(prev[i]);
                int x2 = Graph.getRow(now[i]);
                int y2 = Graph.getCol(now[i]);
                System.out.println("("+ x1 + "," + y1 + ") --> (" + x2 + "," + y2 + ")");
            }
        }
    }

    public static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
