package games.utils.dbUtils;

/**
 * Created by seach_000 on 5/22/2015.
 */
public class Choice
{
    String choice;
    boolean isCorrectChoice;

    public Choice(String choice)
    {
        this.choice = choice;
        this.isCorrectChoice = false;
    }
    public void setToTrue()
    {
        this.isCorrectChoice = true;
    }
    public void setChoice(String choice)
    {
        this.choice = choice;
    }
    public boolean isTrue(){return this.isCorrectChoice;}
    public String toString(){return this.choice;}
}
