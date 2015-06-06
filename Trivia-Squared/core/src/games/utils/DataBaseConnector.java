package games.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by seach_000 on 6/1/2015.
 * I want to use this class to reference all things related to querying our database.
 * We will call it to execute statements to a connection that is referenced by this class.
 * We will also end up needing some prepared statements for pulling from and updating the DB
 *      one pull for each question type and one push for Multiple Choice
 */
public class DataBaseConnector
{
        final String dataBaseName = "jdbc:sqlite:Trivia.db";
        private Connection conn;
        private PreparedStatement getMC, getTF, getSA, addMC, countMC, countSA, countTF;
        private ResultSet results;
        private int numQuestions;
        private Random rand;
        public DataBaseConnector() throws Exception
        {
            conn = dbConn();
            getMC = conn.prepareStatement("SELECT * FROM MC_QUESTIONS" +
                    " WHERE QID like ?");
            getTF =  conn.prepareStatement("SELECT * FROM TF_QUESTIONS" +
                    " WHERE QID like ?");
            getSA =  conn.prepareStatement("SELECT * FROM SA_QUESTIONS" +
                    " WHERE QID like ?");
            countMC = conn.prepareStatement("SELECT COUNT(QID) AS TOTAL FROM MC_QUESTIONS");
            countTF = conn.prepareStatement("SELECT COUNT(QID) AS TOTAL FROM TF_QUESTIONS");
            countSA = conn.prepareStatement("SELECT COUNT(QID) AS TOTAL FROM SA_QUESTIONS");
            addMC = conn.prepareStatement("INSERT INTO MC_QUESTIONS VALUES(?, ?, ?, ? , ?, ?, ?)");
            rand = new Random();
            results = null;
            numQuestions = 0;

        }

        private Connection dbConn() throws Exception
        {
            return DriverManager.getConnection(dataBaseName);
        }

        public void addToDB(MC_Question question) throws Exception
        {
            addMC.setString(1, Integer.toString(question.getQID()));
            addMC.setString(2, question.getQuestion());
            addMC.setString(3, question.getChoice(0).toString());
            addMC.setString(4, question.getChoice(1).toString());
            addMC.setString(5, question.getChoice(2).toString());
            addMC.setString(6, question.getChoice(3).toString());
            addMC.setString(7, question.getAnswer());
            addMC.executeUpdate();
        }
        public void close()
        {
            try{
                this.conn.close();
            }
            catch(Exception e)
            {
                System.out.println("Whoops");
            }
        }
        public void loadTF(ArrayList<TF_Question> masterList) throws Exception
        {
            TF_Question toAdd;
            results = countTF.executeQuery();
            numQuestions = results.getInt("TOTAL");
            for(int i = 1; i <= numQuestions; i++)
            {
                getTF.setString(1, Integer.toString(i));
                results = getTF.executeQuery();
                toAdd = new TF_Question();
                try
                {

                    toAdd.setQuestion(results.getString("Question"));
                    toAdd.setAnswer(results.getString("Answer"));
                    toAdd.setChoice(0,new Choice("True"));
                    toAdd.setChoice(1, new Choice("False"));
                    if (toAdd.getAnswer().equalsIgnoreCase("t")){
                        toAdd.choices[0].setToTrue();
                    }
                    else{
                        toAdd.choices[1].setToTrue();
                    }
                    toAdd.setQID(i);
                    masterList.add(rand.nextInt(masterList.size()+1), toAdd);
                }
                catch(Exception e)
                {
                    System.out.println("\nThis shouldn't happen");

                }
            }
        }

        public int loadMC(ArrayList<MC_Question> masterList) throws Exception
        {
            int mcQID = 0;
            MC_Question toAdd;
            results =countMC.executeQuery();
            numQuestions = results.getInt("TOTAL");
            for(int i = 1; i <= numQuestions; i++)
            {
                getMC.setString(1, Integer.toString(i));
                results = getMC.executeQuery();
                toAdd = new MC_Question();
                try
                {
                    toAdd.setQuestion(results.getString("Question"));
                    toAdd.setAnswer(results.getString("Answer"));
                    toAdd.scrambleAndSetChoices(results.getString("Choice1"), results.getString("Choice2"),
                            results.getString("Choice3"), results.getString("Choice4"));
                    toAdd.findAns();
                    toAdd.setQID(i);
                    if(toAdd.getQID() > mcQID)
                        mcQID = toAdd.getQID();
                    masterList.add(rand.nextInt(masterList.size()+1), toAdd);
                }
                catch(Exception e)
                {
                    numQuestions++;
                }
            }
            return mcQID;
        }

        public void loadSA(ArrayList<SA_Question> masterList) throws Exception
        {
            SA_Question toAdd;
            results = countSA.executeQuery();
            numQuestions = results.getInt("TOTAL");
            String answer, answer2;
            for(int i = 1; i <= numQuestions; i ++)
            {
                getSA.setString(1, Integer.toString(i));
                results = getSA.executeQuery();
                toAdd = new SA_Question();
                try
                {
                    toAdd.setQuestion(results.getString("Question"));
                    answer = results.getString("Answer");
                    if(answer.contains("//"))    //two answers
                    {
                        int loc = answer.indexOf("//");
                        answer2 = answer.substring(loc +2);
                        answer2 = ridBlankSpace(answer2);
                        toAdd.setSecond(answer2);
                        answer = answer.substring(0, loc);
                        answer = ridBlankSpace(answer);
                        toAdd.setAnswer(answer);
                        toAdd.setQID(i);
                    }
                    else
                        toAdd.setAnswer(answer);
                    masterList.add(toAdd);
                }
                catch(Exception e)
                {

                }
            }
        }

    public static String ridBlankSpace(String string)
    {
        String rt = string;
        while(rt.charAt(rt.length()-1) == ' ')
        {
            rt = rt.substring(0, rt.length()-1);
        }
        while(rt.charAt(0) == ' ')
        {
            rt = rt.substring(1);
        }
        return rt;
    }

}
