package DBpackage.Questions;

import java.security.PrivateKey;

public class QuestionFillBlank extends Question{
    private String textBefore;
    private String textAfter;
    private String answer;

    public QuestionFillBlank(int type, int quizID, int questionNumber,
                  String textBefore, String textAfter, String answer) {
        super(type, quizID, questionNumber);
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
}
