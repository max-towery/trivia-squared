package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import games.GameStateManager;
import games.camera.CameraStyles;
import games.containers.ArrowButtons;
import games.containers.MapButton;
import games.containers.PauseButton;
import games.utils.gameUtils.RoomGenerator;
import games.utils.gameUtils.InputHandler;

import java.util.Calendar;

import static games.GameStateManager.GRIDSIZE;
/**
 * Created by Max Towery on 5/28/2015.
 */
public class PlayState implements Screen {

    private final GameStateManager app;
    public Stage stage;
    public boolean isNewGame;

    //camera stuff
    public int xDest, yDest;
    public MapButton mapButton;
    public PauseButton pauseButton;
    public ArrowButtons arrowButtons;


    public PlayState(GameStateManager app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT, app.camera));
        app.mapScreen = new MapState(app, stage);
        app.score.startCal = Calendar.getInstance();
        app.score.startTime = (int)app.score.startCal.getTimeInMillis() /1000;



    }

    @Override
    public void show() {
        app.soundManager.playMusic(app);
        app.score.gameActive = true;
        //time stuff

        app.score.calculateScore(app);


        Gdx.input.setInputProcessor(stage);
        app.camera.position.set(app.playerLoc[0] + app.V_WIDTH /2, app.playerLoc[1] + app.V_HEIGHT /2, 0);


        if (isNewGame){
            isNewGame = false;
            app.score.startCal = Calendar.getInstance();
            app.score.startTime = (int)app.score.startCal.getTimeInMillis() /1000;
            app.score.points = 1;
            app.grid = RoomGenerator.createImageGrid(app);
            for (int i = 0; i < GRIDSIZE; i++){
                for (int j = 0; j < GRIDSIZE; j++){
                    stage.addActor(app.grid[i][j].getImage());
                    if (app.grid[i][j].getImage2() != null)
                        stage.addActor(app.grid[i][j].getImage2());
                }
            }
            app.camera.position.set(app.playerLoc[0] + app.V_WIDTH /2, app.playerLoc[1] + app.V_HEIGHT /2, 0);
            xDest = (int) app.camera.position.x;
            yDest = (int) app.camera.position.y;
            app.gridCreated = true;
        }
        else if (!app.gridCreated){
            app.grid = RoomGenerator.createImageGrid(app);
            for (int i = 0; i < GRIDSIZE; i++){
                for (int j = 0; j < GRIDSIZE; j++){
                    stage.addActor(app.grid[i][j].getImage());
                    if (app.grid[i][j].getImage2() != null)
                        stage.addActor(app.grid[i][j].getImage2());
                }
            }
            app.camera.position.set(app.playerLoc[0] + app.V_WIDTH /2, app.playerLoc[1] + app.V_HEIGHT /2, 0);
            xDest = (int) app.camera.position.x;
            yDest = (int) app.camera.position.y;
            app.gridCreated = true;
        }

            pauseButton = new PauseButton(app);
            arrowButtons = new ArrowButtons(app);
            mapButton = new MapButton(app);
            for(int i = 0; i < 4; i++)
                stage.addActor(arrowButtons.getButtons()[i]);
            stage.addActor(mapButton.getButton());
            stage.addActor(pauseButton.getButton());
        mapButton.getButton().addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                app.soundManager.buttonSound(app);
                if (!app.isCameraMoving()){
                    if (app.score.hasPointsForMap()){
                        app.setScreen(app.mapScreen);
                        mapButton.hide();
                        pauseButton.hide();

                        for (int i = 0; i < 4; i++)
                            arrowButtons.getButtons()[i].setSize(0,0);
                    }
                }
            }
        });
        pauseButton.setClickListener(app, true);




    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
        mapButton.updateButton(app);
        pauseButton.updateButton(app);
        arrowButtons.updateArrowButtons(app);
        app.batch.begin();
        //put batch stuff here

        app.score.displayPoints(app, app.pointFont, app.V_WIDTH -170, app.V_HEIGHT - 25);
        app.score.displayTime(app, app.timeFont, app.V_WIDTH - (int) (app.V_WIDTH / 2.7), app.V_HEIGHT - 25);
        app.batch.end();

        InputHandler.keyboardInput(app, delta);
    }

    public void cameraUpdate(float delta){

        CameraStyles.lerpToTarget(app.camera, xDest, yDest);
        if (app.cameraAcc < 4.5)
            app.cameraAcc += delta;

        if (app.mapScreen.mapRecentlyActive)
            app.mapScreen.activeAcc += delta;

    }

    public void update(float delta){
        stage.act(delta);
        cameraUpdate(delta);

    }



    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {
    }

    public void pauseNotAutomatic(){
        app.pauseScreen = new PauseState(app);
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
