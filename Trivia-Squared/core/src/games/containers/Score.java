package games.containers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import games.Application;

/**
 * Created by Max Towery on 5/30/2015.
 */
public class Score {

    private int value;


    public Score(int value) {
        this.value = value;


    }
    public void displayPoints(Application app, BitmapFont font, int x, int y){
        font.draw(app.batch, "Points: " + String.valueOf(value), x, y);
    }

    public void increase(){
        value++;
    }


    public boolean hasPointsForHint(){
        if (value > 1)
            return true;
        return false;
    }

    public boolean hasPointsForMap() {
        if (value > 0){
            value--;
            return true;

        }

        return false;
    }
}
