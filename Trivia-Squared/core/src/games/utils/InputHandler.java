package games.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import games.Application;
import games.camera.CameraStyles;
import games.screens.MainMenuScreen;

/**
 * Created by Max Towery on 5/29/2015.
 */
public class InputHandler {

    public static void keyboardInput(final Application app, float delta){


        //testing
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            Image image = new Image(app.assets.get("images/5.png", Texture.class));
           app.grid[5][5].setImage(image);
            app.playScreen.stage.addActor(image);
            app.grid[5][5].getImage().setBounds(app.grid[5][5].location[0],app.grid[5][5].location[1], Application.V_WIDTH, Application.V_HEIGHT);


        }

        //pause game
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            app.playScreen.pause();
        }

        //increase score testing
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)){
            app.score.increase();
        }
    }

    public static void resumeGameListener(final Application app){
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            app.setScreen(app.playScreen);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)){
            app.mainMenuScreen = new MainMenuScreen(app);
            app.setScreen(app.mainMenuScreen);
        }
    }
}
