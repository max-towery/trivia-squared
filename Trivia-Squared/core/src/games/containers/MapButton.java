package games.containers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import games.Application;

import java.awt.event.InputEvent;

/**
 * Created by Max Towery on 5/30/2015.
 */
public class MapButton {
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private Button.ButtonStyle bs;

    private Button button;

    public MapButton(final Application app){

        //init stuff
        skin = new Skin();
        bs = new Button.ButtonStyle();
        buttonAtlas = app.assets.get("buttons/map.pack", TextureAtlas.class);
        skin.addRegions(buttonAtlas);
        bs.up = skin.getDrawable("mapup");
        bs.down = skin.getDrawable("mapdown");
        button = new Button(bs);
        updateMapButton(app);

    }
    public Button getButton() {
        return button;
    }

    public void updateMapButton(final Application app){

        button.setY(app.camera.position.y + 180);
        button.setX(app.camera.position.x - 350);
    }


}
