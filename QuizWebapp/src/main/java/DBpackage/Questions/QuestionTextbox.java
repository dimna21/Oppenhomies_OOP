package DBpackage.Questions;

public class QuestionTextbox extends Question{
    private String question;
    private String answer;
    public QuestionTextbox(int questionID, int quizID,int subID, int type, String question, String answer) {
        super(questionID, quizID, subID, type);
        this.answer = answer;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer(){
        return answer;
    }
}
