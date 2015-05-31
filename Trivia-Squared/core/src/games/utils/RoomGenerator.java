package games.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import games.Application;
import games.containers.Room;

import java.util.Random;

/**
 * Created by Max Towery on 5/29/2015.
 */
public class RoomGenerator {

        private static String [] fileHandles = {"images/1.png", "images/2.png", "images/3.png"};
        private static String startfh = "images/4.png";
        private static String endfh = "images/5.png";

        public static final int GRIDSIZE = 10;

        public static Room[][] createImageGrid(final Application app){
            Room[][] grid = new Room[GRIDSIZE][GRIDSIZE];

            int xLoc = 0;
            int yLoc = 0;
            Random rand = new Random();
            int randomFh, randomStart, randomFinish;
            randomStart = rand.nextInt(GRIDSIZE);
            randomFinish = rand.nextInt(GRIDSIZE);
            app.startLoc = new int[2];
            app.endLoc = new int[2];

            for (int i =0; i < GRIDSIZE; i++){
                for (int j = 0; j < GRIDSIZE; j ++){

                    //place start image and remember location for camera
                    if (j == 0 && randomStart == i){

                        grid[i][j] = new Room(new Image(app.assets.get(startfh, Texture.class)));
                        app.startLoc[0] = xLoc; app.startLoc[1] = yLoc;
                        app.playerLoc[0] = app.startLoc[0]; app.playerLoc[1] = app.startLoc[1];

                        System.out.println("start: " + app.startLoc[0] + " " + app.startLoc[1]);
                        System.out.println("player: " + app.playerIndex[0] + " " + app.playerIndex[1]);
                        app.playerIndex[0] = i; app.playerIndex[1] = j;

                    }
                    //place end image and remember location for camera
                    else if (j == GRIDSIZE -1 && randomFinish == i){
                        grid[i][j] = new Room(new Image(app.assets.get(endfh, Texture.class)));

                        app.endLoc[0] = i; app.endLoc[1] = j;
                    }
                    //place random image
                    else{
                        randomFh = rand.nextInt(3);
                        grid[i][j] = new Room(new Image(app.assets.get(fileHandles[randomFh], Texture.class)));
                    }
                    grid[i][j].getImage().setBounds(xLoc,yLoc, Application.V_WIDTH, Application.V_HEIGHT);
                    xLoc += Application.V_WIDTH;


                    //door assignments
                    if (i == 0)
                        grid[i][j].setBotDoor(false);
                    if (i == GRIDSIZE -1)
                        grid[i][j].setTopDoor(false);
                    if (j == 0)
                        grid[i][j].setLeftDoor(false);
                    if (j == GRIDSIZE -1)
                        grid[i][j].setRightDoor(false);
                }
                xLoc = 0;
                yLoc += Application.V_HEIGHT;
            }


            return grid;
        }

        public static Room [][] createRoomGrid(int gridSize){
            Room [][] grid = new Room[gridSize][gridSize];
            for(int i = 0; i < gridSize; i++){
                for(int j = 0; j < gridSize; j++){
                    grid[i][j] = new Room();
                    //door assignments
                    if (i == 0)
                        grid[i][j].setBotDoor(false);
                    if (i == GRIDSIZE -1)
                        grid[i][j].setTopDoor(false);
                    if (j == 0)
                        grid[i][j].setLeftDoor(false);
                    if (j == GRIDSIZE -1)
                        grid[i][j].setRightDoor(false);
                }
            }
            return grid;
        }
}
