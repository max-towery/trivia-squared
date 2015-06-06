package games.interfaces;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import games.Application;

/**
 * Created by Max Towery on 6/3/2015.
 */
public interface ButtonInterface {

    public abstract void hide();
    public abstract void show();
    public abstract TextButton getButton();
    public abstract void setBounds(int x, int y, int w, int h);
    public abstract void setClickListener(final Application app, boolean check);
    public abstract void setText(String text);

    public abstract boolean getResult();
}
