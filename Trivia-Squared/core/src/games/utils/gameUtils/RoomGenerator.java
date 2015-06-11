package games.utils.gameUtils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import games.GameStateManager;
import games.containers.Room;

import java.util.Random;
import static games.GameStateManager.GRIDSIZE;

/**
 * Created by Max Towery on 5/29/2015.
 */
public class RoomGenerator {

        public static Room[][] createImageGrid(final GameStateManager app){
            Room[][] grid = new Room[GRIDSIZE][GRIDSIZE];

            int xLoc = 0;
            int yLoc = 0;
            Random rand = new Random();
            int randomStart, randomFinish;
            randomStart = rand.nextInt(GRIDSIZE);
            randomFinish = rand.nextInt(GRIDSIZE);
            app.startLoc = new int[2];

            String serial;
            for (int i =0; i < GRIDSIZE; i++){
                for (int j = 0; j < GRIDSIZE; j ++){

                    assignDoors(grid, i, j);

                    grid[i][j].question = app.holder.getQuestion(randNumGen());

                    //place start image and remember location for camera
                    if (j == 0 && randomStart == i){
                        serial = grid[i][j].serialToBinary();
                        grid[i][j].setImage(new Image(app.assets.get("images/rooms/" + serial +".png", Texture.class)));

                        grid[i][j].setImage2(new Image(app.assets.get("images/rooms/start.png", Texture.class)));
                        grid[i][j].getImage2().setBounds(xLoc + app.V_WIDTH  / 2 - 250, yLoc + app.V_HEIGHT /2 - 150, 500 , 300);

                        app.startLoc[0] = xLoc; app.startLoc[1] = yLoc;
                        app.playerLoc[0] = app.startLoc[0]; app.playerLoc[1] = app.startLoc[1];
                        app.playerIndex[0] = i; app.playerIndex[1] = j;

                        grid[i][j].question.answeredCorrectly = true;

                    }
                    //place finish image and remember location for camera
                    else if (j == GRIDSIZE -1 && randomFinish == i){
                        serial = grid[i][j].serialToBinary();
                        grid[i][j].setImage(new Image(app.assets.get("images/rooms/" + serial +".png", Texture.class)));
                        grid[i][j].setImage2(new Image(app.assets.get("images/rooms/finish.png", Texture.class)));
                        grid[i][j].getImage2().setBounds(xLoc + app.V_WIDTH / 2 - 250, yLoc + app.V_HEIGHT / 2 - 150, 500, 300);
                        app.endLoc[0] = i; app.endLoc[1] = j;
                        grid[i][j].isEnd = true;
                    }

                    else{
                        serial = grid[i][j].serialToBinary();
                        grid[i][j].setImage(new Image(app.assets.get("images/rooms/" + serial +".png", Texture.class)));
                    }
                    grid[i][j].getImage().setBounds(xLoc,yLoc, GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT);
                    grid[i][j].location[0] = xLoc; grid[i][j].location[1] = yLoc;
                    grid[i][j].index[0] = i; grid[i][j].index[1] = j;
                    xLoc += GameStateManager.V_WIDTH;
                }
                xLoc = 0;
                yLoc += GameStateManager.V_HEIGHT;
            }
            return grid;
        }

        public static int randNumGen(){
            Random rand = new Random();
            int determiner = rand.nextInt(100) + 1;

            if (determiner > 15)
                return 0;
            else if (determiner > 5 && determiner < 16)
                return 1;
            else
                return 2;
        }

        public static void assignDoors(Room [][] grid, int i, int j){
            grid[i][j] = new Room();
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
