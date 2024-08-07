package DBpackage.DAOpackage;

import DBpackage.Announcement;
import DBpackage.DatabaseAccess;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AnnouncementDAO {

    /**Creates an announcement in the database*/
    public static boolean makeAnnouncement(String username, String title, String text, Timestamp date) throws SQLException {
        return DatabaseAccess.makeAnnouncement(username, title, text, date);
    }

    /** Returns latest N announcements */
    public static ArrayList<Announcement> getLatestAnnouncements(int num) {
        return DatabaseAccess.getLatestAnnouncements(num);

    }

}