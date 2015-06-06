package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import games.Application;
import games.containers.MenuButton;

/**
 * Created by Max Towery on 6/5/2015.
 */
public class EndGameScreen implements Screen{

    private final Application app;
    private Stage stage;
    private MenuButton exitButton;
    private MenuButton mainMenu;

    public boolean isWon;


    public EndGameScreen(Application app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(games.Application.V_WIDTH, games.Application.V_HEIGHT,app.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        exitButton = new MenuButton(app, "Exit Game");
        mainMenu = new MenuButton(app, "Main Menu");

        int buttonHeight = app.V_HEIGHT / 5;
        mainMenu.setBounds(app.V_WIDTH / 2 - 100, buttonHeight, 200, 45);
        exitButton.setBounds(app.V_WIDTH / 2 - 100, buttonHeight += 60, 200, 45);

        stage.addActor(mainMenu.getButton());
        stage.addActor(exitButton.getButton());

        mainMenu.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.mainMenuScreen = new MainMenuScreen(app);
                app.setScreen(app.mainMenuScreen);
            }
        });

        exitButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
        app.batch.begin();
        if (isWon){
            app.font50.draw(app.batch, "VICTORY", Gdx.graphics.getWidth() /2 - 245, app.V_HEIGHT - 70);
        }
        else
            app.font50.draw(app.batch, "DEFEAT", Gdx.graphics.getWidth() / 2 - 150, app.V_HEIGHT - 70);

        app.batch.end();
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
