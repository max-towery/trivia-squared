package games.containers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import games.Application;
import games.interfaces.ButtonInterface;



/**
 * Created by Max Towery on 5/30/2015.
 */
public class MapButton extends Button implements ButtonInterface{

    public MapButton(final Application app){
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
    public void setClickListener(final Application app, boolean check) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {

               if (!app.isCameraMoving()) {
                    if (app.score.hasPointsForMap()) {
                        app.setScreen(app.mapScreen);
                        hide();

                        for (int i = 0; i < 4; i++)
                            app.playScreen.arrowButtons.getButtons()[i].setSize(0, 0);
                    }
                }
            }
        });
    }

    @Override
    public void setText(String text) {
        button.setText(text);
    }

    @Override
    public boolean getResult() {
        return false;
    }

    public void updateButton(final Application app){


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
