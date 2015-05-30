package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import games.Application;

/**
 * Created by Max Towery on 5/28/2015.
 */
public class LoadingScreen implements Screen {

    private final Application app;

    private ShapeRenderer shapeRenderer;
    private float progress;


    public LoadingScreen(final Application app) {
        this.app = app;
        this.shapeRenderer = new ShapeRenderer();
    }

    private void queueAssets() {

        app.assets.load("images/logo.png", Texture.class);
        app.assets.load("buttons/menubutton.pack", TextureAtlas.class);
        app.assets.load("images/1.png", Texture.class);
        app.assets.load("images/2.png", Texture.class);
        app.assets.load("images/3.png", Texture.class);
        app.assets.load("images/4.png", Texture.class);
        app.assets.load("images/5.png", Texture.class);

    }

    @Override
    public void show() {
        this.progress = 0f;
        queueAssets();
    }

    private void update(float delta){
        progress = MathUtils.lerp(progress, app.assets.getProgress(), .025f);

        if(app.assets.update() && progress >= app.assets.getProgress() - .01f){
            app.setScreen(app.mainMenuScreen);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        //draw the progress bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, app.camera.viewportHeight / 2 - 8, app.camera.viewportWidth - 64,
                16);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(32, app.camera.viewportHeight / 2 - 8, progress * (app.camera.viewportWidth - 64),
                16);

        shapeRenderer.end();

        app.batch.begin();
        app.font30.draw(app.batch, "LOADING", 20, 30);
        app.batch.end();
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
