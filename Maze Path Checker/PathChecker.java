import java.util.Arrays;

public class PathChecker {

    //used for testing. Delete when finished.
    public static void main(String [] args){

        int[][] grid = {{1,0,1,1,0},
                        {1,0,0,1,0},
                        {1,0,1,1,0},
                        {1,1,1,1,0},
                        {0,0,0,1,1}};

        int [][] result = new int[5][5];        // -1 = not visited
        for (int i = 0; i < 5; i++){            //0 = visited bad path
            Arrays.fill(result[i], -1);         //1 = visited good path
        }

        System.out.println("Has Path?: " + hasPath(grid, result));
        System.out.println("Path Matrix: ");
        for (int i = 0; i < result.length; i++)
            System.out.println(Arrays.toString(result[i]));
    }

    public static boolean hasPathHelper(int [][]g, int [][]r, int i, int j){
        if (i < 0 || j < 0 || i >= r.length || j >= g[0].length || r[i][j] >= 0)
            return false;                           //index OOB

        r[i][j] = 0;                                //mark as visited
        if (g[i][j] == 0)                           //path stops here
            return false;
        if (j == g[0].length - 1 ||
                hasPathHelper(g, r, i + 1, j) ||     //check down
                hasPathHelper(g, r, i - 1, j) ||      //check up
                hasPathHelper(g, r, i, j+1) ||      //check right
                hasPathHelper(g, r, i, j-1)         //check left
                )r[i][j] = 1;                       //mark as good path

        return r[i][j] == 1;
    }

    public static boolean hasPath(int [][] g, int [][] r){
        for(int i = 0; i < g.length; i++){
            if (hasPathHelper(g, r, i, 0))
                return true;
        }
        return false;
    }


}
