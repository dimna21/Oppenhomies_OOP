package DBpackage.Questions;

import java.security.PrivateKey;

public class QuestionFillBlank extends Question{
    private String textBefore;
    private String textAfter;
    private String answer;

    public QuestionFillBlank() {

    }
    public QuestionFillBlank( int questionID, int quizID,int subID, int type,
                  String textBefore, String textAfter, String answer) {
        super(questionID, quizID, subID, type);
        this.textAfter = textAfter;
        this.textBefore = textBefore;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTextBefore() {
        return textBefore;
    }

    public String getTextAfter() {
        return textAfter;
    }

    public void setTextBefore(String textBefore) {
        this.textBefore = textBefore;
    }

    public void setTextAfter(String textAfter) {
        this.textAfter = textAfter;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuestionFillBlank{" +
                "textBefore='" + textBefore + '\'' +
                ", textAfter='" + textAfter + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
