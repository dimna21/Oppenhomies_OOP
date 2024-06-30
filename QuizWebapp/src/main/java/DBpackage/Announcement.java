package DBpackage;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Announcement {

    private java.sql.Timestamp creationDate;
    private String title;
    private String text;
    private int announcerID;
    private String username;
    public Announcement(String title, String text, int announcerID,  String announcerUsername, java.sql.Timestamp creationDate){
        this.username = announcerUsername;
        this.announcerID = announcerID;
        this.text = text;
        this.title = title;
        this.creationDate = creationDate;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAnnouncerID() {
        return announcerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAnnouncerID(int announcerID) {
        this.announcerID = announcerID;
    }

}