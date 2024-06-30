package DBpackage;

import java.sql.Date;
import java.sql.Timestamp;

public class Score {
    private int quiz_id;
    private int score_id;
    private int user_id;
    private int score;
    private int time;
    private java.sql.Timestamp date_scored;

    public Score(int score_id,int quiz_id,int user_id, int score, int time, java.sql.Timestamp date_scored){
        this.score_id = score_id;
        this.quiz_id=quiz_id;
        this.date_scored=date_scored;
        this.user_id=user_id;
        this.time=time;
        this.score=score;
    }

    public int getScore() {
        return score;
    }

    public int getScore_id() {
        return score_id;
    }

    public Timestamp getDate_scored() {
        return date_scored;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setScore_id(int score_id) {
        this.score_id = score_id;
    }

    public int getTime() {
        return time;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setDate_scored(Timestamp date_scored) {
        this.date_scored = date_scored;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
