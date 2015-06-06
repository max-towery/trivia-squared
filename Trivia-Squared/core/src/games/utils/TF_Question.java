package games.utils;

import games.utils.Question;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by seach_000 on 5/27/2015.
 */
public class TF_Question extends Question {


    public TF_Question() {
        super.question = "";
        super.answer = "";
        super.hasBeenAsked = false;
        super.choices = new Choice[2];

    }

    @Override
    public String getQuestion() {
        return this.question;
    }

    @Override
    public String getAnswer() {
        return this.answer;
    }

    @Override
    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setChoice(int index, Choice choice)
    {
        super.choices[index] = choice;
    }

    @Override
    void poseQuestion()
    {
        String questionType = "\nTrue/False";
        String question = "\nQuestion: " + this.getQuestion();
        String choices = "\n1 T \n2 F";
        System.out.println(questionType);
        System.out.println(question);
        System.out.println(choices);
    }

    @Override
    boolean takeAndEvaluateAnswer() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        do
        {
            try
            {
                choice = scanner.nextInt();
            } catch (InputMismatchException e)
            {
                System.out.println("\nInvalid Selection");
            }
        }while(choice<1 || choice>2);
        if(choice == 1)
        {
            if(this.getAnswer().equals("T"))
                return true;
            return false;
        }
        else
        {
            if(this.getAnswer().equals("F"))
                return true;
            return false;
        }
    }


}
