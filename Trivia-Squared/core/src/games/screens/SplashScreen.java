package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import games.Application;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


/**
 * Created by Max Towery on 5/28/2015.
 */
public class SplashScreen implements Screen {

    private final Application app;
    private Stage stage;

    private Image splashImg;

    float splashDelayAcc = 0f;

    public SplashScreen(Application app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Texture splashTex = app.assets.get("images/logo.png", Texture.class);
        splashImg = new Image(splashTex);

        splashImg.setOrigin(splashImg.getWidth() /2 , splashImg.getHeight() / 2);
        splashImg.setPosition(stage.getWidth() /2, stage.getHeight() / 2);
        splashImg.addAction(scaleTo(5f, 5f, 2.5f));
        stage.addActor(splashImg);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();

        app.batch.begin();

       // System.out.println(app.font50.getSpaceWidth()*app.font50.toString().length());
        app.font50.draw(app.batch, "TRIVIA SQUARED", Gdx.graphics.getWidth() /2 - 175, app.V_HEIGHT - 70);
        app.batch.end();
    }

    public void update(float delta){
        stage.act(delta);
        this.splashDelayAcc += delta;
        if (splashDelayAcc >= 4){
            app.setScreen(app.mainMenuScreen);
        }
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
