package games.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import games.Application;
import games.camera.CameraStyles;
import games.screens.MainMenuScreen;

/**
 * Created by Max Towery on 5/29/2015.
 */
public class InputHandler {

    public static void keyboardInput(final Application app, float delta){



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
