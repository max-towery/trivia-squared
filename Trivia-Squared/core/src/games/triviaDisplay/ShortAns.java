package games.triviaDisplay;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import games.Application;
import games.containers.QButton;

/**
 * Created by Max Towery on 6/3/2015.
 */
public class ShortAns extends QuestionBase {

    public TextField textField;
    public Skin skin;
    //THIS NEEDS TO BE UPDATED ONCE QUESTIONS ARE IMPLEMENTED
    public ShortAns(final Application app, Stage stage) {
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
        question = "This will be a Short Answer question. Just you wait.";
    }

    public void setListenerForShortAnswer(final Application app, TextButton button){
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final boolean check =
                        app.grid[app.playerIndex[0]][app.playerIndex[1]].answer.equalsIgnoreCase(textField.getText());
                if (check){
                    app.setScreen(app.playScreen);
                    app.camera.position.set(app.playScreen.xDest, app.playScreen.yDest, 0);
                    if (app.mapScreen.playerImage != null)
                        app.mapScreen.playerImage.setSize(0, 0);

                    app.updatePlayerData();
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
    public void enableHint(final Application app) {

    }

    public void enterKeyPressed(final Application app){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if (app.grid[app.playerIndex[0]][app.playerIndex[1]].answer.equalsIgnoreCase(textField.getText())){
                app.setScreen(app.playScreen);
                app.camera.position.set(app.playScreen.xDest, app.playScreen.yDest, 0);
                if (app.mapScreen.playerImage != null)
                    app.mapScreen.playerImage.setSize(0, 0);

                app.updatePlayerData();
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
    public void renderHint(final Application app, float delta) {

    }

}
