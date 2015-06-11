package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import games.GameStateManager;

/**
 * Created by Max Towery on 5/28/2015.
 */
public class LoadingState implements Screen {

    private final GameStateManager app;

    private ShapeRenderer shapeRenderer;
    private float progress;


    public LoadingState(final GameStateManager app) {
        this.app = app;
        this.shapeRenderer = new ShapeRenderer();
    }

    private void queueAssets() {


        //miscellaneous images
        app.assets.load("images/logo.png", Texture.class);

        //buttons
        app.assets.load("buttons/menubutton.pack", TextureAtlas.class);
        app.assets.load("buttons/map.pack", TextureAtlas.class);
        app.assets.load("buttons/arrows.pack", TextureAtlas.class);
        app.assets.load("buttons/qButton.pack", TextureAtlas.class);
        app.assets.load("buttons/pause.pack", TextureAtlas.class);

        //map images
        app.assets.load("images/rooms/finish.png", Texture.class);
        app.assets.load("images/rooms/start.png", Texture.class);

        app.assets.load("images/rooms/0000.png", Texture.class);
        app.assets.load("images/rooms/0001.png", Texture.class);
        app.assets.load("images/rooms/0010.png", Texture.class);
        app.assets.load("images/rooms/0011.png", Texture.class);
        app.assets.load("images/rooms/0100.png", Texture.class);
        app.assets.load("images/rooms/0101.png", Texture.class);
        app.assets.load("images/rooms/0110.png", Texture.class);
        app.assets.load("images/rooms/0111.png", Texture.class);
        app.assets.load("images/rooms/1000.png", Texture.class);
        app.assets.load("images/rooms/1001.png", Texture.class);
        app.assets.load("images/rooms/1010.png", Texture.class);
        app.assets.load("images/rooms/1011.png", Texture.class);
        app.assets.load("images/rooms/1100.png", Texture.class);
        app.assets.load("images/rooms/1101.png", Texture.class);
        app.assets.load("images/rooms/1110.png", Texture.class);
        app.assets.load("images/rooms/1111.png", Texture.class);
        app.assets.load("images/misc/playerPos.png", Texture.class);

        //sounds
        app.assets.load("sounds/buttonclick.mp3", Sound.class);
        app.assets.load("music/techno.mp3", Music.class);
    }

    @Override
    public void show() {

        this.progress = 0f;
        queueAssets();
    }

    private void update(float delta){
        progress = MathUtils.lerp(progress, app.assets.getProgress(), .025f);

        if(app.assets.update() && progress >= app.assets.getProgress() - .01f){
            app.mainMenuScreen = new MainMenuState(app);
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
        app.font50.draw(app.batch, "LOADING...", app.camera.viewportWidth /2 - 110, app.camera.viewportHeight /2 + 100);
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
