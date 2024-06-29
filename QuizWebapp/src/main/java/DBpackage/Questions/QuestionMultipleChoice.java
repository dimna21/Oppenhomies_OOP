package DBpackage.Questions;

import DBpackage.DatabaseInfo;

import java.sql.*;
import java.util.ArrayList;

public class QuestionMultipleChoice extends Question{
    private String question;
    private String correctAnswer;
    private int ordered;
    private ArrayList<String> answerList;

    public QuestionMultipleChoice( int questionID, int quizID,int subID, int type, String question, int ordered)  {
        super(questionID, quizID, subID, type);
        this.question = question;
        this.ordered = ordered;
        this.answerList=new ArrayList<>();
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswerList(){
        return answerList;
    }
    public String getQuestion(){
        return question;
    }
    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setAnswerList(ArrayList<String> answerList) {
        this.answerList = answerList;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public int getOrdered() {
        return ordered;
    }
}
