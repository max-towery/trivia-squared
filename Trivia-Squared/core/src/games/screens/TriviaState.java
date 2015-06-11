package games.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import games.GameStateManager;
import games.triviaDisplay.MultiChoice;
import games.triviaDisplay.QuestionBase;
import games.triviaDisplay.ShortAns;
import games.triviaDisplay.TrueFalse;
import games.utils.dbUtils.Question;

/**
 * Created by Tamara on 6/4/2015.
 */
public class TriviaState implements Screen {

    private GameStateManager app;
    private Stage stage;
    private QuestionBase multiChoice;
    private QuestionBase trueFalse;
    private QuestionBase shortAns;
    private String question;



    public TriviaState(final GameStateManager app){
        this.app = app;
        this.stage = new Stage(new StretchViewport(GameStateManager.V_WIDTH, GameStateManager.V_HEIGHT,app.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        int currentRoom [] = new int [2];
        currentRoom[0] = app.playerIndex[0];
        currentRoom[1] = app.playerIndex[1];

        Question question = assignQuestion();
        //multiple choice
        if (question.getQuestionType() == 0){
            multiChoice = new MultiChoice(app, stage, question);
            this.question = question.getQuestion();

        }

        //true/false

         if (question.getQuestionType() == 1){
            trueFalse = new TrueFalse(app, stage, question);
            this.question = question.getQuestion();
        }

        //short answer
        else if (question.getQuestionType() == 2){
            shortAns = new ShortAns(app, stage, question);
            this.question = question.getQuestion();

        }


    }

    private Question assignQuestion(){
        if (app.playerDirection == 0){
            return app.grid[app.playerIndex[0]+1][app.playerIndex[1]].question;
        }
        else if (app.playerDirection == 1){
            return app.grid[app.playerIndex[0]-1][app.playerIndex[1]].question;
        }
        else if (app.playerDirection == 2){
            return app.grid[app.playerIndex[0]][app.playerIndex[1]-1].question;
        }
        else
        {
            return app.grid[app.playerIndex[0]][app.playerIndex[1]+1].question;
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();

        app.batch.begin();
        if (trueFalse != null && trueFalse.hintEnabled){
            trueFalse.renderHint(app, delta);
        }
        else if (shortAns != null && shortAns.hintEnabled){
            shortAns.renderHint(app, delta);
        }
        app.font30.drawWrapped(app.batch, question,  app.V_WIDTH / 7, app.V_HEIGHT - 125, 800);
        app.batch.end();
    }


    public void setAnsweredCorrectly(GameStateManager app){

        int i = app.playerIndex[0];
        int j = app.playerIndex[1];
        app.grid[i][j].question.answeredCorrectly = true;
        if (app.playerDirection == 0){
            app.grid[i+1][j].question.answeredCorrectly = true;
        }
        else if (app.playerDirection == 1){
            app.grid[i-1][j].question.answeredCorrectly = true;
        }
        else if (app.playerDirection == 2){
            app.grid[i][j-1].question.answeredCorrectly = true;
        }
        else if (app.playerDirection == 3){
            app.grid[i][j+1].question.answeredCorrectly = true;
        }
    }

    public void update(float delta){
        stage.act(delta);
        if (shortAns != null)
            shortAns.enterKeyPressed(app);
        app.score.updateTime();

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
