package DBpackage;

import java.sql.Timestamp;

public class Achievement {
    private int achievementId;
    private String achievementTitle;
    private int userId;
    private Timestamp achievementDate;

    public Achievement(int achievementId, String achievementTitle, int userId, Timestamp achievementDate) {
        this.achievementId = achievementId;
        this.achievementTitle = achievementTitle;
        this.userId = userId;
        this.achievementDate = achievementDate;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public String getAchievementTitle() {
        return achievementTitle;
    }

    public Timestamp getAchievementDate() {
        return achievementDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setAchievementDate(Timestamp achievementDate) {
        this.achievementDate = achievementDate;
    }

    public void setAchievementTitle(String achievementTitle) {
        this.achievementTitle = achievementTitle;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
