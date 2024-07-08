package DBpackage.DAOpackage;

import DBpackage.DatabaseAccess;

import java.util.ArrayList;

public class StatisticDAO {
    public static ArrayList<Integer> getSiteStatistics() {
        return DatabaseAccess.getSiteStatistics();

    }
}