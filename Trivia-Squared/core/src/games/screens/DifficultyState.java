package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import games.GameStateManager;
import games.containers.MenuButton;

/**
 * Created by Max Towery on 6/5/2015.
 */
public class DifficultyState implements Screen {

    private final GameStateManager app;
    private Stage stage;

    private MenuButton easy;
    private MenuButton medium;
    private MenuButton hard;
    private MenuButton hell;

    private int difficulty;
    private MenuButton mainMenu;

    private TextField textField;
    private Skin skin;

    public DifficultyState(GameStateManager app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        easy = new MenuButton(app, "Easy");
        medium = new MenuButton(app, "Medium");
        hard = new MenuButton(app, "Hard");
        hell = new MenuButton(app, "Hell");
        mainMenu = new MenuButton(app, "Main Menu");

        int buttonHeight = app.V_HEIGHT / 7;
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


        skin = new Skin(Gdx.files.internal("uiskin.json"));
        textField = new TextField("", skin);
        textField.setBounds(app.V_WIDTH /2 - 150, app.V_HEIGHT / 2 + 200, 300, 45);
        stage.setKeyboardFocus(textField);
        stage.addActor(textField);

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
                if (isName(app)){
                    app.GRIDSIZE = 4;
                    app.playScreen.isNewGame = true;
                    difficulty = 4;
                    app.setScreen(app.playScreen);
                }

            }
        });
        medium.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                if (isName(app)){
                    app.GRIDSIZE = 6;
                    app.playScreen.isNewGame = true;
                    difficulty = 3;
                    app.setScreen(app.playScreen);
                }

            }
        });
        hard.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                if (isName(app)){
                    app.GRIDSIZE = 8;
                    app.playScreen.isNewGame = true;
                    difficulty = 2;
                    app.setScreen(app.playScreen);
                }

            }
        });
        hell.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                if (isName(app)){
                    app.GRIDSIZE = 12;
                    difficulty = 1;
                    app.playScreen.isNewGame = true;
                    app.setScreen(app.playScreen);
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
        app.font30.draw(app.batch, "SELECT DIFFICULTY", Gdx.graphics.getWidth() /2 - 145, app.V_HEIGHT/2 + 100);
        app.font30.draw(app.batch, "ENTER YOUR NAME", Gdx.graphics.getWidth() /2 - 150, app.V_HEIGHT/2 + 300);
        app.batch.end();
    }

    public void update(float delta){
        stage.act(delta);
    }

    private boolean isName(GameStateManager app){
        if (textField.getText().length() == 0){
            return false;
        }
        app.username = textField.getText();
        return true;
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

    public int getDifficulty() {
        return difficulty;
    }
}
