import DBpackage.*;
import DBpackage.Questions.*;
import DBpackage.Quiz;
import DBpackage.User;
import junit.framework.TestCase;

import javax.faces.bean.RequestScoped;
import javax.validation.constraints.AssertTrue;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import static DBpackage.DatabaseAccess.*;


public class DBtest extends TestCase{
    DatabaseAccess dbCall;
    protected void setUp() throws Exception {
        super.setUp();
        dbCall = new DatabaseAccess();
    }
    public void testLogin() throws SQLException, NoSuchAlgorithmException {
        boolean ans;
        ans = dbCall.login("john_doe","password123");
        assertTrue(ans);
        ans = dbCall.login("john_doe","password13");
        assertFalse(ans);
    }
    public void testGetUserInfo() throws SQLException{
        User user;
        user = dbCall.getUserInfo("charlie_black");
        assertEquals(user.getUser_id(),5);
        assertEquals(user.getQuizzes_taken(),20);
        user = dbCall.getUserInfo("hi");
        assertNull(user);
    }
    public void testAddUser() throws SQLException{
        boolean added;
        added = dbCall.addUser("a","b",0);
        assertTrue(added);
        added = dbCall.addUser("c","d",0);
        assertTrue(added);
        added = dbCall.addUser("a","d",1);
        assertFalse(added);

    }
    public void testUpdatePicture() throws SQLException{
        boolean added;
        added = dbCall.updatePicture("asddf","picture.jpg");
        assertFalse(added);
        added = dbCall.updatePicture("john_doe","picture.jpg");
        assertTrue(added);
    }
    public void testGetNewestQuiz() throws SQLException{
        ArrayList<Quiz> ls;
        ls=dbCall.getNewestQuiz(0);
        assertEquals(ls.size(),6);
        ls=dbCall.getNewestQuiz(3);
        assertEquals(ls.size(),3);
        assertEquals(ls.get(0).getQuiz_id(),1);
        assertEquals(ls.get(1).getQuiz_id(),5);
        assertEquals(ls.get(2).getQuiz_id(),4);
    }
    public void testGetQuizInfo()throws SQLException{
        Quiz qz;
        qz= dbCall.getQuizInfo(1);
        assertEquals(qz.getName(),"General Knowledge");
        qz= dbCall.getQuizInfo(50);
        assertNull(qz);
        qz= dbCall.getQuizInfo(5);
        assertEquals(qz.getTimesTaken(),3);

    }
    public void testHasher() throws NoSuchAlgorithmException {

        System.out.println(hasher.getHash("password123"));
        System.out.println(hasher.getHash("password234"));
        System.out.println(hasher.getHash("molly"));
        System.out.println(hasher.getHash("b"));
        System.out.println(hasher.getHash("d"));

    }
    public void testTextBox(){
        Question q;
        Question Ques = new Question(1,6,1,1);
        q=dbCall.getTextBox(Ques);
        QuestionTextbox q2 = (QuestionTextbox)q;
        assertEquals(q2.getAnswer(),"Paris");
        assertEquals(q2.getQuizID(),6);
        assertEquals(q2.getSubID(),1);
    }
    public void testFillBlank(){
        Question q;
        Question Ques = new Question(2,6,2,2);
        q=dbCall.getFillBlank(Ques);
        QuestionFillBlank q2 = (QuestionFillBlank)q;
        assertEquals(q2.getTextAfter(),".");
        assertEquals(q2.getAnswer(),"H2O");
        assertEquals(q2.getSubID(),2);
    }
    public void testMultipleChoice(){
        Question q;
        Question Ques = new Question(3,6,3,3);
        q=dbCall.getMultipleChoice(Ques);
        QuestionMultipleChoice q2 = (QuestionMultipleChoice)q;
        assertEquals(q2.getCorrectAnswer(),"Mars");
        assertEquals(q2.getAnswerList().size(),4);
        assertEquals(q2.getSubID(),3);
    }
    public void testGetCheckbox(){
        Question q;
        Question Ques = new Question(5,6,5,6);
        q=dbCall.getCheckbox(Ques);
        QuestionCheckbox q2 = (QuestionCheckbox)q;
        assertEquals(q2.getOrdered(),1);
        assertEquals(q2.getQuestion(),"What are the primary colors?");
        assertEquals(q2.getAnswerList(),new ArrayList<>(Arrays.asList("Red", "Blue", "Green", "Yellow")));


         Ques = new Question(6,6,6,6);
         q=dbCall.getCheckbox(Ques);
         q2 = (QuestionCheckbox)q;
        assertEquals(q2.getOrdered(),1);
        assertEquals(q2.getQuestion(),"Select the programming languages.");
        assertEquals(q2.getAnswerList(),new ArrayList<>(Arrays.asList("Java", "Python", "C++", "HTML")));


    }
    public void testGetMultiAnswer(){
        Question q;
        Question Ques = new Question(8,6,8,7);
        q=dbCall.getMultiAnswer(Ques);
        QuestionMultiAnswer q2 = (QuestionMultiAnswer)q;
        assertEquals(q2.getOrdered(),1);
        assertEquals(q2.getQuestion(),"What are the planets in the Solar System in order?");
        assertEquals(q2.getAnswerList(),new ArrayList<>(Arrays.asList
                ("Mercury", "Venus", "Earth", "Mars","Jupiter", "Saturn", "Uranus", "Neptune")));


         Ques = new Question(9,6,9,7);
        q=dbCall.getMultiAnswer(Ques);
         q2 = (QuestionMultiAnswer)q;
        assertEquals(q2.getOrdered(),0);
        assertEquals(q2.getQuestion(),"What are the components of a computer?");
        assertEquals(q2.getAnswerList(),new ArrayList<>(Arrays.asList
                ("CPU", "RAM", "Motherboard", "Power Supply", "Storage")));

    }

    public void testGetQuizQuestions(){
        Quiz quiz = dbCall.getQuizInfo(6);
        ArrayList<Question> myQuestions= dbCall.getQuizQuestions(6);
        assertEquals(myQuestions.size(),3);

        for(Question q : myQuestions){
            int type = q.getType();
            Question myQuestion;
            switch(type){
                case 1:
                    myQuestion = (QuestionTextbox)q;
                    assertEquals(((QuestionTextbox) myQuestion).getAnswer(),"Paris");
                    break;
                case 2:
                    myQuestion = (QuestionFillBlank)q;
                    assertEquals(((QuestionFillBlank) myQuestion).getAnswer(),"H2O");
                    break;
                case 3:
                    myQuestion = (QuestionMultipleChoice)q;
                    assertEquals(((QuestionMultipleChoice) myQuestion).getAnswerList().size(),4);
                    break;
            }

        }
    }
    public void testGetPictureQuestion(){
        Question q;
        Question Ques = new Question(4,6,4,4);
        q=dbCall.getPictureQuestion(Ques);
        QuestionPicture q2 = (QuestionPicture)q;
        assertEquals(q2.getAnswer(),"Eiffel Tower");
        assertEquals(q2.getQuizID(),6);
        assertEquals(q2.getSubID(),4);

        Ques = new Question(4,6,3,4);
        q=dbCall.getPictureQuestion(Ques);
        assertNull(q);
    }

    public void testGetUsername(){
        assertEquals(dbCall.getUsername(1),"john_doe");
        assertEquals(dbCall.getUsername(2),"jane_smith");
        assertEquals(dbCall.getUsername(3),"alice_jones");
        assertEquals(dbCall.getUsername(4),"bob_brown");
    }
    public void testFriendRequests(){

        ArrayList<FriendRequest> list = dbCall.friendRequests(1);
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("jane_smith");
        testArray.add("alice_jones");
        testArray.add("bob_brown");
        testArray.add("charlie_black");

        for(int i = 0; i < list.size(); i++){
            String s = list.get(i).getFrom_username();
            assertTrue(testArray.contains(s));
        }
    }
    /*
    public void testChallenges(){
        ArrayList<Challenge> list = dbCall.challenges("john_doe");
        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("jane_smith");
        testArray.add("alice_jones");
        testArray.add("bob_brown");
        testArray.add("charlie_black");

        for(int i = 0; i < list.size(); i++){
            String from = list.get(i).getFrom_username();
            assertTrue(testArray.contains(from));
            assertEquals(list.get(i).getNotification(), 1);
        }
    }
     */
    public void testCreateNote(){
        assertTrue(dbCall.createNote("john_doe","jane_smith","i love you"));
        assertFalse(dbCall.createNote("john_do","jane_smith","i love you"));

    }
    public void testGetAnnouncements(){
        ArrayList<Announcement> l = dbCall.getLatestAnnouncements(0);
        assertEquals(l.size(), 3);
        assertEquals(l.get(1).getTitle(), "Announcement 2");
        assertEquals(l.get(2).getTitle(), "Announcement 1");

    }

    public void testIsAdmin(){
        assertTrue(dbCall.isAdmin("john_doe"));
        assertTrue(dbCall.isAdmin("charlie_black"));
        assertFalse(dbCall.isAdmin("jane_smith"));
    }

    public void testGetUserID(){
        assertEquals(DatabaseAccess.getUserID("john_doe"),1);
        assertEquals(DatabaseAccess.getUserID("jane_smith"),2);
    }

    public void testMakeAnnouncement() throws SQLException {
        Timestamp announcementDate = Timestamp.valueOf("2025-06-24 00:00:00");
        assertTrue(dbCall.makeAnnouncement("charlie_black", "Announcement 3", "This is announcement 3", announcementDate));
        assertFalse(dbCall.makeAnnouncement("jane_smith", "Announcement 4", "This is announcement 4", announcementDate));
    }

    public void testPromoteToAdmin(){
        assertFalse(dbCall.promoteToAdmin("john_doe", "charlie_black"));
        assertFalse(dbCall.promoteToAdmin("jane_smith", "bob_brown"));
        assertTrue(dbCall.promoteToAdmin("john_doe", "bob_brown"));
    }

    public void testFriendRequest() throws SQLException {
        dbCall.sendFriendRequest("jane_smith","bob_brown");
    }

    public void testUpdateFriendRequest() throws SQLException {
        dbCall.updateFriendRequestStatus(4,2,1);
        dbCall.updateFriendRequestStatus(4,1,2);
    }

    public void testFriendlist(){
        assertEquals(dbCall.getFriendlist("john_doe").size(), 4);
    }
    public void testSendChallenge(){
        dbCall.sendChallenge("bob_brown","charlie_black",56131);
    }

    public void testAnswerChallenge() throws SQLException {
        dbCall.answerChallenge(1,2,2);
        dbCall.answerChallenge(1,3,0);
        dbCall.answerChallenge(5,4,2);
    }

    public void testDeleteAccount() throws SQLException{
        assertFalse(dbCall.deleteAccount(2,1));
        assertTrue(dbCall.deleteAccount(1,2));
        assertTrue(dbCall.deleteAccount(5,1));
    }

    public void testDeleteQuiz() throws SQLException{
        dbCall.deleteQuiz(1);
        dbCall.deleteQuiz(2);
    }

    public void testSiteStatistics(){
        ArrayList<Integer> l = dbCall.getSiteStatistics();
        assertEquals(5, (int) l.get(0));
        assertEquals(4, (int) l.get(1));
    }
    public void testGetRecentPerformance(){
        ArrayList<Score> s = DatabaseAccess.getRecentPerformance("john_doe",1,0);
        assertEquals(s.size(),3);
        assertEquals(s.get(0).getScore(),80);
        assertEquals(s.get(2).getScore(),70);
    }
    public void testGetPopularQuiz(){
        ArrayList<Quiz> A = dbCall.getPopularQuiz(2);
        assertEquals(A.get(0).getTimesTaken(),12);
        assertEquals(A.get(1).getTimesTaken(),10);
        A = dbCall.getPopularQuiz(0);
        assertEquals(A.get(A.size()-1).getTimesTaken(),0);
    }
    public void testRecentCreationsByUser(){
        ArrayList<Quiz> A = dbCall.recentCreationsByUser("charlie_black",4);
        assertEquals(A.get(0).getTimesTaken(),3);
        assertEquals(A.get(1).getTimesTaken(),5);
        assertEquals(A.size(),3);
    }
    public void testGetLastAttemptOfUserOnQuiz(){
        ArrayList<Score> s = dbCall.getLastAttemptsOfUserOnQuiz("john_doe",1,3);
        assertEquals(s.get(0).getScore(),80);
        s = dbCall.getLastAttemptsOfUserOnQuiz("john_doe",12,3);
        assertEquals(s.size(),0);
        s = dbCall.getLastAttemptsOfUserOnQuiz("jane_smith",3,3);
        assertEquals(s.get(0).getScore(),95);
    }
    public void testGetTopPerformersForLastDay() {

        ArrayList<ScoreAndUser> A = dbCall.getTopPerformersForLastDay(4, 0);
        assertEquals(A.size(), 4);
        assertEquals(A.get(0).getScore().getScore(), 95);
    }
    public void testGetNotes(){
        ArrayList<Note> notes= DatabaseAccess.getNotes(1,100);
        assertEquals(notes.size(),3);
        assertEquals(notes.get(0).getText(),"same");
        assertEquals(notes.get(2).getText(),"Great, what about you?");
    }
    public void testGetAverageTime(){
        double averageTime = dbCall.getAverageTime(4);
        assertEquals(averageTime,1150.0);

    }
    public void testGetAverageScore(){
        double avgScore = dbCall.getAverageScore(4);
        assertEquals(avgScore,82.5);
    }
    public void testGetLastAttemptsOfUser(){
        ArrayList<Score> s = dbCall.getLastAttemptsOfUser("bob_brown",3);
        assertEquals(s.get(0).getScore(),70);
        assertEquals(s.get(1).getScore(),95);
        assertEquals(s.get(0).getQuiz_id(),4);
    }
    public void testGetRecentAchievements(){
        ArrayList<Achievement> a =dbCall.getRecentAchievements("bob_brown",2);
        assertEquals(a.size(),2);
        assertEquals(a.get(0).getAchievementTitle(),"Practice makes perfect");
        a =dbCall.getRecentAchievements("john_doe",3);
        assertEquals(a.size(),2);
        assertEquals(a.get(1).getAchievementTitle(),"Quiz machine");

        a =dbCall.getRecentAchievements("jane_smith",3);
        assertEquals(a.size(),1);
        assertEquals(a.get(0).getAchievementTitle(),"Amateur author");

    }
    public void testGetFriendlist(){
        ArrayList<User>users = dbCall.getFriendlist("john_doe");
        assertEquals(users.size(),4);
        users = dbCall.getFriendlist("charlie_black");
        assertEquals(users.size(),1);


    }
    public void testGetFriendsActivity(){
        ArrayList<Activity> act= dbCall.getFriendsActivity("john_doe",2);
        assertEquals(act.size(),4);
        assertEquals(act.get(1).getUsername(),"jane_smith");
        assertEquals(act.get(1).getAchievementList().size(),1);
        assertEquals(act.get(1).getAchievementList().get(0).getAchievementTitle(),"Amateur author");
    }

    public void testGetUserAchievements(){
        System.out.println(dbCall.getUserAchievements("john_doe").toString());
        System.out.println(dbCall.getUserAchievements("jane_smith").toString());
        System.out.println(dbCall.getUserAchievements("alice_jones").toString());
        System.out.println(dbCall.getUserAchievements("bob_brown").toString());
        System.out.println(dbCall.getUserAchievements("charlie_black").toString());
    }

    public void testGetQuizRating(){
        assertEquals(dbCall.getQuizRating(1),0.6);
    }

    public void testQuizFinished(){
        Timestamp date = Timestamp.valueOf("2025-06-24 00:00:00");
        dbCall.quizFinished(2,1, 120, date, 96, false, 5);
        assertEquals(dbCall.getQuizRating(1), 1.0);
        // jane_smith should have 9 quizzes taken
        // jane_smith should get greatest achievement
        // scores table must get 16th entry
    }

    public void testCreateQuiz(){
        Timestamp date = Timestamp.valueOf("2024-07-01 00:00:00");
        int quizID = dbCall.createQuizAndGetID("Football quiz", "Test your football knowledge", 1, "john_doe", 0, 0, 0, 0, date);
        ArrayList<Question> questions = new ArrayList<>();

        // A question where you have to fill in one textbox
        QuestionTextbox q1 = new QuestionTextbox(1, quizID, 1, QUESTION_TEXTBOX,
                "Which footballer is famous for having a penalty kick named after him?", "Panenka");
        // A question where you have to fill in a blank in a sentence
        QuestionFillBlank q2 = new QuestionFillBlank(1, quizID, 2, QUESTION_FILL_BLANK,
                "Kylian Mbappe has a girlfriend",
                "Kylian Mbappe has a transgender girlfriend",
                "transgender");
        // Multiple choice question with one answer
        ArrayList<String> ans3 = new ArrayList<String>();
        ans3.add("Pele");
        ans3.add("Garrincha");
        ans3.add("Cafu");
        QuestionMultipleChoice q3 = new QuestionMultipleChoice(1, quizID, 3, QUESTION_MULTIPLE_CHOICE,
                "Which of these players has the most world cups?", 1, ans3, "Pele");

        // A question about a picture with one answer
        QuestionPicture q4 = new QuestionPicture(1, quizID, 4, QUESTION_PICTURE, "Who is this distinguished gentleman?", "Ronaldo", "src/main/webapp/Pictures/440942305_405396802310623_7796720358131087077_n.jpg");

        // A question that has several textboxes that require one answer each
        ArrayList<String> ans5 = new ArrayList<String>();
        ans5.add("Messi");
        ans5.add("Neymar");
        ans5.add("Suarez");
        QuestionMultiAnswer q5 = new QuestionMultiAnswer(1, quizID, 5, QUESTION_MULTIANSWER, "Name the 3 players who made up MSN trio:", 0, ans5);

        // A multiple choice question that has several answers
        ArrayList<String> ans6 = new ArrayList<String>();
        ans6.add("Messi");
        ans6.add("Neymar");
        ans6.add("Suarez");
        ans6.add("Mbappe");
        ArrayList<Integer> correctAns6 = new ArrayList<>();
        correctAns6.add(1);
        correctAns6.add(0);
        correctAns6.add(0);
        correctAns6.add(1);
        QuestionCheckbox q6 = new QuestionCheckbox(1, quizID, 6, QUESTION_CHECKBOX,
                "Which players have won the world cup? ", 0, ans6, correctAns6);

        // A matching question
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> matching = new ArrayList<>();
        words.add("Lionel Messi");
        matching.add("8");
        words.add("Cristiano Ronaldo");
        matching.add("5");
        words.add("Johan Cruyff");
        matching.add("3");
        QuestionMatching q7 = new QuestionMatching(1, quizID, 7, QUESTION_MATCHING,
                "How many Ballon'd'ors have these players won?", words, matching);
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        questions.add(q7);
        dbCall.populateQuiz(questions);

    }

}
