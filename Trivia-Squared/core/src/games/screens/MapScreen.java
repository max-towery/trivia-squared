package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static games.Application.GRIDSIZE;
import games.Application;
import games.utils.RoomGenerator;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Max Towery on 5/30/2015.
 */
public class MapScreen implements Screen {

    private Application app;
    private Stage stage;
    public float activeAcc;
    public boolean mapRecentlyActive;

    public Image playerImage;


    private Skin skin;
    private TextButton backButton;
    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle tbs;

    private float fadeAcc;
    private boolean fading;

    public MapScreen(Application app, Stage stage) {
        this.app = app;
        this.stage = stage;
        activeAcc = 4;
    }

    @Override
    public void show() {
        activeAcc = 0.0f;
        fadeAcc = 0.0f;
        fading = false;
        app.camera.zoom = GRIDSIZE + 3;
        app.camera.position.set(app.V_WIDTH * GRIDSIZE /2 , app.V_HEIGHT * GRIDSIZE /2 , 0);


        skin = new Skin();
        tbs = new TextButton.TextButtonStyle();
        buttonAtlas = app.assets.get("buttons/menubutton.pack", TextureAtlas.class);
        skin.addRegions(buttonAtlas);

        tbs.up = skin.getDrawable("up");
        tbs.down = skin.getDrawable("down");
        tbs.font = app.font300white;
        tbs.over = skin.getDrawable("down");
        tbs.downFontColor = com.badlogic.gdx.graphics.Color.RED;

        backButton = new TextButton("GO BACK", tbs);
        backButton.setBounds(app.V_WIDTH * GRIDSIZE /2 - 750, app.V_HEIGHT * GRIDSIZE /2 + (GRIDSIZE /2 * app.V_HEIGHT + 500), 1500, 600);
        stage.addActor(backButton);

        playerImage = new Image(app.assets.get("images/misc/playerPos.png", Texture.class));
        playerImage.setBounds(app.playerLoc[0] + app.V_WIDTH /2 - 100, app.playerLoc[1] + app.V_HEIGHT /2 - 100, 200, 200);
        stage.addActor(playerImage);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(app.playScreen);
                app.camera.zoom = 1;
                mapRecentlyActive = true;
                app.grid[app.endLoc[0]][app.endLoc[1]].getImage2().addAction(fadeIn(1f));
                app.camera.position.set(app.playScreen.xDest, app.playScreen.yDest, 0);
                playerImage.setSize(0, 0);
                app.playScreen.mapButton.hide();
            }
        });

    }

    private void fadeExit(float delta){

        if (fadeAcc <= 1 && !fading){
            fadeAcc += delta;
        }
        else if (fadeAcc <= 0) {
            fading = false;
            app.grid[app.endLoc[0]][app.endLoc[1]].getImage2().addAction(fadeIn(1f));
        }
        else if (fading)
            fadeAcc -= delta;
        else if (fadeAcc >= 1){
            app.grid[app.endLoc[0]][app.endLoc[1]].getImage2().addAction(fadeOut(1f));
            fading = true;
        }

    }

    private void fadeAll(){
        System.out.println(app.playerIndex[0] + " " + app.playerIndex[1]);
        for (int i = 0; i < app.grid.length; i++){
            for(int j = 0; j < app.grid.length; j++){
                if (i == app.endLoc[0] && j == app.endLoc[1])
                    continue;
                else if (i == app.playerIndex[0] && j == app.playerIndex[0]){
                    System.out.println(app.playerIndex[0] + " " +app.playerIndex[1]);
                    continue;
                }
                app.grid[i][j].getImage().addAction(fadeOut(1f));

            }
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        stage.draw();
    }

    private void update(float delta)
    {
        stage.act();
        fadeExit(delta);


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
    }
}
