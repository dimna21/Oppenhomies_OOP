package DBpackage;

public class User {
    private int user_id;
    private String username;
    private String password;
    private int admin_status;
    private int quizzes_taken;
    private int quizzes_created;
    private int highest_scorer;
    private int practice_mode;
    private String profile_pic_url;
    private int activeAcc;

    // Constructor
    public User(int user_id, String username, String password, int admin_status, int quizzes_taken,
                int quizzes_created, int highest_scorer, int practice_mode, String profile_pic_url) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.admin_status = admin_status;
        this.quizzes_taken = quizzes_taken;
        this.quizzes_created = quizzes_created;
        this.highest_scorer = highest_scorer;
        this.practice_mode = practice_mode;
        this.profile_pic_url = profile_pic_url;
        this.activeAcc = 1;
    }
    public User(int user_id, String username, String password, int admin_status, int quizzes_taken,
                int quizzes_created, int highest_scorer, int practice_mode, String profile_pic_url,
                int activeAcc) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.admin_status = admin_status;
        this.quizzes_taken = quizzes_taken;
        this.quizzes_created = quizzes_created;
        this.highest_scorer = highest_scorer;
        this.practice_mode = practice_mode;
        this.profile_pic_url = profile_pic_url;
        this.activeAcc = activeAcc;
    }

    public int getActiveAcc() {
        return activeAcc;
    }

    public void setActiveAcc(int activeAcc) {
        this.activeAcc = activeAcc;
    }
    // Setter methods


    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAdmin_status(int admin_status) {
        this.admin_status = admin_status;
    }

    public void setQuizzes_taken(int quizzes_taken) {
        this.quizzes_taken = quizzes_taken;
    }

    public void setQuizzes_created(int quizzes_created) {
        this.quizzes_created = quizzes_created;
    }

    public void setHighest_scorer(int highest_scorer) {
        this.highest_scorer = highest_scorer;
    }

    public void setPractice_mode(int practice_mode) {
        this.practice_mode = practice_mode;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }
    // Getter methods
    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAdmin_status() {
        return admin_status;
    }

    public int getQuizzes_taken() {
        return quizzes_taken;
    }

    public int getQuizzes_created() {
        return quizzes_created;
    }

    public int getHighest_scorer() {
        return highest_scorer;
    }

    public int getPractice_mode() {
        return practice_mode;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }
}