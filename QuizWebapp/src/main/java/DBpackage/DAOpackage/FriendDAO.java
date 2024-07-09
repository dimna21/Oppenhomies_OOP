package DBpackage.DAOpackage;
import DBpackage.*;
import DBpackage.Questions.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class FriendDAO {

    public static ArrayList<FriendRequest> friendRequests(int userID) {
        return DatabaseAccess.friendRequests(userID);
    }

    /**Sends a friend request from one account to another*/
    public static void sendFriendRequest(String from, String to) throws SQLException {
        DatabaseAccess.sendFriendRequest(from, to);
    }

    /**
     * Takes user IDs as parameters and answers a friend request.
     *  Status = 1 accepted
     *  Status = 2 rejected
     */
    public static void updateFriendRequestStatus(int userAnswering, int answeringTo, int status) throws SQLException {
        DatabaseAccess.updateFriendRequestStatus(userAnswering, answeringTo, status);
    }

    /** Returns a friendlist of specified username */
    public static ArrayList<User> getFriendlist(String username) {
        return DatabaseAccess.getFriendlist(username);

    }

    /** Returns pending friend requests */
    public static ArrayList<FriendRequest> waitingFriendRequests(int userID) {
        return DatabaseAccess.waitingFriendRequests(userID);
    }

    /** Returns N activities of a friend */
    public static ArrayList<Activity> getFriendsActivity(String user, int maxActivities) {
        return DatabaseAccess.getFriendsActivity(user,maxActivities);
    }

    /** Returns last friend request of a user */
    public static FriendRequest getLatestFriendRequest() {
        return DatabaseAccess.getLatestFriendRequest();
    }

}