package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import games.Application;



import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Max Towery on 5/28/2015.
 */
public class MainMenuScreen implements Screen {

    private final Application app;
    private Stage stage;
    private Image logo;

    private Skin skin;
    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle tbs;

    private TextButton startButton;
    private TextButton quitButton;
    private TextButton loadButton;

    public MainMenuScreen(Application app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Texture logoTex = app.assets.get("images/logo.png", Texture.class);
        logo = new Image(logoTex);
        logo.setOrigin(logo.getWidth() /2 , logo.getHeight() / 2);
        logo.setPosition(stage.getWidth() / 2 + 50, stage.getHeight() / 2 - 60);
        logo.addAction(scaleTo(3f,3f, 3f));
        stage.addActor(logo);

        tbs = new TextButton.TextButtonStyle();
        skin = new Skin();


        //load buttons
        buttonAtlas = app.assets.get("buttons/menubutton.pack", TextureAtlas.class);


        skin.addRegions(buttonAtlas);

        tbs.up = skin.getDrawable("up");
        tbs.down = skin.getDrawable("down");
        tbs.font = app.font30white;
        tbs.over = skin.getDrawable("down");
        tbs.downFontColor = Color.RED;


        startButton = new TextButton("Start Game", tbs);
        quitButton = new TextButton("Quit Game", tbs);
        loadButton = new TextButton("Load Game", tbs);

        startButton.setBounds(20, 280, 200, 45);
        loadButton.setBounds(20, 210, 200, 45);
        quitButton.setBounds(20, 140, 200, 45);


        stage.addActor(startButton);
        stage.addActor(quitButton);
        stage.addActor(loadButton);

        quitButton.addListener(new ClickListener(){
           @Override
            public void clicked(InputEvent event, float x, float y){
               Gdx.app.exit();
           }
        });

        startButton.addListener(new ClickListener(){
           @Override
            public void clicked(InputEvent event, float x, float y){
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
