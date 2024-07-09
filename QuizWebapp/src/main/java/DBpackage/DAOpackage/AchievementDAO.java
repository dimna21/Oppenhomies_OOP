package DBpackage.DAOpackage;

import DBpackage.Achievement;
import DBpackage.DatabaseAccess;

import java.util.ArrayList;

public class AchievementDAO {

    /**Returns recent N achievements for a user*/
    public  static ArrayList<Achievement> getRecentAchievements(String username, int maxAmount) {
        return DatabaseAccess.getRecentAchievements(username,maxAmount);
    }

    /** Returns the achievements of a user in an arrayList format of strings */
    public static  ArrayList<String> getUserAchievements(String username) {
        return DatabaseAccess.getUserAchievements(username);
    }
}
