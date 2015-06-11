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
import games.utils.gameUtils.FileUtil;
import games.utils.gameUtils.InputHandler;
import games.utils.gameUtils.SaveGameUtil;

import java.io.IOException;

/**
 * Created by Max Towery on 5/29/2015.
 */
public class PauseState implements Screen{

    private final GameStateManager app;
    private Stage stage;

    private MenuButton quitButton;
    private MenuButton resumeButton;
    private MenuButton manualButton;
    private MenuButton saveButton;

    public boolean recentlyActive;

    public PauseState(GameStateManager app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        app.score.gameActive = false;
        Gdx.input.setInputProcessor(stage);
        recentlyActive = true;

        saveButton = new MenuButton(app, "Save Game");
        resumeButton = new MenuButton(app, "Resume Game");
        manualButton = new MenuButton(app, "User Manual");
        quitButton = new MenuButton(app, "Quit Game");

        int buttonHeight = app.V_HEIGHT / 5;
        quitButton.setBounds(app.V_WIDTH / 2 - 125, buttonHeight, 250, 45);
        manualButton.setBounds(app.V_WIDTH / 2 - 125, buttonHeight += 60, 250, 45);
        saveButton.setBounds(app.V_WIDTH / 2 - 125, buttonHeight += 60, 250, 45);
        resumeButton.setBounds(app.V_WIDTH / 2 - 125, buttonHeight += 100, 250, 45);

        stage.addActor(quitButton.getButton());
        stage.addActor(manualButton.getButton());
        stage.addActor(resumeButton.getButton());
        stage.addActor(saveButton.getButton());

        quitButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                app.mainMenuScreen = new MainMenuState(app);
                app.setScreen(app.mainMenuScreen);
                app.soundManager.music.stop();
                app.score.curMin = 0;
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
        resumeButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                app.soundManager.music.play();
                app.playScreen.mapButton.hide();
                app.playScreen.arrowButtons.hide();
                app.playScreen.pauseButton.hide();
                app.setScreen(app.playScreen);
                app.score.timeResume();

            }
        });
        saveButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);

            }
        });





    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        app.batch.begin();
        app.font50.draw(app.batch, "PAUSED", app.V_WIDTH/2 - 110, app.V_HEIGHT -150);
        app.batch.end();

        try {
            SaveGameUtil.saveGame(app);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputHandler.resumeGameListener(app);
    }

    private void update(float delta){
        stage.act(delta);
        stage.draw();
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
