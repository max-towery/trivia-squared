package games.containers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import games.Application;

import java.util.Random;

/**
 * Created by Max Towery on 5/29/2015.
 */
public class Rooms {

        private static String [] fileHandles = {"images/1.png", "images/2.png", "images/3.png"};
        private static String startfh = "images/4.png";
        private static String endfh = "images/5.png";

        public static final int GRIDSIZE = 10;



        public static Image[][] createImageGrid(final Application app, Stage stage){
            Image[][] grid = new Image[GRIDSIZE][GRIDSIZE];

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
                        grid[i][j] = new Image(app.assets.get(startfh, Texture.class));
                        app.startLoc[0] = xLoc; app.startLoc[1] = yLoc;
                    }
                    //place end image and remember location for camera
                    else if (j == GRIDSIZE -1 && randomFinish == i){
                        grid[i][j] = new Image(app.assets.get(endfh, Texture.class));
                        app.endLoc[0] = xLoc; app.endLoc[1] = yLoc;
                    }
                    //place random image
                    else{
                        randomFh = rand.nextInt(3);
                        grid[i][j] = new Image(app.assets.get(fileHandles[randomFh], Texture.class));
                    }
                    grid[i][j].setBounds(xLoc,yLoc, Application.V_WIDTH, Application.V_HEIGHT);
                    xLoc += Application.V_WIDTH;
                }
                xLoc = 0;
                yLoc += Application.V_HEIGHT;
            }

            for (int i = 0; i < Rooms.GRIDSIZE; i++){
                for (int j = 0; j < Rooms.GRIDSIZE; j++){
                    stage.addActor(grid[i][j]);
                }
            }

            return grid;
        }
}
