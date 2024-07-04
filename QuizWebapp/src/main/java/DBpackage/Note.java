package DBpackage;

public class Note {
    private int noteId;
    private int fromId;
    private String fromUsername;
    private int toId;
    private String text;
    private int notification;
    public Note(int noteId, int fromId, String fromUsername, int toID, String text, int notification){
        this.fromId=fromId;
        this.fromUsername = fromUsername;
        this.noteId=noteId;
        this.text=text;
        this.toId=toID;
        this.notification=notification;
    }

    public void setNotification(int notification) {
        this.notification = notification;
    }

    public int getNotification() {
        return notification;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getText() {
        return text;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }
    public String getFromUsername() { return this.fromUsername; }

    public int getToId() {
        return toId;
    }

    public int getFromId() {
        return fromId;
    }

}