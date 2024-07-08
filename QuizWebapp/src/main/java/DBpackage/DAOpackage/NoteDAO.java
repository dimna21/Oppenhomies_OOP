package DBpackage.DAOpackage;

import DBpackage.DatabaseAccess;
import DBpackage.Note;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NoteDAO {
    public static boolean createNote(String fromUsername, String toUsername, String messageText) {
        return DatabaseAccess.createNote(fromUsername,toUsername,messageText);
    }
    public  static ArrayList<Note> getNotes(int userID, int maxAmount) {
        return DatabaseAccess.getNotes(userID,maxAmount);

    }
    public  static ArrayList<Note> getNotesForChat(int fromId, int toId, int amount) {
        return DatabaseAccess.getNotesForChat(fromId,toId,amount);

    }
    private static  ArrayList<Note> getNotesFromResultSet(ResultSet rs) throws SQLException {
        return DatabaseAccess.getNotesFromResultSet(rs);

    }

    }
