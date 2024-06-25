package DBpackage.Questions;

public class QuestionTextbox extends Question{
    private String question;
    private String answer;
    public QuestionTextbox(int type, int quizID, int questionNumber, String question, String answer) {
        super(type, quizID, questionNumber);
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
