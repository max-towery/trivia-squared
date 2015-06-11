package games.utils.gameUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import games.GameStateManager;
import games.screens.MainMenuState;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

/**
 * Created by Max Towery on 5/29/2015.
 */
public class InputHandler {

    public static void keyboardInput(final GameStateManager app, float delta){


        //testing
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            Image image = new Image(app.assets.get("images/5.png", Texture.class));
           app.grid[5][5].setImage(image);
            app.playScreen.stage.addActor(image);
            app.grid[5][5].getImage().setBounds(app.grid[5][5].location[0],app.grid[5][5].location[1], GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT);


        }

        //pause game
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            app.playScreen.pauseNotAutomatic();
        }

        //increase score testing
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)){
            app.score.increase();
        }
    }

    public static void resumeGameListener(final GameStateManager app){
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
                app.playScreen.mapButton.hide();
                app.playScreen.arrowButtons.hide();
                app.playScreen.pauseButton.hide();
                app.setScreen(app.playScreen);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)){
            app.mainMenuScreen = new MainMenuState(app);
            app.setScreen(app.mainMenuScreen);
        }
    }
}
