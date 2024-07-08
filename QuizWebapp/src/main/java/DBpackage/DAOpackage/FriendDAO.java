package DBpackage.DAOpackage;
import DBpackage.*;
import DBpackage.Questions.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class FriendDAO {

    public static ArrayList<FriendRequest> friendRequests(int userID) {
        return DatabaseAccess.friendRequests(userID);
    }

    public static void sendFriendRequest(String from, String to) throws SQLException {
        DatabaseAccess.sendFriendRequest(from, to);

    }

    public static void updateFriendRequestStatus(int userAnswering, int answeringTo, int status) throws SQLException {
        DatabaseAccess.updateFriendRequestStatus(userAnswering, answeringTo, status);
    }

    public static ArrayList<User> getFriendlist(String username) {
        return DatabaseAccess.getFriendlist(username);

    }
    public static ArrayList<FriendRequest> waitingFriendRequests(int userID) {
        return DatabaseAccess.waitingFriendRequests(userID);

    }

    public static ArrayList<Activity> getFriendsActivity(String user, int maxActivities) {
        return DatabaseAccess.getFriendsActivity(user,maxActivities);


    }


}