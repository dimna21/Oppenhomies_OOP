package DBpackage;

import java.util.Date;

public class Quiz {
    private String name;
    private String description;
    private int creatorID;
    private boolean randomQuestion;
    private boolean onePage;
    private boolean immediate;
    private boolean practice;
    private Date creationDate;
    private int timesTaken;

    public Quiz(String name, String description, int creatorID, boolean randomQuestion,
                boolean onePage, boolean immediate, boolean practice, Date creationDate,
                int timesTaken){
        this.name = name;
        this.description = description;
        this.creatorID = creatorID;
        this.randomQuestion = randomQuestion;
        this.onePage = onePage;
        this.immediate = immediate;
        this.practice = practice;
        this.creationDate = creationDate;
        this.timesTaken = timesTaken;
    }

    //Setters
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
    }

    public void setOnePage(boolean onePage) {
        this.onePage = onePage;
    }

    public void setPractice(boolean practice) {
        this.practice = practice;
    }

    public void setRandomQuestion(boolean randomQuestion) {
        this.randomQuestion = randomQuestion;
    }

    public void setTimesTaken(int timesTaken) {
        this.timesTaken = timesTaken;
    }


    // Getters
    public Date getCreationDate() {
        return creationDate;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public int getTimesTaken() {
        return timesTaken;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public boolean getImmediate() {
        return immediate;
    }

    public boolean getOnePage() {
        return onePage;
    }

    public boolean getPractice() {
        return practice;
    }

    public boolean getRandomQuestion() {
       return randomQuestion;
    }

    // Additional functionality

    public void incrementQuizTaken(){
        timesTaken++;
    }


}
