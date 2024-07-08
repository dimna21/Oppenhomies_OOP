package DBpackage.Questions;

public class QuestionTextbox extends Question{
    private String question;
    private String answer;

    public QuestionTextbox() {

    }
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

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    @Override
    public String toString() {
        return "QuestionTextbox{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
