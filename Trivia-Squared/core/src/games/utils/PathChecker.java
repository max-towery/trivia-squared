package games.utils;

import games.containers.Room;

import java.util.Arrays;

/**
 * Created by Max Towery on 5/30/2015.
 */
public class PathChecker {
    /*
    //used for testing. Delete when finished.
    public static void main(String [] args){

        int[][] grid =
                       {{1,0,1,1,0},
                        {1,0,0,1,0},
                        {1,0,1,1,0},
                        {1,1,1,1,0},
                        {0,0,0,1,1}};



        Room [][] rooms = RoomGenerator.createRoomGrid(5);

        alterRooms(grid, rooms);
        int [][] result1 = new int[5][5];
        int [][] result = new int[5][5];        // -1 = not visited
        for (int i = 0; i < 5; i++){            //0 = visited bad path
            Arrays.fill(result[i], -1);         //1 = visited good path
            Arrays.fill(result1[i], -1);
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
                hasPathHelper(g, r, i - 1, j) ||     //check down
                hasPathHelper(g, r, i + 1, j) ||      //check up
                hasPathHelper(g, r, i, j+1) ||      //check right
                hasPathHelper(g, r, i, j-1)         //check left
                )r[i][j] = 1;                       //mark as good path

        return r[i][j] == 1;
    }

    public static boolean hasPath(int [][] g, int [][] r){
        for(int i = r.length -1 ; i < g.length; i--){
            if (hasPathHelper(g, r, i, 0))
                return true;
        }
        return false;
    }


    public static void alterRooms(int[][] grid, Room[][] rooms)
    {
        for(int i = grid.length -1 ; i < grid.length; i--)
        {
            for(int j = 0; j < grid[i].length; j++)
            {
                if(grid[i][j] == 0)
                {
                    rooms[i][j].setToFalse();
                }
                else
                if(grid[i][j] ==1)
                {

                    //START EDITING FROM HERE
                    if(i != 0) //not in first row
                    {
                        if(i!= grid.length-1) //not in last row
                        {
                            if(j != 0) //not the first column
                            {
                                if(j != grid[0].length -1) //not the last column, not in top or bot. row
                                {
                                    if(grid[i+1][j] == 0)
                                        rooms[i][j].setTop();
                                    if(grid[i][j+1] == 0)
                                        rooms[i][j].setRight();
                                    if(grid[i-1][j] == 0)
                                        rooms[i][j].setBot();
                                    if(grid[i][j-1] == 0)
                                        rooms[i][j].setLeft();
                                }
                                else //last column, not top or bot. row
                                {
                                    if(grid[i+1][j] == 0)
                                        rooms[i][j].setTop();
                                    if(grid[i-1][j] == 0)
                                        rooms[i][j].setBot();
                                    if(grid[i][j-1] == 0)
                                        rooms[i][j].setLeft();
                                }
                            }
                            else //first column, not top or bot. row
                            {
                                if(grid[i+1][j] == 0)
                                    rooms[i][j].setTop();
                                if(grid[i-1][j] == 0)
                                    rooms[i][j].setBot();
                                if(grid[i][j+1] == 0)
                                    rooms[i][j].setRight();
                            }
                        }
                        else //last row
                        {
                            if(j != 0)//not in first column
                            {
                                if(j != grid[0].length-1)//not in last column
                                {
                                    if(grid[i-1][j] == 0)
                                        rooms[i][j].setTop();
                                    if(grid[i][j+1] == 0)
                                        rooms[i][j].setRight();
                                    if(grid[i][j-1] == 0)
                                        rooms[i][j].setLeft();
                                }
                                else //in the last column
                                {
                                    if(grid[i-1][j] ==0)
                                        rooms[i][j].setTop();
                                    if(grid[i][j-1] == 0)
                                        rooms[i][j].setLeft();
                                }
                            }
                            else //in the first column
                            {
                                if(grid[i-1][j] == 0)
                                    rooms[i][j].setBot();
                                if(grid[i][j+1] == 0)
                                    rooms[i][j].setRight();
                            }
                        }

                    }
                    else//first row
                    {
                        if(j != 0) //not in first column
                        {
                            if(j != grid[0].length-1) //not in last column
                            {
                                if(grid[i+1][j] == 0)
                                    rooms[i][j].setBot();
                                if(grid[i][j+1] == 0)
                                    rooms[i][j].setRight();
                                if(grid[i][j-1] == 0)
                                    rooms[i][j].setLeft();
                            }
                            else //in last column
                            {
                                if(grid[i+1][j] == 0)
                                    rooms[i][j].setBot();
                                if(grid[i][j-1] == 0)
                                    rooms[i][j].setLeft();
                            }
                        }
                        else //in first column
                        {
                            if(grid[i+1][j] == 0)
                                rooms[i][j].setBot();
                            if(grid[i][j+1] == 0)
                                rooms[i][j].setRight();

                        }

                    }
                }
            }
        }
    }

*/

}


