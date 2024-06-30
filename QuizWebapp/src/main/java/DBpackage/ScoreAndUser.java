package DBpackage;

public class ScoreAndUser {
    private Score score;
    private User user;
    public ScoreAndUser(Score s, User u){
        this.score=s;
        this.user=u;

    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}