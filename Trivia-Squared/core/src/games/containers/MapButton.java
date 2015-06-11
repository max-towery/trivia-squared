package games.containers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import games.GameStateManager;
import games.interfaces.ButtonInterface;


/**
 * Created by Max Towery on 5/30/2015.
 */
public class MapButton extends Button implements ButtonInterface{

    public MapButton(final GameStateManager app){
        super();
        buttonAtlas = app.assets.get("buttons/map.pack", TextureAtlas.class);
        skin.addRegions(buttonAtlas);
        tbs.up = skin.getDrawable("mapup");
        tbs.down = skin.getDrawable("mapdown");
        tbs.font = app.font30white;
        button = new TextButton("", tbs);
        hide();
    }
    public TextButton getButton() {
        return button;
    }

    @Override
    public void setBounds(int x, int y, int w, int h) {
    }

    @Override
    public void setClickListener(final GameStateManager app, boolean check) {
    }


    @Override
    public boolean getResult() {
        return false;
    }

    public void updateButton(final GameStateManager app){
        if (app.mapScreen.mapRecentlyActive)
            hide();
        if (!app.isCameraMoving()){
            button.setY(app.camera.position.y + (int)(app.V_HEIGHT /2.5));
            button.setX(app.camera.position.x - app.V_WIDTH /2);
            show();
        }
        else
            hide();
    }

    public void hide(){
        this.button.setSize(0,0);
    }
    public void show(){
        this.button.setSize(50,50);
    }

}
