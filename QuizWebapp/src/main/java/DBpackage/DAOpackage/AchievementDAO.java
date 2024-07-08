package DBpackage.DAOpackage;

import DBpackage.Achievement;
import DBpackage.DatabaseAccess;

import java.util.ArrayList;

public class AchievementDAO {
    public  static ArrayList<Achievement> getRecentAchievements(String username, int maxAmount) {
        return DatabaseAccess.getRecentAchievements(username,maxAmount);

    }
    public static  ArrayList<String> getUserAchievements(String username) {
        return DatabaseAccess.getUserAchievements(username);

    }
    }
