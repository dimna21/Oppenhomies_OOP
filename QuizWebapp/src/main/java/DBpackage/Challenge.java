package DBpackage;

public class Challenge {
    private  int quiz_id;
    private int requestId;
    private int from_id;
    private int to_id;
    private int notification;
    private String from_username;
    private String to_username;
    public Challenge(int requestID,int from_id, int to_id, int quiz_id, int notification, String from_username, String to_username){
        this.from_id = from_id;
        this.to_id = to_id;
        this.notification = notification;
        this.requestId=requestID;
        this.quiz_id = quiz_id;
        this.from_username = from_username;
        this.to_username = to_username;
    }

    public String getFrom_username() {
        return from_username;
    }

    public String getTo_username() {
        return to_username;
    }
    public void setFrom_username(String from_username) {
        this.from_username = from_username;
    }

    public void setTo_username(String to_username) {
        this.to_username = to_username;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public int getFrom_id() {
        return from_id;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getNotification() {
        return notification;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public void setNotification(int notification) {
        this.notification = notification;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }
}
