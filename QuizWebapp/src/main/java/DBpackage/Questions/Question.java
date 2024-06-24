package DBpackage.Questions;

public class Question {
    private int type;
    private int quizID;
    private int questionNumber;

    public Question(int type, int quizID, int questionNumber){

    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public int getQuizID() {
        return quizID;
    }

    public int getType() {
        return type;
    }
}
