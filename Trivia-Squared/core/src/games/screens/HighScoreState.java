package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import games.GameStateManager;
import games.containers.MenuButton;
import games.utils.gameUtils.HighScoreUtil;

import java.io.IOException;


/**
 * Created by Max Towery on 6/6/2015.
 */
public class HighScoreState implements Screen {

    private final GameStateManager app;
    private Stage stage;
    private MenuButton mainMenu;
    private MenuButton easy;
    private MenuButton medium;
    private MenuButton hard;
    private MenuButton hell;

    private String modeDisplay;




    public HighScoreState(GameStateManager app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT,app.camera));
    }

    @Override
    public void show() {
        modeDisplay = "";

        Gdx.input.setInputProcessor(stage);

        app.score.updateTime();
        mainMenu = new MenuButton(app, "Main Menu");
        easy = new MenuButton(app, "Easy");
        medium = new MenuButton(app, "Medium");
        hard = new MenuButton(app, "Hard");
        hell = new MenuButton(app, "Hell");

        int pad = 20;
        mainMenu.setBounds(10, 20, 200, 45);
        hell.setBounds(10, pad+= 60, 200, 45);
        hard.setBounds(10, pad += 60, 200, 45);
        medium.setBounds(10, pad += 60, 200, 45);
        easy.setBounds(10, pad += 60, 200, 45);

        stage.addActor(mainMenu.getButton());
        stage.addActor(easy.getButton());
        stage.addActor(medium.getButton());
        stage.addActor(hard.getButton());
        stage.addActor(hell.getButton());

        mainMenu.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                app.mainMenuScreen = new MainMenuState(app);
                app.setScreen(app.mainMenuScreen);
            }
        });
        easy.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                try {
                    app.scores = HighScoreUtil.readFile(HighScoreUtil.easyfh);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                modeDisplay = "EASY";
            }
        });
        medium.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                try {
                    app.scores = HighScoreUtil.readFile(HighScoreUtil.medfh);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                modeDisplay = "MEDIUM";
            }
        });
        hard.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                try {
                    app.scores = HighScoreUtil.readFile(HighScoreUtil.hardfh);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                modeDisplay = "HARD";
            }
        });
        hell.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                try {
                    app.scores = HighScoreUtil.readFile(HighScoreUtil.hellfh);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                modeDisplay = "HELL";
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
        app.font50.draw(app.batch, modeDisplay, 10, app.V_HEIGHT - 70);
        app.font50.draw(app.batch, "HIGH SCORES", Gdx.graphics.getWidth() /2 - 175, app.V_HEIGHT - 70);
        int height = app.V_HEIGHT - 150;
        app.font30.draw(app.batch, "RANK", Gdx.graphics.getWidth() /2 - 290, height);
        app.font30.draw(app.batch, "NAME", Gdx.graphics.getWidth() /2 - 160, height);
        app.font30.draw(app.batch, "SCORE", Gdx.graphics.getWidth() /2 +155, height);
        app.font30.draw(app.batch, "__________________________________________", Gdx.graphics.getWidth() /2 - 290, height-=20);
        height -=40;
        if (modeDisplay.length() > 1){
            for(int i = 0; i < app.scores.size(); i++){
                app.font20.draw(app.batch, String.valueOf(i+1) + ".", Gdx.graphics.getWidth() /2 - 290, height-= 25);
                app.font20.draw(app.batch, app.scores.get(i).getName(), Gdx.graphics.getWidth() /2 - 160, height);
                app.font20.draw(app.batch, app.scores.get(i).getScoreToString(), Gdx.graphics.getWidth() /2 +155, height);
            }
        }
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

    public String getModeDisplay() {
        return modeDisplay;
    }

    public void setModeDisplay(String modeDisplay) {
        this.modeDisplay = modeDisplay;
    }
}

