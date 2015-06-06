package games.containers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Max Towery on 6/3/2015.
 */
public abstract class Button{

    protected Skin skin;
    protected TextureAtlas buttonAtlas;
    protected TextButton.TextButtonStyle tbs;
    protected TextButton button;

    public Button(){
        tbs = new TextButton.TextButtonStyle();
        this.skin = new Skin();
    }
}