package games.utils;

import games.utils.Question;

import java.util.Scanner;

/**
 * Created by seach_000 on 5/27/2015.
 */
public class SA_Question extends Question {

    String secondAnswer;
    public SA_Question()
    {
        super.answer = "";
        super.question = "";
        super.hasBeenAsked = false;
        super.questionType = 2;
        super.answeredCorrectly = false;
        this.secondAnswer = null;

    }
    @Override
    public String getQuestion() {
        return this.question;
    }

    @Override
    String getAnswer() {
        return this.answer;
    }
    String getSecond() {return this.secondAnswer;}

    @Override
    public void setQuestion(String question)
    {
        this.question = question;
    }
    @Override
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
    public void setSecond(String second)
    {
        this.secondAnswer = second;
    }

    @Override
    public void poseQuestion()
    {
        String questionType = "\nShort Answer";
        String question = "\nQuestion: " + this.getQuestion();
        System.out.println(questionType);
        System.out.println(question);
    }

    @Override
    boolean takeAndEvaluateAnswer()
    {
        Scanner scanner = new Scanner(System.in);
        String answerIn = "";
        String promptForAnswer = "\nPlease enter an answer";
        System.out.println(promptForAnswer);
        answerIn = scanner.nextLine();
        if(this.getSecond() != null)
        {
            if (this.getAnswer().equalsIgnoreCase(answerIn) == true || this.getSecond().equalsIgnoreCase(answerIn) == true)
                return true;
        }
        else if(this.getAnswer().equalsIgnoreCase(answerIn))
            return true;
        return false;
    }
}
