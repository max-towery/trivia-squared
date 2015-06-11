package games.triviaDisplay;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import games.GameStateManager;
import games.containers.MenuButton;
import games.containers.QButton;
import games.utils.dbUtils.Question;

import java.util.Random;

/**
 * Created by Max Towery on 6/3/2015.
 */
public class ShortAns extends QuestionBase {

    public TextField textField;
    public Skin skin;
    float hintAcc;
    String scrambledWord;
    String padding;

    public String answer;
    //THIS NEEDS TO BE UPDATED ONCE QUESTIONS ARE IMPLEMENTED
    public ShortAns(final GameStateManager app, Stage stage, Question question) {
        super(app);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        textField = new TextField("", skin);
        textField.setBounds(app.V_WIDTH /2 - 150, app.V_HEIGHT / 6 + 100, 300, 45);
        stage.setKeyboardFocus(textField);
        stage.addActor(textField);
        qButtons = new QButton[1];
        int randInt = rand.nextInt(4);
        qButtons[0] = new QButton(app, "SUBMIT", buttonColorsUp[randInt], buttonColorsDown[randInt]);
        qButtons[0].setBounds(app.V_WIDTH / 2 - 150, app.V_HEIGHT / 6, 300, 45);
        setListenerForShortAnswer(app, qButtons[0].getButton());
        stage.addActor(qButtons[0].getButton());

        this.answer = question.getAnswer();

        hint = new MenuButton(app, "Hint");
        hint.setBounds(app.V_WIDTH / 2 - 50, app.V_HEIGHT - 60, 100, 50);
        stage.addActor(hint.getButton());

        scrambledWord = question.getAnswer();
        enableHint(app);

    }

    public void setListenerForShortAnswer(final GameStateManager app, TextButton button){
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final boolean check =
                        answer.equalsIgnoreCase(textField.getText());
                if (check){
                    app.setScreen(app.playScreen);
                    app.camera.position.set(app.playScreen.xDest, app.playScreen.yDest, 0);
                    if (app.mapScreen.playerImage != null)
                        app.mapScreen.playerImage.setSize(0, 0);
                    app.score.increase();
                    app.updatePlayerData();
                    app.checkIfPlayerWon();
                }
                else{
                    if (app.mapScreen.playerImage != null)
                        app.mapScreen.playerImage.setSize(0, 0);
                    app.updateImageData();
                    app.setScreen(app.playScreen);
                }
            }
        });
    }

    @Override
    public void enableHint(final GameStateManager app) {
        hint.getButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (app.score.hasPointsForHint()){

                    hint.getButton().remove();
                    hintEnabled = true;
                }


            }
        });

        hintAcc = 0;

        int maxLength = 20 - scrambledWord.length();
        for(int i = 0; i < maxLength; i++){
            padding += " ";
        }
    }


    public void enterKeyPressed(final GameStateManager app){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if (answer.equalsIgnoreCase(textField.getText())){
                app.setScreen(app.playScreen);
                app.camera.position.set(app.playScreen.xDest, app.playScreen.yDest, 0);
                if (app.mapScreen.playerImage != null)
                    app.mapScreen.playerImage.setSize(0, 0);
                app.score.increase();
                app.updatePlayerData();
                app.checkIfPlayerWon();
            }
            else{
                if (app.mapScreen.playerImage != null)
                    app.mapScreen.playerImage.setSize(0, 0);
                app.updateImageData();
                app.setScreen(app.playScreen);

            }
        }
    }

    @Override
    public void renderHint(final GameStateManager app, float delta) {

        hintAcc+= delta;
        if (hintAcc > .10){
            scrambledWord = scrambleWord(scrambledWord);
            app.font20.drawWrapped(app.batch, scrambleWord(scrambledWord), Gdx.graphics.getWidth() / 2 - scrambledWord.length() - padding.length(), app.V_HEIGHT / 2  + 100, 800);
            hintAcc = 0;
        }
        else
            app.font20.drawWrapped(app.batch, scrambledWord, Gdx.graphics.getWidth() / 2 - scrambledWord.length() - padding.length(), app.V_HEIGHT / 2  + 100, 800);

    }

    public String scrambleWord(String string){
        Random rand = new Random();
        String temp;
        String [] word = string.trim().split("");
        for(int i = 0; i < word.length; i++){
            int index = rand.nextInt(word.length);
            temp = word[i];
            word[i] = word[index];
            word[index] = temp;
        }
        String ret = "";
        for(int i = 0; i < word.length; i++){
            ret = ret + word[i];
        }
        return ret;

    }

}
