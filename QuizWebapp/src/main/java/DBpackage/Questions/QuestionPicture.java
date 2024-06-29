package DBpackage.Questions;

public class QuestionPicture extends Question{
    private String question;
    private String answer;
    private String imageURL;

    public QuestionPicture(int questionID, int quizID,int subID, int type, String question, String answer, String imageURL) {
        super(questionID, quizID, subID, type);
        this.question = question;
        this.answer = answer;
        this.imageURL = imageURL;
    }


    public String getQuestion() {
        return question;
    }

    public String getAnswer(){
        return answer;
    }

    public String getImageURL(){
        return imageURL;
    }

}
