package DBpackage.Questions;

import java.util.ArrayList;

public class QuestionCheckbox extends Question{

    private String question;
    private int ordered;
    private ArrayList<String> answerList;
    private ArrayList<Integer> correctList;

    public QuestionCheckbox() {

    }

    public QuestionCheckbox(int questionID, int quizID,int subID, int type,String question, int ordered,
                            ArrayList<String> answers, ArrayList<Integer> correct){
        super(questionID, quizID, subID, type);
        this.question=question;
        this.ordered=ordered;
        this.answerList=answers;
        this.correctList=correct;
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

    public ArrayList<Integer> getCorrectList() {
        return correctList;
    }

    public void setCorrectList(ArrayList<Integer> correctList) {
        this.correctList = correctList;
    }

    public String getQuestion() {
        return question;
    }
}
