package DBpackage.Questions;

import java.util.ArrayList;

public class
QuestionMultiAnswer extends Question{
    private String question;
    private int ordered;
    private ArrayList<String> answerList;

    public QuestionMultiAnswer() {

    }

    public QuestionMultiAnswer(int questionID, int quizID,int subID, int type,String question, int ordered,
                            ArrayList<String> answers){
        super(questionID, quizID, subID, type);
        this.question=question;
        this.ordered=ordered;
        this.answerList=answers;
    }


    public int getOrdered() {
        return ordered;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswerList(ArrayList<String> answerList) {
        this.answerList = answerList;
    }

    public ArrayList<String> getAnswerList() {
        return answerList;
    }


    public String getQuestion() {
        return question;
    }
}
