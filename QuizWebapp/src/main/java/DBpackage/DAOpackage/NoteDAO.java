package DBpackage.DAOpackage;

import DBpackage.DatabaseAccess;
import DBpackage.Note;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NoteDAO {

    /**Creates a note and adds it to the database*/
    public static boolean createNote(String fromUsername, String toUsername, String messageText) {
        return DatabaseAccess.createNote(fromUsername,toUsername,messageText);
    }

    /**returns latest N notes by a user*/
    public  static ArrayList<Note> getNotes(int userID, int maxAmount) {
        return DatabaseAccess.getNotes(userID,maxAmount);

    }

    /**Returns all chats for a conversation between 2 users*/
    public  static ArrayList<Note> getNotesForChat(int fromId, int toId, int amount) {
        return DatabaseAccess.getNotesForChat(fromId,toId,amount);

    }

    /**Returns notes from result set if needed*/
    private static  ArrayList<Note> getNotesFromResultSet(ResultSet rs) throws SQLException {
        return DatabaseAccess.getNotesFromResultSet(rs);

    }

}
