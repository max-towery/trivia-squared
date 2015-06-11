package games.containers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import games.GameStateManager;
import games.interfaces.ButtonInterface;

/**
 * Created by Max Towery on 6/10/2015.
 */
public class PauseButton extends Button implements ButtonInterface{

    public PauseButton(final GameStateManager app){
        super();
        buttonAtlas = app.assets.get("buttons/pause.pack", TextureAtlas.class);
        skin.addRegions(buttonAtlas);
        tbs.up = skin.getDrawable("pauseup");
        tbs.down = skin.getDrawable("pausedown");
        tbs.font = app.font30white;
        button = new TextButton("", tbs);
        hide();
    }



    @Override
    public void hide() {
        this.button.setSize(0,0);
    }

    @Override
    public void show() {
        this.button.setSize(50,50);
    }

    @Override
    public TextButton getButton() {
        return button;
    }

    @Override
    public void setBounds(int x, int y, int w, int h) {

    }

    @Override
    public void setClickListener(final GameStateManager app, boolean check) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                app.soundManager.buttonSound(app);
                app.soundManager.music.pause();
                app.playScreen.pauseNotAutomatic();
                app.score.stopTimer();
                hide();

            }
        });
    }

    public void updateButton(final GameStateManager app){
        if (app.mapScreen.mapRecentlyActive)
            hide();
        if (!app.isCameraMoving()){
            button.setY(app.camera.position.y + (int)(app.V_HEIGHT /2.5));
            button.setX(app.camera.position.x - app.V_WIDTH /3);
            show();
        }
        else
            hide();
    }


    @Override
    public boolean getResult() {
        return false;
    }


}
