package games.triviaDisplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import games.GameStateManager;
import games.containers.MenuButton;
import games.containers.QButton;
import games.utils.dbUtils.Question;

import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Max Towery on 6/3/2015.
 */
public class TrueFalse extends QuestionBase {

    float hintAcc;


    //THIS NEEDS TO BE UPDATED ONCE QUESTIONS ARE IMPLEMENTED
    public TrueFalse(final GameStateManager app, Stage stage, Question question) {
        super(app);
        qButtons = new QButton[2];

        int pad = 60;
            int randInt = rand.nextInt(4);
            qButtons[0] = new QButton(app, question.choices[1].toString(), buttonColorsUp[randInt], buttonColorsDown[randInt]);
            qButtons[0].setBounds(app.V_WIDTH / 2 - 150, app.V_HEIGHT / 6 + pad, 300, 45);
            pad+= 60;
            qButtons[0].setResult(question.choices[0].isTrue());
            qButtons[0].setClickListener(app, question.choices[0].isTrue());
            stage.addActor(qButtons[0].getButton());
            qButtons[1] = new QButton(app, question.choices[0].toString(), buttonColorsUp[randInt], buttonColorsDown[randInt]);
            qButtons[1].setBounds(app.V_WIDTH / 2 - 150, app.V_HEIGHT /6 + pad, 300, 45);
            pad+= 60;
            qButtons[1].setResult(question.choices[1].isTrue());
            qButtons[1].setClickListener(app, question.choices[1].isTrue());
            stage.addActor(qButtons[1].getButton());

        hint = new MenuButton(app, "Hint");
        hint.setBounds(app.V_WIDTH / 2 - 50, app.V_HEIGHT - 60, 100, 50);
        stage.addActor(hint.getButton());

        enableHint(app);

    }


    @Override
    public void enableHint(final GameStateManager app) {
        hint.getButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (app.score.hasPointsForHint()){
                    hint.getButton().remove();
                    hintEnabled = true;

                    final float xDim = qButtons[0].getButton().getX();
                    final float yDim0 = qButtons[0].getButton().getY();
                    final float yDim1 = qButtons[1].getButton().getY();
                    final int button0 = rand.nextInt(app.V_WIDTH - (int)qButtons[0].getButton().getWidth());
                    final int button1 = rand.nextInt(app.V_WIDTH - (int)qButtons[0].getButton().getWidth());

                    Timer.schedule(new Timer.Task(){


                        Random rand = new Random();
                        int b1 = rand.nextInt(5)+5;
                        int b0 = b1 - 1;


                        @Override
                        public void run() {

                            qButtons[0].getButton().addAction(sequence(moveTo(button0, yDim0, 4), moveTo(xDim, yDim0, 4)));
                            qButtons[1].getButton().addAction(sequence(moveTo(button1 , yDim1, 4), moveTo(xDim, yDim1, 4)));

                            if (qButtons[0].getResult())
                                b0+= 1; b1 -= 2;
                            //correct answer
                            for (int i = 0; i < b1; i++){
                                Timer.schedule(new Timer.Task(){
                                    @Override
                                    public void run(){
                                        qButtons[1].getButton().addAction(sequence(fadeOut((float) (rand.nextFloat() + .25)), fadeIn((float) (rand.nextFloat()+.25))));
                                    }
                                }, i);
                            }
                            //incorrect answer
                            for (int i = 0; i < b0; i++){
                                Timer.schedule(new Timer.Task(){
                                    @Override
                                    public void run(){
                                        qButtons[0].getButton().addAction(sequence(fadeOut((float) (rand.nextFloat() + .25)), fadeIn((float) (rand.nextFloat()+.25))));
                                    }
                                }, i);
                            }
                        }

                    }, 6);
                }

            }
        });

    }

    @Override
    public void renderHint(final GameStateManager app, float delta){

        hintAcc+= delta;
        if (hintAcc > 0 &&  hintAcc < 1.5){
            app.font20.drawWrapped(app.batch, "Count which answer fades in and out more", Gdx.graphics.getWidth() / 2 - 200, app.V_HEIGHT / 2  + 100, 800);
        }
        else if (hintAcc > 2 && hintAcc < 3.5){
            app.font20.drawWrapped(app.batch, "The higher count wins", Gdx.graphics.getWidth() / 2 - 100, app.V_HEIGHT/2 + 100, 800);
        }
        else if (hintAcc > 4 && hintAcc < 5.5){
            app.font20.drawWrapped(app.batch, "GET READY...", Gdx.graphics.getWidth() / 2 -50, app.V_HEIGHT/2 + 100, 800);
        }
    }

    @Override
    public void enterKeyPressed(GameStateManager app) {

    }
}
