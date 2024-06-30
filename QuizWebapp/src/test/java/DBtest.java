import DBpackage.*;
import DBpackage.Questions.*;
import DBpackage.Quiz;
import DBpackage.User;
import junit.framework.TestCase;

import javax.validation.constraints.AssertTrue;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;


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
    public void test4() throws SQLException{
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

        System.out.println( hasher.getHash("password123"));
        System.out.println( hasher.getHash("password234"));
        System.out.println( hasher.getHash("molly"));
        System.out.println( hasher.getHash("b"));
        System.out.println( hasher.getHash("d"));

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

        ArrayList<FriendRequest> list = dbCall.friendRequests("john_doe");
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
    public void testCreateNote(){
        assertTrue(dbCall.createNote("john_doe","jane_smith","i love you"));
        assertFalse(dbCall.createNote("john_do","jane_smith","i love you"));

    }

    public void testGetAnnouncements() throws SQLException {
        ArrayList<Announcement> l = dbCall.getLatestAnnouncements(2);
        assertEquals(l.size(), 2);
        assertEquals(l.get(0).getTitle(), "Announcement 2");
        assertEquals(l.get(1).getTitle(), "Announcement 1");
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
        Score s = dbCall.getLastAttemptOfUserOnQuiz("john_doe",1);
        assertEquals(s.getScore(),80);
        s = dbCall.getLastAttemptOfUserOnQuiz("john_doe",12);
        assertNull(s);
        s = dbCall.getLastAttemptOfUserOnQuiz("jane_smith",3);
        assertEquals(s.getScore(),95);
    }
    public void testGetTopPerformersForLastDay() {

        ArrayList<ScoreAndUser> A = dbCall.getTopPerformersForLastDay(4, 0);
        assertEquals(A.size(), 4);
        assertEquals(A.get(0).getScore().getScore(), 95);
    }
    public void testGetNotes(){
        ArrayList<Note> notes= DatabaseAccess.getNotes("john_doe",100);
        assertEquals(notes.size(),3);
        assertEquals(notes.get(0).getText(),"same");
        assertEquals(notes.get(2).getText(),"Great, what about you?");
    }


}
