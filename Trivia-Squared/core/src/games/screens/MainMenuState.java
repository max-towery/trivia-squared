package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import games.GameStateManager;
import games.containers.MenuButton;
import games.utils.gameUtils.FileUtil;
import games.utils.gameUtils.SoundManager;


import java.io.IOException;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Max Towery on 5/28/2015.
 */
public class MainMenuState implements Screen {

    private final GameStateManager app;
    private Stage stage;
    private Image logo;
    private MenuButton startButton;
    private MenuButton quitButton;
    private MenuButton loadButton;
    private MenuButton highscoreButton;
    private MenuButton adminButton;
    private MenuButton manualButton;

    public MainMenuState(GameStateManager app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        app.soundManager = new SoundManager(app);
        Gdx.input.setInputProcessor(stage);
        Texture logoTex = app.assets.get("images/logo.png", Texture.class);
        logo = new Image(logoTex);
        logo.setOrigin(logo.getWidth() /2 , logo.getHeight() / 2);
        logo.setPosition(stage.getWidth() / 2 + 50, stage.getHeight() / 2 - 60);
        logo.addAction(scaleTo(3f, 3f, 3f));
        stage.addActor(logo);
        startButton = new MenuButton(app,"Start Game");
        quitButton = new MenuButton(app,"Quit Game");
        highscoreButton = new MenuButton(app, "High Scores");
        loadButton = new MenuButton(app,"Load Game");
        adminButton = new MenuButton(app, "Admin");
        manualButton = new MenuButton(app, "User Manual");
        manualButton.setBounds(0,0,200,45);
        adminButton.setBounds(app.V_WIDTH - 200, 0, 200, 45);
        int buttonHeight = app.V_HEIGHT / 3;
        quitButton.setBounds(20, buttonHeight, 200, 45);
        highscoreButton.setBounds(20, buttonHeight+= 60, 200, 45);
        loadButton.setBounds(20, buttonHeight += 60, 200, 45);
        startButton.setBounds(20, buttonHeight+= 60 , 200, 45);
        stage.addActor(startButton.getButton());
        stage.addActor(quitButton.getButton());
        stage.addActor(loadButton.getButton());
        stage.addActor(highscoreButton.getButton());
        stage.addActor(adminButton.getButton());
        stage.addActor(manualButton.getButton());
        quitButton.getButton().addListener(new ClickListener(){
           @Override
            public void clicked(InputEvent event, float x, float y){

               Gdx.app.exit();
           }
        });
        startButton.getButton().addListener(new ClickListener(){
           @Override
            public void clicked(InputEvent event, float x, float y){
               app.soundManager.buttonSound(app);
               app.difficultyScreen = new DifficultyState(app);
               app.setScreen(app.difficultyScreen);
           }
        });
        highscoreButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                app.highScoreScreen = new HighScoreState(app);
                app.setScreen(app.highScoreScreen);
            }
        });
        adminButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                app.adminScreen = new AdminState(app);
                app.setScreen(app.adminScreen);
            }
        });
        loadButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
            }
        });
        manualButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                try {
                    FileUtil.openUserManual();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        app.font50.draw(app.batch, "TRIVIA SQUARED", Gdx.graphics.getWidth() /2 - 140, app.V_HEIGHT - 70);
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
