package games.containers;

import games.Application;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import games.interfaces.ButtonInterface;

/**
 * Created by Max Towery on 6/3/2015.
 */
public class MenuButton extends Button implements ButtonInterface{


    public MenuButton(final Application app, String text){
        super();
        buttonAtlas = app.assets.get("buttons/menubutton.pack", TextureAtlas.class);
        skin.addRegions(buttonAtlas);
        tbs.up = skin.getDrawable("up");
        tbs.down = skin.getDrawable("down");
        tbs.font = app.font30white;
        tbs.over = skin.getDrawable("down");
        button = new TextButton(text, tbs);
        //default bounds
        setBounds(app.V_WIDTH /2 - 100, app.V_HEIGHT /2, 200, 45);
    }

    @Override
    public void setBounds(int x, int y, int w, int h){
        button.setBounds(x, y, w, h);
    }

    @Override
    public void setClickListener(Application app, boolean check) {

    }


    @Override
    public void hide() {
    }

    @Override
    public void show() {
    }

    @Override
    public TextButton getButton() {
        return button;
    }

    @Override
    public void setText(String text) {
        button.setText(text);
    }

    @Override
    public boolean getResult() {
        return false;
    }


}
