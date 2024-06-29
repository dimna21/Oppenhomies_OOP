package DBpackage.Questions;

public class Question {
    private int type;
    private int quizID;
    private int subID;
    private int questionID;

    public Question(int questionID, int quizID, int subID, int type){
        this.questionID = questionID;
        this.type = type;
        this.quizID = quizID;
        this.subID = subID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getSubID() {
        return subID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public void setSubID(int subID) {
        this.subID = subID;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQuestionNumber() {
        return subID;
    }

    public int getQuizID() {
        return quizID;
    }

    public int getType() {
        return type;
    }
}
