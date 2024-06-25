package DBpackage;

public class FriendRequest {
    private int requestId;
    private int from_id;
    private int to_id;
    private int notification;
    public FriendRequest(int requestID,int from_id, int to_id, int notification){
        this.from_id=from_id;
        this.to_id = to_id;
        this.notification = notification;
        this.requestId=requestID;
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
