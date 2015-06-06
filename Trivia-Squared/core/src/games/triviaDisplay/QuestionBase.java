package games.triviaDisplay;

import games.Application;
import games.containers.MenuButton;
import games.containers.QButton;

import java.util.Random;

/**
 * Created by Max Towery on 6/3/2015.
 */
public abstract class QuestionBase {

    protected QButton[] qButtons;
    protected MenuButton hint;
    protected String [] buttonColorsUp;
    protected String [] buttonColorsDown;
    protected String question;
    Random rand;
    public boolean hintEnabled;

    public QuestionBase(final Application app) {
        buttonColorsUp = new String[]{"redup","blueup","yellowup", "greenup"};
        buttonColorsDown = new String [] {"reddown","bluedown","yellowdown","greendown"};
        qButtons = new QButton[4];
        rand = new Random();
        hintEnabled = false;
        app.grid[app.playerIndex[0]][app.playerIndex[1]].question.asked();

    }

    public String getQuestion(){
        return question;
    }

    public abstract void enableHint(final Application app);


    public abstract void enterKeyPressed(Application app);

    public abstract void renderHint(final Application app, float delta);
}
