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
}
