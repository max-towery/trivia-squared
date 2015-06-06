package games.utils;

import games.containers.Room;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by seach_000 on 6/2/2015.
 *  6/3 added functionality to add questions to the database
 *      added functionality to generate question objects in a random order -- will be used to get questions
 *          for each room object
 *
 */
public class QuestionHolder {
    private ArrayList<SA_Question> SA_List;
    private ArrayList<TF_Question> TF_List;
    private ArrayList<MC_Question> MC_List;
    private DataBaseConnector conn;
    private int mcQID;
    private QuestionHolder()
    {
        this.SA_List = new ArrayList<SA_Question>();
        this.TF_List = new ArrayList<TF_Question>();
        this.MC_List = new ArrayList<MC_Question>();
        this.conn = null;
        this.mcQID = 0;
    }
    public int getMcQID()
    {
        return this.mcQID;
    }
    public static QuestionHolder getQuestionHolder() throws Exception
    {
        DataBaseConnector conn = new DataBaseConnector();
        QuestionHolder qHolder = new QuestionHolder();
        qHolder.mcQID = conn.loadMC(qHolder.MC_List);
        conn.loadSA(qHolder.SA_List);
        conn.loadTF(qHolder.TF_List);
        conn.close();
        return qHolder;
    }

    public void addMC_Question() throws Exception
    {
        DataBaseConnector conn = new DataBaseConnector();
        MC_Question toAdd = MC_Question.createQuestion();
        toAdd.setQID(this.mcQID +1);
        this.mcQID++;
        conn.addToDB(toAdd);
        this.MC_List.add(toAdd);
        conn.close();
    }

    public int getSize()
    {
        return this.MC_List.size() + this.TF_List.size() + this.SA_List.size();
    }

    public Question getQuestion(int questionType)
    {
        Random rand = new Random();
        int index;
        if (questionType == 0) {
            index = rand.nextInt(MC_List.size());
            Question question =  MC_List.remove(index);
            question.questionType = 0;
            return question;
        }
        else if (questionType == 1){
            index = rand.nextInt(TF_List.size());
            Question question = TF_List.remove(index);
            question.questionType = 1;
            return question;
        }
        return null;
    }
}
