package games.utils.gameUtils;

import games.GameStateManager;
import games.containers.HighScore;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Max Towery on 6/6/2015.
 */
public class HighScoreUtil {

    public static final String easyfh = "textFiles/easyhighscores.txt";
    public static final String medfh = "textFiles/medhighscores.txt";
    public static final String hardfh = "textFiles/hardhighscores.txt";
    public static final String hellfh = "textFiles/hellhighscores.txt";

    public static void writeToFile(GameStateManager app, String filehandle) throws IOException {

        FileWriter fileWriter = new FileWriter(filehandle, false);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        HighScore newHighScore = new HighScore(app.username, app.score.getScore());
        app.scores.add(newHighScore);
        Collections.sort(app.scores, new ScoreComparator());
        for(int i = 0; i < app.scores.size(); i++){
            printWriter.print(app.scores.get(i));
            printWriter.print("\n");
        }
        fileWriter.close();
    }
    public static ArrayList<HighScore> readFile(String filehandle) throws IOException {

        ArrayList<HighScore> scores = new ArrayList<HighScore>();
        Scanner fin = FileUtil.openFileForRead(filehandle);
        while (fin.hasNextLine()){
                scores.add(new HighScore(fin.nextLine(), Integer.valueOf(fin.nextLine())));
        }
        System.out.println(scores);

        Collections.sort(scores, new ScoreComparator());
        while(scores.size() > 10){
            scores.remove(scores.size()-1);
        }
        fin.close();
        System.out.println(scores);
        return scores;
    }



}
