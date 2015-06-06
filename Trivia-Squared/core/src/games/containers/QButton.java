package games.containers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import games.Application;
import games.interfaces.ButtonInterface;

/**
 * Created by Max Towery on 6/3/2015.
 */
public class QButton extends Button implements ButtonInterface {

    boolean result; //true = correct, false = incorrect
    public QButton(final Application app, String text, String up, String down){
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

    public void setClickListener(final Application app, final boolean check){

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
                }
            }
        });
    }

    @Override
    public void setText(String text) {
        button.setText(text);
    }

    public boolean getResult() {
        return result;
    }
}
