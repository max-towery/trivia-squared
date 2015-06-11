package games.utils.gameUtils;

import games.containers.HighScore;

import java.util.Comparator;

/**
 * Created by Max Towery on 6/7/2015.
 */
public class ScoreComparator implements Comparator<HighScore>{
    @Override
    public int compare(HighScore o1, HighScore o2) {
        return o2.getScore() - o1.getScore();
    }
}
