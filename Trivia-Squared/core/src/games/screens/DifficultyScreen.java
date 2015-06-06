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
public class DifficultyScreen implements Screen {

    private final Application app;
    private Stage stage;

    private MenuButton easy;
    private MenuButton medium;
    private MenuButton hard;
    private MenuButton hell;
    private MenuButton mainMenu;

    public DifficultyScreen(Application app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        easy = new MenuButton(app, "Easy");
        medium = new MenuButton(app, "Medium");
        hard = new MenuButton(app, "Hard");
        hell = new MenuButton(app, "Hell");
        mainMenu = new MenuButton(app, "Main Menu");

        int buttonHeight = app.V_HEIGHT / 3;
        mainMenu.setBounds(app.V_WIDTH / 2 - 100, buttonHeight, 200, 45);
        hell.setBounds(app.V_WIDTH / 2  - 100, buttonHeight += 60, 200, 45);
        hard.setBounds(app.V_WIDTH / 2 - 100, buttonHeight += 60, 200, 45);
        medium.setBounds(app.V_WIDTH / 2 - 100, buttonHeight += 60, 200, 45);
        easy.setBounds(app.V_WIDTH / 2 - 100, buttonHeight+= 60, 200, 45);

        stage.addActor(hell.getButton());
        stage.addActor(hard.getButton());
        stage.addActor(medium.getButton());
        stage.addActor(easy.getButton());
        stage.addActor(mainMenu.getButton());

        mainMenu.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.mainMenuScreen = new MainMenuScreen(app);
                app.setScreen(app.mainMenuScreen);
            }
        });
        easy.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.GRIDSIZE = 3;
                app.playScreen = new PlayScreen(app);
                app.setScreen(app.playScreen);
            }
        });
        medium.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.GRIDSIZE = 7;
                app.playScreen = new PlayScreen(app);
                app.setScreen(app.playScreen);
            }
        });
        hard.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.GRIDSIZE = 10;
                app.playScreen = new PlayScreen(app);
                app.setScreen(app.playScreen);
            }
        });
        hell.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.GRIDSIZE = 15;
                app.playScreen = new PlayScreen(app);
                app.setScreen(app.playScreen);
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
        app.font50.draw(app.batch, "SELECT DIFFICULTY", Gdx.graphics.getWidth() /2 - 245, app.V_HEIGHT - 70);
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
