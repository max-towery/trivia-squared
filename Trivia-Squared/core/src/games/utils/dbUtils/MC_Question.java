package games.utils.dbUtils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by seach_000 on 5/2/2015.
 * 5/8 -- added the formatAnswers method that is used to aid in processing our question and answer data sets
 *      returns nothing but does alter the answer string object to indicate what choice is the correct one
 *
 *      added the findAndAlter method to match the correct answer with it's corresponding letter choice
 */
public class MC_Question extends Question
{


    public MC_Question()
    {
        super.setQuestion("");
        super.setAnswer("");
        super.hasBeenAsked = false;
        super.answeredCorrectly = false;
        super.questionType = 0;
        super.choices = new games.utils.dbUtils.Choice[4];
    }


    public void setQuestion(String inc)
    {
        this.question = inc;
    }
    public void setChoice(int index, games.utils.dbUtils.Choice choice)
    {
        this.choices[index] = choice;
    }

    public void setChoice(int index, String choice)
    {
        this.choices[index].setChoice(choice);
    }
    public void setAnswer(String inc)
    {
        this.answer = inc;
    }



    public String getQuestion(){return this.question;}
    public String getAnswer(){return this.answer;}


    public games.utils.dbUtils.Choice getChoice(int index){return choices[index];}
    public games.utils.dbUtils.Choice[] getChoices() {return this.choices;}
    //in this I want to get questions, and choices


    public static void scanForQuestions(ArrayList<MC_Question> list, Scanner toScan){
        String lineIn= "";
        MC_Question toAdd;
        for(int i =0; i < 15; i++)
        {
            while (lineIn.contains(">") != true)
            {
                lineIn =toScan.nextLine();
            }
            toAdd = new MC_Question();
            toAdd.question += lineIn;
            lineIn = toScan.nextLine();
            while (lineIn.length() >=3 && lineIn.contains("a.") != true)
            {
                if(lineIn.contains("a.")!= true)
                {
                    toAdd.question += lineIn;
                    lineIn = toScan.nextLine();
                }
            }
            while(lineIn.length() < 2)
                lineIn = toScan.nextLine();
            if(lineIn.contains("a.") == true)
            {
                toAdd.setChoice(0, new games.utils.dbUtils.Choice(lineIn));
            }
            else
                toAdd.setChoice(0, new games.utils.dbUtils.Choice(toScan.nextLine())); //choices[0] = toScan.nextLine();
            toAdd.setChoice(1, new games.utils.dbUtils.Choice(toScan.nextLine())); //choices[1] = toScan.nextLine();
            toAdd.setChoice(2, new games.utils.dbUtils.Choice(toScan.nextLine()));
            toAdd.setChoice(3, new games.utils.dbUtils.Choice(toScan.nextLine()));
            list.add(toAdd);
            lineIn = toScan.nextLine();
        }
        while(lineIn.contains("Answer") != true)
        {
            lineIn = toScan.nextLine();
        }
        for(int i = 0; i < 15; i++)
        {
            lineIn = toScan.nextLine();
            if(lineIn.contains(">")!= true && lineIn.length() == 1)
            {
                i--;
                //list.get(i).answer += lineIn;

            }
            else
            {
                if(lineIn.contains(">") == true)
                {
                    list.get(i).answer = lineIn;
                }
                else
                if(lineIn.length()>4)//charAt(lineIn.length()-3) == '.' || lineIn.charAt(lineIn.length()-2) == '.')
                {
                    i--;
                    list.get(i).answer += lineIn;
                }
            }
           // if(list.get(i).answer.length()<2)
            //    i--;
        }

    }

    /*
      this needs to go into each question in our list.  Needs to get the correct answer, then compare the correct answer
      with the choices, to see what letter the answer corresponds to...I think we would just put the letter in front of
      our correct answer
     */
    public static void formatAnswers(ArrayList<MC_Question> list)
    {
        games.utils.dbUtils.Choice a, b, c, d;
        for(int i =0; i < list.size(); i ++)
        {
            a = list.get(i).getChoice(0);
            b = list.get(i).getChoice(1);
            c = list.get(i).getChoice(2);
            d = list.get(i).getChoice(3);
            findAndAlterAnswer(a, b, c, d, list.get(i).answer);
            if(list.get(i).getAnswer().length() > 4)
                list.get(i).setAnswer(list.get(i).getAnswer().substring(3));

            list.get(i).setQuestion(list.get(i).getQuestion().substring(3));

            list.get(i).setChoice(0, list.get(i).getChoice(0).toString().substring(5));
            list.get(i).setChoice(1, list.get(i).getChoice(1).toString().substring(5));
            list.get(i).setChoice(2, list.get(i).getChoice(2).toString().substring(5));
            list.get(i).setChoice(3, list.get(i).getChoice(3).toString().substring(5));
            boolean results;
            do
            {
                results = list.get(i).cleanBlankSpaces();
            }while(results == false);
        }
    }

    public boolean cleanBlankSpaces()
    {
        boolean blankAtStart = true;
        if(this.getAnswer().charAt(0) == ' ')
        {
            this.setAnswer(this.getAnswer().substring(1));
            if(this.getAnswer().charAt(0) == ' ')
                blankAtStart = false;
        }
        if(this.getQuestion().charAt(0) == ' ')
        {
            this.setQuestion(this.getQuestion().substring(1));
            if(this.getQuestion().charAt(0) == ' ')
                blankAtStart = false;
        }
        if(this.getChoice(0).toString().charAt(0) == ' ')
        {
            this.setChoice(0, (this.getChoice(0).toString().substring(1)));
            if(this.getChoice(0).toString().charAt(0) == ' ')
                blankAtStart = false;
        }
        if(this.getChoice(1).toString().charAt(0) == ' ')
        {
            this.setChoice(1, (this.getChoice(1).toString().substring(1)));
            if(this.getChoice(1).toString().charAt(0) == ' ')
                blankAtStart = false;
        }
        if(this.getChoice(2).toString().charAt(0) == ' ')
        {
            this.setChoice(2, (this.getChoice(2).toString().substring(1)));
            if(this.getChoice(2).toString().charAt(0) == ' ')
                blankAtStart = false;
        }
        if(this.getChoice(3).toString().charAt(0) == ' ')
        {
            this.setChoice(3, (this.getChoice(3).toString().substring(1)));
            if(this.getChoice(3).toString().charAt(0) == ' ')
                blankAtStart = false;
        }
        return blankAtStart;
    }
    public static void findAndAlterAnswer(games.utils.dbUtils.Choice a, games.utils.dbUtils.Choice b, games.utils.dbUtils.Choice c, games.utils.dbUtils.Choice d, String ans)
    {
        if(ans.contains(a.toString().substring(6)))
        {
            a.setToTrue();
        }
        else
            if(ans.contains(b.toString().substring(6)))
            {
                b.setToTrue();
            }
            else
                if(ans.contains(c.toString().substring(6)))
                {
                    c.setToTrue();
                }
                else
                    if(ans.contains(d.toString().substring(6)))
                    {
                        d.setToTrue();
                    }

    }

    public void findAns()
    {
        String ans = this.getAnswer();
        if(ans.contains(this.getChoice(0).toString()))
            this.getChoice(0).setToTrue();
        else
            if(ans.contains(this.getChoice(1).toString()))
                this.getChoice(1).setToTrue();
            else if(ans.contains(getChoice(2).toString()))
                    this.getChoice(2).setToTrue();
                else if(ans.contains(getChoice(3).toString()))
                        this.getChoice(3).setToTrue();
    }

    public static void createAndAddQuestion()
    {
        String failed = "\nSorry, question not valid";
        MC_Question toAdd = createQuestion();
        if(toAdd == null)
        {
            System.out.println(failed);
        }
        else
        {

        }
    }

    public static MC_Question createQuestion()
    {
        String questionPrompt = "\nPlease enter the question you wish to add: ";
        String answerPrompt = "\nPlease enter the correct answer: ";
        String falseOne = "\nPlease enter a false choice: ";
        String falseTwo = "\nPlease enter a second false choice: ";
        String falseThree = "\nPlease enter a third false choice: ";
        Scanner scanner = new Scanner(System.in);
        MC_Question toAdd  = new MC_Question();
        String question = "";
        String choice1 = "";
        String choice2 = "";
        String choice3 = "";
        String answer = "";
        System.out.println(questionPrompt);
        question = scanner.nextLine();
        System.out.println(answerPrompt);
        answer = scanner.nextLine();
        System.out.println(falseOne);
        choice1 = scanner.nextLine();
        System.out.println(falseTwo);
        choice2 = scanner.nextLine();
        System.out.println(falseThree);
        choice3 = scanner.nextLine();
        toAdd.setQuestion(question);
        toAdd.setAnswer(answer);
        toAdd.scrambleAndSetChoices(choice1, choice2, choice3, answer);
        findAndAlterAnswer(toAdd.getChoice(0), toAdd.getChoice(1),  toAdd.getChoice(2),
                toAdd.getChoice(3), toAdd.getAnswer());
        if(toAdd.ensureTruth())
            return toAdd;
        else return null;
    }

    public boolean ensureTruth()
    {
        boolean hasTrueAns = false;
        for(int i = 0; i < 4; i ++)
        {
            if(this.getChoice(i).isTrue())
                hasTrueAns = true;
        }
        return hasTrueAns;
    }

    public void scrambleAndSetChoices(String one, String two, String three, String four)
    {
        Random rand = new Random();
        int ans = rand.nextInt(4);
        this.setChoice(ans, new games.utils.dbUtils.Choice(four));
        this.setChoice((ans+1)%4, new games.utils.dbUtils.Choice(one));
        this.setChoice((ans+2)%4, new games.utils.dbUtils.Choice(two));
        this.setChoice((ans+3)%4, new games.utils.dbUtils.Choice(three));
    }


    void poseQuestion()
    {
        String questionType = "\nMultiple Choice";
        String question = "\nQuestion: " + this.getQuestion();
        String choices = "\nChoices: " + "\n1 " + this.getChoices()[0] +
                "\n2 "+ this.getChoices()[1] + "\n3" + this.getChoices()[2] +
                "\n4" + this.getChoices()[3];
        System.out.println(questionType);
        System.out.println(question);
        System.out.println(choices);
    }

    boolean takeAndEvaluateAnswer()
    {
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
        }while(choice<1 || choice>4);
        choice--;
        if(this.getChoice(choice).isTrue())
            return true;
        return false;
    }

}
