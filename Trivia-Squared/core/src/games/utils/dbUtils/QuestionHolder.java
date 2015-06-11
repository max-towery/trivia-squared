package games.utils.dbUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by seach_000 on 6/2/2015.
 *  6/3 added functionality to add questions to the database
 *      added functionality to generate question objects in a random order -- will be used to get questions
 *          for each room object
 *
 *  added getEncryptionKey() to return key from database
 *
 *  added saQID and tfQID to hold key values of questions to add
 *
 */
public class QuestionHolder {
    private ArrayList<SA_Question> SA_List;
    private ArrayList<TF_Question> TF_List;
    private ArrayList<MC_Question> MC_List;
    private DataBaseConnector conn;
    private int mcQID, saQID, tfQID;
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
    public static QuestionHolder getQuestionHolder()
    {
        DataBaseConnector conn = null;
        QuestionHolder qHolder = null;
        try {
            conn = new DataBaseConnector();
            qHolder = new QuestionHolder();
            qHolder.mcQID = conn.loadMC(qHolder.MC_List);
            qHolder.saQID = conn.loadSA(qHolder.SA_List);
            qHolder.tfQID = conn.loadTF(qHolder.TF_List);
            conn.close();
            return qHolder;
        }
        catch(Exception e)
        {
        }
        return qHolder;
    }

    public void openDB()
    {
        try {
            conn = new DataBaseConnector();
        }
        catch(Exception e)
        {
        }
    }
    public void close()
    {
        this.conn.close();
    }
    public static String getEncryptionKey() throws Exception
    {
        DataBaseConnector conn = new DataBaseConnector();
        String key = conn.getEncryptionKey();
        conn.close();
        return key;
    }
    public void addMC_Question(String question, String c1, String c2, String c3, String ans) throws Exception
    {
        MC_Question toAdd = new MC_Question();
        toAdd.setQuestion(question);
        toAdd.setChoice(0, new Choice(c1));
        toAdd.setChoice(1, new Choice(c2));
        toAdd.setChoice(2, new Choice(c3));
        toAdd.setAnswer(ans);
        toAdd.setChoice(3, new Choice(ans));
        toAdd.choices[3].setToTrue();
        toAdd.setQID(this.mcQID +1);
        this.mcQID++;
        conn.addToDB(toAdd);
        this.MC_List.add(toAdd);
    }
    public void addTF_Question(String question, boolean ans) throws Exception
    {
        TF_Question toAdd = new TF_Question();
        toAdd.setQuestion(question);
        toAdd.setChoice(0, new Choice("True"));
        toAdd.setChoice(1, new Choice("False"));
        if(ans) {
            toAdd.choices[0].setToTrue();
            toAdd.setAnswer("T");
        }
        else
        {
            toAdd.choices[1].setToTrue();
            toAdd.setAnswer("F");
        }

        toAdd.setQID(this.tfQID+1);
        this.tfQID++;
        conn.addToDB(toAdd);
        this.TF_List.add(toAdd);
    }
    public void addSA_Question(String question, String answer) throws Exception
    {
        SA_Question toAdd = new SA_Question();
        toAdd.setQuestion(question);
        toAdd.setAnswer(answer);
        toAdd.setQID(this.saQID);
        this.saQID++;
        conn.addToDB(toAdd);
        this.SA_List.add(toAdd);
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
        else{
            index = rand.nextInt(SA_List.size());
            Question question = SA_List.remove(index);
            question.questionType = 2;
            return question;
        }
    }
}
