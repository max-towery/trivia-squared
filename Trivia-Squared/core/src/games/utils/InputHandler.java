package games.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import games.Application;
import games.containers.Rooms;

/**
 * Created by Max Towery on 5/29/2015.
 */
public class InputHandler {

    public static void keyboardInput(final Application app){
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)){
            app.camera.zoom = 13;
            app.camera.position.set(0, 0, 0);
            app.camera.position.set(app.V_WIDTH * Rooms.GRIDSIZE /2 , app.V_HEIGHT * Rooms.GRIDSIZE /2 , 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)){
            app.camera.position.set(app.startLoc[0] + app.V_WIDTH /2, app.startLoc[1] + app.V_HEIGHT /2, 0);
            app.camera.zoom = 1;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            app.camera.translate(0 - app.V_WIDTH , 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            app.camera.translate(app.V_WIDTH, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            app.camera.translate(0, app.V_HEIGHT);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            app.camera.translate(0, 0 - app.V_HEIGHT);
        }
    }
}
