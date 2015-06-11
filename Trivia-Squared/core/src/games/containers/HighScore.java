package games.containers;

/**
 * Created by Max Towery on 6/6/2015.
 */
public class HighScore{

        private String name;
        private Integer score;
        public HighScore(String name, Integer score){
            this.name = name;
            this.score = score;
        }
        public String getName(){
            return name;
        }
        public int getScore(){
            return score;
        }
        public String toString(){
            return this.name + "\n" + this.score;
        }
        public String getScoreToString(){
            return String.valueOf(score);
        }
}

