package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import games.Application;
import games.camera.CameraStyles;
import games.containers.ArrowButtons;
import games.containers.MapButton;
import games.utils.RoomGenerator;
import games.utils.InputHandler;

/**
 * Created by Max Towery on 5/28/2015.
 */
public class PlayScreen implements Screen {

    private final Application app;
    public Stage stage;

    //camera stuff
    public int xDest, yDest;
    public MapButton mapButton;
    public ArrowButtons arrowButtons;

    public PlayScreen(Application app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, app.camera));
        app.mapScreen = new MapScreen(app, stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        app.camera.position.set(app.playerLoc[0] + app.V_WIDTH /2, app.playerLoc[1] + app.V_HEIGHT /2, 0);
        if (!app.gridCreated){
            app.grid = RoomGenerator.createImageGrid(app);
            for (int i = 0; i < RoomGenerator.GRIDSIZE; i++){
                for (int j = 0; j < RoomGenerator.GRIDSIZE; j++){
                    stage.addActor(app.grid[i][j].getImage());
                }
            }
            app.camera.position.set(app.playerLoc[0] + app.V_WIDTH /2, app.playerLoc[1] + app.V_HEIGHT /2, 0);
            xDest = (int) app.camera.position.x;
            yDest = (int) app.camera.position.y;
            app.gridCreated = true;
        }
        arrowButtons = new ArrowButtons(app);
        for(int i = 0; i < 4; i++)
            stage.addActor(arrowButtons.getButtons()[i]);

        mapButton = new MapButton(app);
        stage.addActor(mapButton.getButton());


        mapButton.getButton().addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {

                if (!app.isCameraMoving()){
                    if (app.score.hasPointsForMap()){
                        app.setScreen(app.mapScreen);
                        mapButton.getButton().setSize(0,0);
                        for (int i = 0; i < 4; i++)
                            arrowButtons.getButtons()[i].setSize(0,0);
                    }
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
        //put batch stuff here

        app.score.displayPoints(app, app.pointFont, app.V_WIDTH -170, app.V_HEIGHT - 25);
        app.batch.end();

        InputHandler.keyboardInput(app, delta);
    }

    public void cameraUpdate(float delta){

        if (app.mapScreen.activeAcc < 3){
            app.camera.position.set(xDest, yDest, 0);
        }

        CameraStyles.lerpToTarget(app.camera, xDest, yDest);
        if (app.cameraAcc < 4.5)
            app.cameraAcc += delta;

        if (app.mapScreen.mapRecentlyActive)
            app.mapScreen.activeAcc += delta;
    }

    public void update(float delta){
        stage.act(delta);
        cameraUpdate(delta);
        mapButton.updateMapButton(app);
        arrowButtons.updateArrowButtons(app, delta);

    }



    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {
        app.setScreen(app.pauseScreen);
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
