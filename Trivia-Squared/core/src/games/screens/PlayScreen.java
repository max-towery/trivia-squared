package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import games.Application;
import games.containers.Rooms;
import games.utils.InputHandler;

/**
 * Created by Max Towery on 5/28/2015.
 */
public class PlayScreen implements Screen {

    private final Application app;

    private Stage stage;



    private Image [][] grid;

    public PlayScreen(Application app) {

        this.app = app;
        this.stage = new Stage(new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, app.camera));

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        grid = Rooms.createImageGrid(app, stage);
        app.camera.position.set(app.startLoc[0] + app.V_WIDTH /2, app.startLoc[1] + app.V_HEIGHT /2, 0);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();

        app.batch.begin();
        //put batch stuff here
        app.batch.end();


        InputHandler.keyboardInput(app);


    }

    public void update(float delta){
        stage.act(delta);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
