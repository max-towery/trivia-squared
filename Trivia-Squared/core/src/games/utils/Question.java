package games.utils;

import games.Application;

/**
 * Created by seach_000 on 5/22/2015.
 */
public abstract class Question {
    protected String answer;
    protected String question;
    public Choice[] choices;
    public boolean hasBeenAsked;
    public boolean answeredCorrectly;
    protected int QID;
    int questionType;
    public String getQuestion()
    {
        return this.question;
    }
    abstract String getAnswer();
    public void setQuestion(String question)
    {
        this.question = question;
    }
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
    public void setHasBeenAsked(boolean bool)
    {
        this.hasBeenAsked = bool;
    }
    public void setQID(int i){this.QID = i;}
    public int getQID(){return this.QID;}
    abstract void poseQuestion();
    abstract boolean takeAndEvaluateAnswer();

    private void flag_hasBeenAsked()
    {
        this.hasBeenAsked = true;
    }

    public static void questionEvent(Question pi)
    {
        pi.poseQuestion();
        pi.flag_hasBeenAsked();
        if(pi.takeAndEvaluateAnswer())
            System.out.println("\nYou did it!!!!");
        else
            System.out.println("\nFAIL");

    }

    public int getQuestionType() {
        return questionType;
    }
    public void asked()
    {
        this.hasBeenAsked = true;
    }


}
