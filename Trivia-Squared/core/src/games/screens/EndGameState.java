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
 * Created by Max Towery on 6/5/2015.
 */
public class EndGameState implements Screen{

    private GameStateManager app;
    private Stage stage;
    private MenuButton exitButton;
    private MenuButton mainMenu;
    private boolean isWon;

    public EndGameState(GameStateManager app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT,app.camera));
    }

    @Override
    public void show() {

        app.soundManager.music.stop();
        app.score.updateTime();
        if (isWon){
            try {
                if (app.difficultyScreen.getDifficulty() == 4){
                    app.scores = HighScoreUtil.readFile(HighScoreUtil.easyfh);
                    HighScoreUtil.writeToFile(app, HighScoreUtil.easyfh);
                }

                else if (app.difficultyScreen.getDifficulty() == 3){
                    app.scores = HighScoreUtil.readFile(HighScoreUtil.medfh);
                    HighScoreUtil.writeToFile(app, HighScoreUtil.medfh);
                }

                else if (app.difficultyScreen.getDifficulty() == 2){
                    app.scores = HighScoreUtil.readFile(HighScoreUtil.hardfh);
                    HighScoreUtil.writeToFile(app, HighScoreUtil.hardfh);
                }

                else{
                    app.scores = HighScoreUtil.readFile(HighScoreUtil.hellfh);
                    HighScoreUtil.writeToFile(app, HighScoreUtil.hellfh);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        app.score.curMin = 0;
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
                app.soundManager.buttonSound(app);
                app.mainMenuScreen = new MainMenuState(app);
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
            app.font50.draw(app.batch, "VICTORY", Gdx.graphics.getWidth() /2 - 150, app.V_HEIGHT - 70);
            app.font30.draw(app.batch, "Your Score: " + app.score.getScore(), Gdx.graphics.getWidth() /2 - 140, app.V_HEIGHT - 140);
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

    public boolean isWon() {
        return isWon;
    }

    public void setisWon(boolean won) {
        this.isWon = won;
    }
}


