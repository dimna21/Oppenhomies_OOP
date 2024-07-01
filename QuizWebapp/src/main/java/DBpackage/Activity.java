package DBpackage;

import java.util.ArrayList;

public class Activity {
    private String username;
    private ArrayList<Score> scoreList;
    private ArrayList<Quiz> quizList;
    private ArrayList<Achievement> achievementList;
    public Activity(String username, ArrayList<Score> scoreList,ArrayList<Quiz> quizList,
                    ArrayList<Achievement> achievementList){
        this.username=username;
        this.scoreList=scoreList;
        this.quizList=quizList;
        this.achievementList=achievementList;
    }

    public void setAchievementList(ArrayList<Achievement> achievementList) {
        this.achievementList = achievementList;
    }

    public ArrayList<Achievement> getAchievementList() {
        return achievementList;
    }

    public ArrayList<Quiz> getQuizList() {
        return quizList;
    }

    public ArrayList<Score> getScoreList() {
        return scoreList;
    }

    public String getUsername() {
        return username;
    }

    public void setQuizList(ArrayList<Quiz> quizList) {
        this.quizList = quizList;
    }

    public void setScoreList(ArrayList<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}