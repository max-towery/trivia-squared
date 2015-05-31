package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import games.Application;
import games.utils.InputHandler;

/**
 * Created by Max Towery on 5/29/2015.
 */
public class PauseScreen implements Screen{

    final Application app;

    public PauseScreen(Application app) {
        this.app = app;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        app.batch.begin();
        app.font30.draw(app.batch, "PAUSED", 20, 30);
        app.batch.end();

        InputHandler.resumeGameListener(app);
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
