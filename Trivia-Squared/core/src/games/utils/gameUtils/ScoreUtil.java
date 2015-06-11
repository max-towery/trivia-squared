package games.utils.gameUtils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import games.GameStateManager;

import java.util.Calendar;

/**
 * Created by Max Towery on 5/30/2015.
 */
public class ScoreUtil {

    private final int PERFECT_SCORE = 1000;

    public boolean gameActive;

    public long pauseTime;
    public int points;
    public Calendar startCal;
    public Calendar currentCal;
    public boolean atLeastOneMinute;

    public long startTime, currentTime;
    public int curMin;
    private int timeInSeconds;

    private int score;



    public ScoreUtil() {
        this.points = 1;
        curMin = 0;
        atLeastOneMinute = false;
        score = 0;
        gameActive = false;
        currentTime = 0;
        timeInSeconds = 0;
    }
    public void displayPoints(GameStateManager app, BitmapFont font, int x, int y){
        font.draw(app.batch, "Points: " + String.valueOf(points), x, y);
    }


    public void updateTime(){

        currentCal = Calendar.getInstance();

        currentTime = (int)currentCal.getTimeInMillis() /1000;

        if (currentTime - startTime >= 60){
            curMin += 1;
            startCal = Calendar.getInstance();
            startTime = (int)startCal.getTimeInMillis() /1000;
            atLeastOneMinute = true;
        }

        timeInSeconds = (int) (currentTime - startTime);

    }

    public void stopTimer(){
        currentCal = Calendar.getInstance();
        pauseTime = (int)currentCal.getTimeInMillis()/1000;
    }

    public void timeResume(){
        currentCal = Calendar.getInstance();
        currentTime = (int)currentCal.getTimeInMillis() /1000;
        long diffTime = currentTime - pauseTime;
        startTime = startTime + diffTime;


    }
    public void displayTime(GameStateManager app, BitmapFont font, int x, int y){

        if (gameActive)
            updateTime();

        String timeInSecondsToString = String.valueOf(timeInSeconds);

        if (timeInSeconds < 10){
            timeInSecondsToString = "0" + timeInSecondsToString;
        }
        if (atLeastOneMinute){
            font.draw(app.batch, "Time: " + curMin + ":"+ String.valueOf(timeInSecondsToString), x, y);

        }
        else{
            font.draw(app.batch, "Time: " + String.valueOf(timeInSecondsToString), x, y);
        }
    }

    private int minToSec() {
        return curMin * 60;
    }

    public void calculateScore(GameStateManager app){
        this.score = PERFECT_SCORE - ((minToSec() * app.difficultyScreen.getDifficulty()) + timeInSeconds + points*5);
        if (this.score <= 0)
            this.score = 0;
    }

    public void increase(){
        points++;
    }

    public boolean hasPointsForHint(){
        if (points > 0){
            points--;
            return true;
        }

        return false;
    }

    public boolean hasPointsForMap() {
        if (points > 0){
            points--;
            return true;
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    public String toString(){
        return String.valueOf(this.points);
    }
}
