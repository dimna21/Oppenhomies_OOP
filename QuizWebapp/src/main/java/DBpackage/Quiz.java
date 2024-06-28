package DBpackage;

import java.util.Date;

public class Quiz {
    private  int quiz_id;
    private String quiz_name;
    private String quiz_description;
    private int quiz_creator_id;
    private int random_question;
    private int one_page;
    private int immediate;
    private int practice;
    private java.sql.Date creation_date;
    private int times_taken;

    public Quiz(int quiz_id, String name, String description, int creatorID, int randomQuestion,
                int onePage, int immediate, int practice, java.sql.Date creationDate,
                int timesTaken){
        this.quiz_id = quiz_id;
        this.quiz_name = name;
        this.quiz_description = description;
        this.quiz_creator_id = creatorID;
        this.random_question = randomQuestion;
        this.one_page = onePage;
        this.immediate = immediate;
        this.practice = practice;
        this.creation_date = creationDate;
        this.times_taken = timesTaken;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int isImmediate() {
        return immediate;
    }

    public int isOnePage() {
        return one_page;
    }

    public int isPractice() {
        return practice;
    }

    public int isRandomQuestion() {
        return random_question;
    }

    //Setters
    public void setCreationDate(java.sql.Date creationDate) {
        this.creation_date = creationDate;
    }

    public void setName(String name) {
        this.quiz_name = name;
    }

    public void setCreatorID(int creatorID) {
        this.quiz_creator_id = creatorID;
    }

    public void setDescription(String description) {
        this.quiz_description = description;
    }

    public void setImmediate(int immediate) {
        this.immediate = immediate;
    }

    public void setOnePage(int onePage) {
        this.one_page = onePage;
    }

    public void setPractice(int practice) {
        this.practice = practice;
    }

    public void setRandomQuestion(int randomQuestion) {
        this.random_question = randomQuestion;
    }

    public void setTimesTaken(int timesTaken) {
        this.times_taken = timesTaken;
    }


    // Getters
    public Date getCreationDate() {
        return creation_date;
    }

    public int getCreatorID() {
        return quiz_creator_id;
    }

    public int getTimesTaken() {
        return times_taken;
    }

    public String getDescription() {
        return quiz_description;
    }

    public String getName() {
        return quiz_name;
    }

    public int getImmediate() {
        return immediate;
    }

    public int getOnePage() {
        return one_page;
    }

    public int getPractice() {
        return practice;
    }

    public int getRandomQuestion() {
       return random_question;
    }

    // Additional functionality

    public void incrementQuizTaken(){
        times_taken++;
    }


}
