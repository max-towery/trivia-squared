package games.containers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import games.GameStateManager;
import games.interfaces.ButtonInterface;
import games.screens.EndGameState;
import games.utils.gameUtils.PathChecker;

/**
 * Created by Max Towery on 6/3/2015.
 */
public class QButton extends Button implements ButtonInterface {

    private boolean result; //true = correct, false = incorrect
    public QButton(final GameStateManager app, String text, String up, String down){
        super();
        buttonAtlas = app.assets.get("buttons/qButton.pack", TextureAtlas.class);
        skin.addRegions(buttonAtlas);
        tbs.up = skin.getDrawable(up);
        tbs.down = skin.getDrawable(down);
        tbs.font = app.font30white;
        tbs.over = skin.getDrawable(down);
        button = new TextButton(text, tbs);
        //default bounds
        setBounds(app.V_WIDTH /2 - 100, app.V_HEIGHT /2, 200, 45);
        result = false;
    }

    @Override
    public void hide() {
        button.remove();
    }

    @Override
    public void show() {
    }

    @Override
    public TextButton getButton() {
        return this.button;
    }

    @Override
    public void setBounds(int x, int y, int w, int h) {
        this.button.setBounds(x,y,w,h);
    }

    public void setResult(boolean result){
        this.result = result;
    }

    public void setClickListener(final GameStateManager app, final boolean check){

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.soundManager.buttonSound(app);
                if (check){

                    app.triviaScreen.setAnsweredCorrectly(app);
                    app.setScreen(app.playScreen);
                    app.camera.position.set(app.playScreen.xDest, app.playScreen.yDest, 0);
                    if (app.mapScreen.playerImage != null)
                        app.mapScreen.playerImage.setSize(0, 0);
                    app.score.increase();
                    app.updatePlayerData();
                    app.checkIfPlayerWon();
                }
                else{
                    if (app.mapScreen.playerImage != null)
                        app.mapScreen.playerImage.setSize(0, 0);
                    app.updateImageData();
                    app.setScreen(app.playScreen);
                    boolean isPath = PathChecker.checkPaths(app.grid, app.playerIndex[0], app.playerIndex[1]);
                    if (!isPath){
                        app.endGameScreen = new EndGameState(app);
                        app.endGameScreen.setisWon(false);
                        app.setScreen(app.endGameScreen);
                    }
                }
            }
        });
    }

    public boolean getResult() {
        return result;
    }
}
