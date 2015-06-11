package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import games.GameStateManager;
import games.containers.MenuButton;

/**
 * Created by Max Towery on 6/7/2015.
 */
public class AdminState implements Screen{

    //temp password for testing
    private String password = "soury01";

    private boolean loginError;
    private boolean loggedIn;

    private GameStateManager app;
    private Stage stage;
    private float loginAcc;

    private boolean saEnabled;
    private boolean tfEnabled;
    private boolean mcEnabled;

    private MenuButton loginButton;
    private MenuButton mainMenu;

    private MenuButton mcButton;
    private MenuButton tfButton;
    private MenuButton saButton;
    private MenuButton submit;

    private TextField pwTextField;
    //short answer fields
    private TextField saQuestion, saAnswer;
    //multiple choice fields
    private TextField mcQuestion, mcAnswer1, mcAnswer2, mcAnswer3, mcAnswer4;
    //true/false fields
    private TextField tfQuestion;
    private MenuButton tAnswer, fAnswer;
    private boolean isTrue = false;


    private Skin skin;


    public AdminState(final GameStateManager app) {
        this.app = app;
        this.stage = new Stage(new StretchViewport(GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT, app.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        loggedIn = false;
        loginAcc = 0;
        loginError = false;
        mcEnabled = false; tfEnabled = false; saEnabled = false;
        loginButton = new MenuButton(app, "Login");
        mainMenu = new MenuButton(app, "Main Menu");

        mcButton = new MenuButton(app, "Multiple Choice");
        tfButton = new MenuButton(app, "True/False");
        saButton = new MenuButton(app, "Short Answer");

        int buttonHeight = app.V_HEIGHT / 3;
        mcButton.setBounds(app.V_WIDTH / 2  - 150, buttonHeight += 60, 300, 45);
        tfButton.setBounds(app.V_WIDTH / 2  - 150, buttonHeight += 60, 300, 45);
        saButton.setBounds(app.V_WIDTH / 2  - 150, buttonHeight += 60, 300, 45);


        buttonHeight = app.V_HEIGHT / 7;
        mainMenu.setBounds(app.V_WIDTH / 2 - 100, buttonHeight, 200, 45);
        loginButton.setBounds(app.V_WIDTH / 2  - 100, buttonHeight += 300, 200, 45);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        pwTextField = new TextField("", skin);
        pwTextField.setBounds(app.V_WIDTH / 2 - 100, buttonHeight += 60, 200, 45);
        pwTextField.setPasswordCharacter('*');
        pwTextField.setPasswordMode(true);
        stage.setKeyboardFocus(pwTextField);
        stage.addActor(pwTextField);
        stage.addActor(mainMenu.getButton());
        stage.addActor(loginButton.getButton());
        mainMenu.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.holder.close();
                app.soundManager.buttonSound(app);
                app.mainMenuScreen = new MainMenuState(app);
                app.setScreen(app.mainMenuScreen);
            }
        });
        loginButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.soundManager.buttonSound(app);
                if (pwTextField.getText().equals(password)){
                    loggedIn = true;
                    pwTextField.remove();
                    loginButton.getButton().remove();
                    app.holder.openDB();
                    adminQuestionSelect();
                }
                else{
                    loginError = true;
                    pwTextField.setText("");
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
        app.batch.begin();
        messageDisplay(delta);

        app.batch.end();
    }

    private void messageDisplay(float delta){

        if (!loggedIn){
            if (!loginError){
                app.font30.draw(app.batch, "Enter Administrator Password", Gdx.graphics.getWidth() /2 - 220, app.V_HEIGHT/2 + 200);
                loginAcc = 0;
            }
            else if (loginError && loginAcc < 2){
                app.font30red.draw(app.batch, "Login Error: Incorrect Password", Gdx.graphics.getWidth() / 2 - 230, app.V_HEIGHT / 2 + 200);
                loginAcc += delta;
                if (loginAcc > 2)
                    loginError = false;
            }
        }
        else if (loggedIn && !(mcEnabled || tfEnabled || saEnabled)){
            app.font30.draw(app.batch, "Select Question type to add to the database", Gdx.graphics.getWidth() /2 - 320, app.V_HEIGHT/2 + 200);
        }
        else{
            if (saEnabled){
                app.font30.draw(app.batch, "Enter short answer question into database", Gdx.graphics.getWidth() /2 - 320, app.V_HEIGHT/2 + 300);
                app.font30.draw(app.batch, "Question:", 20, app.V_HEIGHT/2 + 140);
                app.font30.draw(app.batch, "Answer:", 20, app.V_HEIGHT/2 + 90);
            }
            if (tfEnabled){
                app.font30.draw(app.batch, "Enter true/false question into database", Gdx.graphics.getWidth() /2 - 330, app.V_HEIGHT/2 + 300);
                app.font30.draw(app.batch, "Question:", 20, app.V_HEIGHT/2 + 140);
                app.font30.draw(app.batch, "Choose Correct Answer", app.V_WIDTH / 2 - 80, app.V_HEIGHT/2  + 50);
            }
            if (mcEnabled){
                app.font30.draw(app.batch, "Enter multiple choice question into database", Gdx.graphics.getWidth() /2 - 320, app.V_HEIGHT/2 + 300);
                app.font30.draw(app.batch, "Question:", 20, app.V_HEIGHT/2 + 140);
                app.font30.draw(app.batch, "(correct answer)", app.V_WIDTH / 2 - 20, app.V_HEIGHT/2  + 80);
                app.font30.draw(app.batch, "Question 1:", 20, app.V_HEIGHT/2 + 80);
                app.font30.draw(app.batch, "Question 2 :", 20, app.V_HEIGHT/2 + 30);
                app.font30.draw(app.batch, "Question 3:", 20, app.V_HEIGHT/2 - 20);
                app.font30.draw(app.batch, "Question 4:", 20, app.V_HEIGHT/2 - 70);
            }
            mcButton.getButton().remove();
            saButton.getButton().remove();
            tfButton.getButton().remove();
        }
    }

    private void adminQuestionSelect(){
        stage.addActor(mcButton.getButton());
        stage.addActor(saButton.getButton());
        stage.addActor(tfButton.getButton());

        mcButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                mcMode(true);
            }
        });

        saButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                saMode(true);
            }
        });

        tfButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                tfMode(true);
            }
        });
    }

    public void enterKeyPressed(final GameStateManager app){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if (pwTextField.getText().equals(password)){
                loggedIn = true;
                app.holder.openDB();
                pwTextField.remove();
                loginButton.getButton().remove();
                adminQuestionSelect();
            }
            else{
                pwTextField.setText("");
                loginError = true;

            }
        }
    }

    private void mcMode(boolean check){
        if (check){
            mcEnabled = true;
            if (mcQuestion == null){
                mcQuestion = new TextField("", skin);
                mcQuestion.setBounds(app.V_WIDTH / 6, app.V_HEIGHT/2 + 100, (app.V_WIDTH - (app.V_WIDTH /4)), 45);

                mcAnswer1 = new TextField("", skin);
                mcAnswer2 = new TextField("", skin);
                mcAnswer3 = new TextField("", skin);
                mcAnswer4 = new TextField("", skin);
                mcAnswer4.setBounds(app.V_WIDTH / 4, app.V_HEIGHT / 2 + 50, 200, 45);
                mcAnswer3.setBounds(app.V_WIDTH / 4, app.V_HEIGHT / 2, 200, 45);
                mcAnswer2.setBounds(app.V_WIDTH / 4, app.V_HEIGHT / 2 - 50, 200, 45);
                mcAnswer1.setBounds(app.V_WIDTH / 4, app.V_HEIGHT / 2 - 100, 200, 45);
                enableSubmitButton(app.V_WIDTH /2 - 175, app.V_HEIGHT /2 - 160);
            }
            stage.addActor(mcQuestion);
            stage.addActor(mcAnswer1);
            stage.addActor(mcAnswer2);
            stage.addActor(mcAnswer3);
            stage.addActor(mcAnswer4);
            stage.addActor(submit.getButton());
        }
        else{
            if (mcEnabled){
                mcEnabled = false;
                mcQuestion.remove();
                mcAnswer1.remove();
                mcAnswer2.remove();
                mcAnswer3.remove();
                mcAnswer4.remove();
                adminQuestionSelect();
                submit.getButton().remove();
            }
        }
    }

    private void saMode(boolean check){
        if (check){
            saEnabled = true;
            if (saQuestion == null){
                saQuestion = new TextField("", skin);
                saQuestion.setBounds(app.V_WIDTH / 6, app.V_HEIGHT/2 + 100, (app.V_WIDTH - (app.V_WIDTH /4)), 45);
                saAnswer = new TextField("", skin);
                saAnswer.setBounds(app.V_WIDTH /6, app.V_HEIGHT/2 + 50, 200, 45);
                enableSubmitButton(app.V_WIDTH/2 - 175, app.V_HEIGHT /2 - 50);
            }
            stage.addActor(saQuestion);
            stage.addActor(saAnswer);
            stage.addActor(submit.getButton());

        }
        else{
            if (saEnabled){
                saEnabled = false;
                saQuestion.remove();
                saAnswer.remove();
                adminQuestionSelect();
                submit.getButton().remove();
            }

        }

    }

    private void tfMode(boolean check) {
        if (check) {
            tfEnabled = true;
            if (tfQuestion == null) {
                tfQuestion = new TextField("", skin);
                tfQuestion.setBounds(app.V_WIDTH / 6, app.V_HEIGHT / 2 + 100, (app.V_WIDTH - (app.V_WIDTH / 4)), 45);
                tAnswer = new MenuButton(app, "True");
                fAnswer = new MenuButton(app, "False");
                tAnswer.setBounds(app.V_WIDTH / 6, app.V_HEIGHT / 2 + 45, 200, 45);
                fAnswer.setBounds(app.V_WIDTH / 6, app.V_HEIGHT / 2 - 5, 200, 45);
                //this allows the question to be true or false
                fAnswer.getButton().addListener(new ClickListener() //added this!!!!
                {
                    public void clicked(InputEvent event, float x, float y) {
                        isTrue = false;
                      //  app.holder.close();
                        app.soundManager.buttonSound(app);

                    }
                    });
                tAnswer.getButton().addListener(new ClickListener()
                {
                    public void clicked(InputEvent event, float x, float y){
                    isTrue = true;
                   // app.holder.close();
                    app.soundManager.buttonSound(app);

                }
                });
                enableSubmitButton(app.V_WIDTH / 2 - 175, app.V_HEIGHT / 2 - 100);
            }
            stage.addActor(tfQuestion);
            stage.addActor(tAnswer.getButton());
            stage.addActor(fAnswer.getButton());
            stage.addActor(submit.getButton());

        } else {
            if (tfEnabled) {
                tfEnabled = false;
                tfQuestion.remove();
                tAnswer.getButton().remove();
                fAnswer.getButton().remove();
                submit.getButton().remove();
                adminQuestionSelect();
            }
        }
    }

    private void enableSubmitButton(int w, int h){
        submit = new MenuButton(app, "Submit to Database");
        submit.setBounds(w, h, 350, 45);
        submit.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                if (mcEnabled){
                    try {
                        app.holder.addMC_Question(mcQuestion.getText(), mcAnswer2.getText(), mcAnswer3.getText(), mcAnswer4.getText(), mcAnswer1.getText());
                    }
                    catch(Exception e)
                    {
                    }
                    mcMode(false);


                }
                else if (tfEnabled){
                    try{
                        app.holder.addTF_Question(tfQuestion.getText(), isTrue);
                    }
                    catch(Exception e)
                    {
                    }
                    tfMode(false);
                }
                else if (saEnabled){
                    try{
                        app.holder.addSA_Question(saQuestion.getText(), saAnswer.getText());
                    }
                    catch(Exception e)
                    {
                    }
                    saMode(false);
                }
            }
        });
    }

    public void update(float delta){
        stage.act(delta);
        enterKeyPressed(app);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
