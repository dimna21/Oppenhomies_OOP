package DBpackage.Questions;

import java.util.ArrayList;

public class QuestionMatching extends Question{

    private String question;
    private ArrayList<String> words;
    private ArrayList<String> matchingWords;

    public QuestionMatching() {}

    public QuestionMatching(int questionID, int quizID, int subID, int type,
                            String question, ArrayList<String> words, ArrayList<String> matchingWords) {
        super(questionID, quizID, subID, type);
        this.question = question;
        this.words = words;
        this.matchingWords = matchingWords;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public ArrayList<String> getMatchingWords() {
        return matchingWords;
    }

    public void setMatchingWords(ArrayList<String> matchingWords) {
        this.matchingWords = matchingWords;
    }
}