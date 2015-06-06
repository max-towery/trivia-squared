package games.triviaDisplay;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import games.Application;
import games.containers.MenuButton;
import games.containers.QButton;
import games.utils.Question;

/**
 * Created by Max Towery on 6/3/2015.
 */


public class MultiChoice extends QuestionBase {


    //THIS NEEDS TO BE UPDATED ONCE QUESTIONS ARE IMPLEMENTED
    public MultiChoice(final Application app, Stage stage, Question question) {
        super(app);
        int pad = 60;
        for(int i = 0; i < qButtons.length; i++){
            int randInt = rand.nextInt(4);
            qButtons[i] = new QButton(app, question.choices[i].toString(), buttonColorsUp[randInt], buttonColorsDown[randInt]);
            qButtons[i].setBounds(app.V_WIDTH / 2 - 150, app.V_HEIGHT /6 + pad, 300, 45);
            pad+= 60;
            qButtons[i].setResult(question.choices[i].isTrue());
            qButtons[i].setClickListener(app, qButtons[i].getResult());
            stage.addActor(qButtons[i].getButton());
        }
       // this.question = "This will be a Multiple Choice question. Just you wait.";


        hint = new MenuButton(app, "Hint (1 point)");
        hint.setBounds(app.V_WIDTH /2 - 100, app.V_HEIGHT - 60, 200, 50);
        stage.addActor(hint.getButton());
        enableHint(app);


    }


    @Override
    public void enableHint(final Application app) {
        hint.getButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int randInt;
                int removed = 0;
                while (removed < 2){
                    randInt = rand.nextInt(4);
                    if (qButtons[randInt].getResult() == false){
                        qButtons[randInt].hide();
                        removed++;
                        qButtons[randInt].setResult(true);
                    }
                }
                hint.getButton().remove();

            }
        });
    }

    @Override
    public void enterKeyPressed(Application app) {

    }

    @Override
    public void renderHint(final Application app, float delta) {

    }
}
