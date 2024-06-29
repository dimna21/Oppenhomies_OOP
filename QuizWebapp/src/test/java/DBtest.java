import DBpackage.*;
import DBpackage.Questions.Question;
import DBpackage.Questions.QuestionFillBlank;
import DBpackage.Questions.QuestionMultipleChoice;
import DBpackage.Questions.QuestionTextbox;
import DBpackage.Quiz;
import DBpackage.User;
import junit.framework.TestCase;

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
        assertEquals(ls.size(),5);
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
        qz= dbCall.getQuizInfo(6);
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
}
