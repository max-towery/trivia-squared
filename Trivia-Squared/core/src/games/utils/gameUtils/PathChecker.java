package games.utils.gameUtils;

import games.containers.Room;

import java.util.ArrayDeque;


/**
 * Created by Max Towery on 5/30/2015.
 */
public class PathChecker {

    public static boolean checkPaths(Room[][] grid, int i, int j){
        //mark all rooms unvisisted
        markUnvisited(grid);
        ArrayDeque <Room> queue = new ArrayDeque<Room>();
        //add first room
        queue.add(grid[i][j]);
        grid[i][j].setVisited(true);

        while(!queue.isEmpty()){
            Room room = queue.remove();
            if (room.isEnd)
                return true;
            addNeighbors(room, grid, queue, room.index[0], room.index[1]);
        }
        return false;
    }

    private static void addNeighbors(Room room, Room [][] grid, ArrayDeque<Room> queue, int i, int j){
        if(grid[i][j].isTopDoor()){
            if (!grid[i+1][j].getVisited()){
                queue.add(grid[i+1][j]);
                grid[i+1][j].setVisited(true);
            }
        }
        if(grid[i][j].isBotDoor()){
            if (!grid[i-1][j].getVisited()){
                queue.add(grid[i-1][j]);
                grid[i-1][j].setVisited(true);
            }
        }
        if(grid[i][j].isLeftDoor()){
            if (!grid[i][j-1].getVisited()){
                queue.add(grid[i][j-1]);
                grid[i][j-1].setVisited(true);
            }
        }
        if(grid[i][j].isRightDoor()){
            if (!grid[i][j+1].getVisited()){
                queue.add(grid[i][j+1]);
                grid[i][j+1].setVisited(true);
            }
        }
    }

    private static void markUnvisited(Room[][] grid){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid.length; j++)
                grid[i][j].setVisited(false);
        }
    }

}


