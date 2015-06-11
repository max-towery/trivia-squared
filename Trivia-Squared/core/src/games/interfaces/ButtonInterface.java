package games.interfaces;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import games.GameStateManager;

/**
 * Created by Max Towery on 6/3/2015.
 */
public interface ButtonInterface {

    public abstract void hide();
    public abstract void show();
    public abstract TextButton getButton();
    public abstract void setBounds(int x, int y, int w, int h);
    public abstract void setClickListener(final GameStateManager app, boolean check);
    public abstract boolean getResult();
}
