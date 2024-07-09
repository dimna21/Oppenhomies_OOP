package DBpackage.DAOpackage;

import DBpackage.DatabaseAccess;

import java.util.ArrayList;

public class StatisticDAO {

    /**
     *  Returns the site statistics.
     * The first entry of the ArrayList is total number of users.
     * The second entry of the ArrayList is total number of quizzes taken
     */
    public static ArrayList<Integer> getSiteStatistics() {
        return DatabaseAccess.getSiteStatistics();
    }
}